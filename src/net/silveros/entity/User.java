package net.silveros.entity;

import net.silveros.game.Gamemode;
import net.silveros.game.RivalsCore;
import net.silveros.game.SpawnLocations;
import net.silveros.kits.ItemRegistry;
import net.silveros.kits.Kit;
import net.silveros.main.RivalsPlugin;
import net.silveros.utility.Util;
import net.silveros.utility.Vec3;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    public int team = 0;
    public boolean fogCloakCheck = false;

    public UUID playerId;
    public int currentKit = -1; // -1 means no kit, refer to Kit.java for kit values

    //Ability cooldown presets (formula: seconds * 20)
    private static final int PRESET_ShieldUp = 55 * 20;
    private static final int PRESET_Fletch = 2 * 20;
    private static final int PRESET_Snare = 2 * 20;
    private static final int PRESET_FromAbove = 55 * 20;
    private static final int PRESET_DuneSlice = 30 * 20;
    private static final int PRESET_DuneSlicerActive = 5 * 20;
    private static final int PRESET_NormalBear = 5 * 20;
    private static final int PRESET_NumbActive = 10 * 20;
    private static final int PRESET_Numb = 30 * 20;
    private static final int PRESET_ChaosZone = 30 * 20;
    private static final int PRESET_ChaosZoneActive = 10 * 20;
    private static final int PRESET_StinkBomb = 30 * 20;
    private static final int PRESET_StinkBombActive = 10 * 20;
    private static final int PRESET_FogCloak = 30 * 20;
    private static final int PRESET_FogCloakMin = 2 * 20;
    private static final int PRESET_HerobrinePower = 55 * 20;
    private static final int PRESET_HerobrinePowerActive = 10 * 20;

    //Ability cooldowns

    //bunket
    public int cooldown_ShieldUp = PRESET_ShieldUp;
    //archer
    public int cooldown_Fletch = PRESET_Fletch;
    public int cooldown_Snare = PRESET_Snare;
    public int cooldown_FromAbove = PRESET_FromAbove;
    //hamood
    public int cooldown_DuneSlice = PRESET_DuneSlice;
    public int cooldown_DuneSlicerActive = PRESET_DuneSlicerActive;
    //gummybear
    public int cooldown_NormalBear = PRESET_NormalBear;
    public int cooldown_Numb = PRESET_Numb;
    public int cooldown_NumbActive = PRESET_NumbActive;
    public int cooldown_ChaosZone = PRESET_ChaosZone;
    public int cooldown_ChaosZoneActive = PRESET_ChaosZoneActive;
    public int cooldown_StinkBomb = PRESET_StinkBomb;
    public int cooldown_StinkBombActive = PRESET_StinkBombActive;
    //herobrine
    public int cooldown_FogCloak = PRESET_FogCloak;
    public int cooldown_FogCloakMin = PRESET_FogCloakMin;
    public int cooldown_HerobrinePower = PRESET_HerobrinePower;
    public int cooldown_HerobrinePowerActive = PRESET_HerobrinePowerActive;

    //Time until abilities can be used
    public int timeUntil_Swift = 60 * 20;
    public int timeUntil_BearAbilities = 40;

    //Misc
    public boolean bearAbility = false;
    private float radius = 2f;
    private float angle = 0f;
    public double numbDamage = 0;
    public double dealtDamage = 0;
    public int chaosDamage = 20;

    private int totalEnergy = 0;
    private int respawnTimer = 0; // will tick down if above zero
    public boolean isDead = false;

    //random number generator
    public double randomPosition (double min, double max) {
        return ((Math.random() * (max - min)) + min);
    }

    public User(UUID uuid) {
        this.playerId = uuid;
    }

    /**Player tick loop*/
    public void onTick() {
        Player player = this.getPlayer();
        World world = player.getWorld();
        Location local = player.getLocation();
        PlayerInventory inv = this.getInv();

        //Update energy
        ItemStack energyItem = inv.getItem(6);
        if (energyItem == null && this.totalEnergy > 0 || energyItem != null && energyItem.getAmount() != this.totalEnergy) {
            inv.setItem(6, ItemRegistry.getEnergy(this.totalEnergy));
        }

        //Misc gameplay
        if (this.isDead) {
            if (this.respawnTimer > 0) {
                if (!this.getTeam(RivalsPlugin.core.TEAM_SPECTATOR) && player.getGameMode() == GameMode.SPECTATOR) {
                    Vec3 spawn = SpawnLocations.TERRA_SPECTATOR;
                    player.teleport(new Location(player.getWorld(), spawn.posX, spawn.posY, spawn.posZ));
                }

                this.respawnTimer--;
            } else {
                this.respawn();
            }
        }

        for (Entity e : world.getEntitiesByClass(Marker.class)) {
            //Archer "Snare" ability
            if (e.getScoreboardTags().contains(RivalsTags.SNARE_ENTITY)) {
                Location l = e.getLocation().subtract(0, 1, 0);
                world.spawnParticle(Particle.BLOCK_CRACK, e.getLocation(), 5, 0, 0, 0, 0.1, world.getBlockAt(l).getBlockData());

                if (e.getNearbyEntities(1, 1, 1).contains(player)) {
                    if (!RivalsCore.matchingTeams(this.getTeam(), e, player)) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 160, 2, false, false));
                        world.playSound(l, Sound.BLOCK_BIG_DRIPLEAF_BREAK, 1, 0.75f);
                        e.remove();
                    }
                }
            }

            //Chaos Zone Ability
            if (e.getScoreboardTags().contains(RivalsTags.CHAOS_ZONE_ENTITY)) {
                Location l = e.getLocation();
                double x = (radius * Math.sin(angle));
                double z = (radius * Math.cos(angle));
                angle += 0.1;

                world.spawnParticle(Particle.REDSTONE, l.getBlockX()+x, l.getBlockY(), l.getBlockZ()+z, 0, 0.001, 0,0,0, new Particle.DustOptions(Color.AQUA, 5));
                world.spawnParticle(Particle.REDSTONE, l.getBlockX()-x, l.getBlockY(), l.getBlockZ()-z, 0, 0.001, 0,0,0, new Particle.DustOptions(Color.AQUA, 5));
                if (cooldown_ChaosZoneActive > 0) {
                    cooldown_ChaosZoneActive--;
                } else {
                    e.remove();
                    cooldown_ChaosZoneActive = PRESET_ChaosZoneActive;
                }
                if (e.getNearbyEntities(2, 1, 2).contains(player)) {
                    if(RivalsCore.matchingTeams(this.getTeam(), e, player)){
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 5, 3, true, true));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5, 99, true, true));
                    }
                    if(!RivalsCore.matchingTeams(this.getTeam(), e, player)) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5, 1, true, true));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 5, 2, true, true));
                        if (chaosDamage > 0) {
                            chaosDamage--;
                        } else {
                            player.damage(2);
                            chaosDamage = 20;
                        }
                    }
                }
            }

            //Stink Bomb ability
            if (e.getScoreboardTags().contains(RivalsTags.STINK_BOMB_ENTITY)) {
                Location l = e.getLocation();
                //generates random locations and deltas for particle cloud
                double x = l.getBlockX()+this.randomPosition(-2, 2);
                double y = l.getBlockY()+this.randomPosition(-1, 3);
                double z = l.getBlockZ()+this.randomPosition(-2, 2);
                double dX = this.randomPosition(-0.7, 0.7);
                double dY = this.randomPosition(-0.7, 0.7);
                double dZ = this.randomPosition(-0.7, 0.7);
                double dA = this.randomPosition(-0.4, 0.4);

                world.spawnParticle(Particle.REDSTONE, x, y, z, 30, dX, dY, dZ, dA, new Particle.DustOptions(Color.YELLOW, 40));
                if (this.cooldown_StinkBombActive > 0) {
                    this.cooldown_StinkBombActive--;
                } else {
                    e.remove();
                    this.cooldown_StinkBombActive = PRESET_StinkBombActive;
                }
                if (e.getNearbyEntities(3, 2, 3).contains(player)) {
                    if (!RivalsCore.matchingTeams(this.getTeam(), e, player)) {
                        //TODO fix nausea
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10, 1, false, false));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 4, true, true));
                    }
                }
            }

            //Energy block check
            if (e.getScoreboardTags().contains(RivalsTags.ENERGY_BLOCK_ENTITY)) {
                Location l = e.getLocation().subtract(0, 1, 0);

                if (world.getBlockAt(l).getType() == Material.DIAMOND_BLOCK) {
                    if (e.getNearbyEntities(0.5, 1, 0.5).contains(player)) {
                        Score cooldown = RivalsPlugin.core.energyBlockCooldown.getScore(e.getUniqueId().toString());
                        if (cooldown.getScore() <= 0) {
                            this.addEnergy(1);

                            world.playSound(l, Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 1);
                            world.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                            world.playSound(player, Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 2);

                            cooldown.setScore(RivalsCore.ENERGY_BLOCK_TIMER);
                            RivalsCore.spawnFirework(e.getLocation(), Color.AQUA);

                            TextDisplay text = (TextDisplay)world.spawnEntity(e.getLocation().add(0, 1.5, 0), EntityType.TEXT_DISPLAY);
                            text.addScoreboardTag(RivalsTags.ENERGY_BLOCK_COOLDOWN_TEXT_ENTITY);
                            text.setBillboard(Display.Billboard.VERTICAL);
                            text.setDisplayWidth(1f);

                            Score cooldownText = RivalsPlugin.core.energyBlockCooldown.getScore(text.getUniqueId().toString());
                            cooldownText.setScore(RivalsCore.ENERGY_BLOCK_TIMER);

                            world.setBlockData(l, Material.IRON_BLOCK.createBlockData());
                        }
                    }
                }
            }

            //Restock block check
            if (e.getScoreboardTags().contains(RivalsTags.RESTOCK_BLOCK_ENTITY)) {
                Location l = e.getLocation().subtract(0, 1, 0);

                if (world.getBlockAt(l).getType() == Material.GOLD_BLOCK) {
                    if (e.getNearbyEntities(0.5, 1, 0.5).contains(player)) {
                        Score cooldown = RivalsPlugin.core.restockBlockCooldown.getScore(e.getUniqueId().toString());
                        if (cooldown.getScore() <= 0) {
                            this.activateKit(true);

                            world.playSound(l, Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 1);
                            world.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                            world.playSound(player, Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 2);

                            cooldown.setScore(RivalsCore.RESTOCK_TIMER);
                            RivalsCore.spawnFirework(e.getLocation(), Color.YELLOW);

                            TextDisplay text = (TextDisplay)world.spawnEntity(e.getLocation().add(0, 1.5, 0), EntityType.TEXT_DISPLAY);
                            text.addScoreboardTag(RivalsTags.RESTOCK_BLOCK_COOLDOWN_TEXT_ENTITY);
                            text.setBillboard(Display.Billboard.VERTICAL);
                            text.setDisplayWidth(1f);

                            Score cooldownText = RivalsPlugin.core.restockBlockCooldown.getScore(text.getUniqueId().toString());
                            cooldownText.setScore(RivalsCore.RESTOCK_TIMER);

                            world.setBlockData(l, Material.IRON_BLOCK.createBlockData());
                        }
                    }
                }
            }

            //Score block check
            if (e.getScoreboardTags().contains(RivalsTags.SCORE_BLOCK_ENTITY)) {
                Location l = e.getLocation().subtract(0, 1, 0);

                if (world.getBlockAt(l).getType() == Material.EMERALD_BLOCK) {
                    if (e.getNearbyEntities(0.5, 1, 0.5).contains(player)) {
                        Score cooldown = RivalsPlugin.core.scoreBlockCooldown.getScore(e.getUniqueId().toString());
                        if (cooldown.getScore() <= 0) {
                            RivalsPlugin.core.addScoreBlockPoints(this.getTeam());

                            world.playSound(l, Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 1);
                            world.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                            world.playSound(player, Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 2);

                            cooldown.setScore(RivalsCore.SCORE_BLOCK_TIMER);
                            RivalsCore.spawnFirework(e.getLocation(), Color.LIME);

                            TextDisplay text = (TextDisplay)world.spawnEntity(e.getLocation().add(0, 1.5, 0), EntityType.TEXT_DISPLAY);
                            text.addScoreboardTag(RivalsTags.SCORE_BLOCK_COOLDOWN_TEXT_ENTITY);
                            text.setBillboard(Display.Billboard.VERTICAL);
                            text.setDisplayWidth(1f);

                            Score cooldownText = RivalsPlugin.core.scoreBlockCooldown.getScore(text.getUniqueId().toString());
                            cooldownText.setScore(RivalsCore.SCORE_BLOCK_TIMER);

                            world.setBlockData(l, Material.IRON_BLOCK.createBlockData());
                        }
                    }
                }
            }
        }

        //Kit cooldowns
        if (this.currentKit == Kit.BUNKET.kitID) {
            if (!inv.contains(ItemRegistry.ABILITY_ShieldUp)) {
                if (this.cooldown_ShieldUp > 0) {
                    this.cooldown_ShieldUp--;
                } else {
                    inv.setItem(5, ItemRegistry.ABILITY_ShieldUp);
                    this.cooldown_ShieldUp = PRESET_ShieldUp;
                }
            }
        }

        if (this.currentKit == Kit.ARCHER.kitID) {
            if (!inv.contains(ItemRegistry.ABILITY_Fletch)) {
                if (this.cooldown_Fletch > 0) {
                    this.cooldown_Fletch--;
                } else {
                    inv.setItem(3, ItemRegistry.ABILITY_Fletch);
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_FLETCHER, 10, 1);
                    this.cooldown_Fletch = PRESET_Fletch;
                }
            } else if (!inv.contains(ItemRegistry.ABILITY_Snare)) {
                if (this.cooldown_Snare > 0) {
                    this.cooldown_Snare--;
                } else {
                    inv.setItem(4, ItemRegistry.ABILITY_Snare);
                    this.cooldown_Snare = PRESET_Snare;
                }
            } else if (!inv.contains(ItemRegistry.ABILITY_FromAbove)) {
                if (this.cooldown_FromAbove > 0) {
                    this.cooldown_FromAbove--;
                } else {
                    inv.setItem(5, ItemRegistry.ABILITY_FromAbove);
                    this.cooldown_FromAbove = PRESET_FromAbove;
                }
            }
        }

        if (this.currentKit == Kit.HAMOOD.kitID) {
            if (!inv.contains(ItemRegistry.ABILITY_Swift)) {
                if (this.timeUntil_Swift > 0) {
                    this.timeUntil_Swift--;
                } else {
                    inv.setItem(5, ItemRegistry.ABILITY_Swift);
                }
            }

            if (!inv.contains(ItemRegistry.ABILITY_DuneSlice)) {
                if (this.cooldown_DuneSlice > 0) {
                    this.cooldown_DuneSlice--;
                } else {
                    inv.setItem(4, ItemRegistry.ABILITY_DuneSlice);
                    this.cooldown_DuneSlice = PRESET_DuneSlice;
                }

                if (this.cooldown_DuneSlicerActive > 0) {
                    this.cooldown_DuneSlicerActive--;
                } else {
                    inv.setItem(0, ItemRegistry.WEAPON_HamoodSword);
                    this.cooldown_DuneSlicerActive = PRESET_DuneSlicerActive;
                }
            }
        }

        if (this.currentKit == Kit.GUMMY_BEAR.kitID) {
            if (!inv.contains(ItemRegistry.ITEM_GummyEssence)) {
                if (this.cooldown_NormalBear > 0) {
                    this.cooldown_NormalBear--;
                } else {
                    inv.setItem(4, ItemRegistry.ABILITY_NormalBear);
                    inv.setItem(9, ItemRegistry.ITEM_GummyEssence);
                    this.cooldown_NormalBear = PRESET_NormalBear;
                }
            }

            //Normal Bear
            if (inv.getHelmet().equals(ItemRegistry.SKULL_GummyBear)) {
                if (this.timeUntil_BearAbilities > 0) {
                    this.timeUntil_BearAbilities--;
                } else {
                    inv.setItem(3, ItemRegistry.ABILITY_DefenseBear);
                    inv.setItem(4, ItemRegistry.ABILITY_AttackBear);
                    inv.setItem(5, ItemRegistry.ABILITY_SpeedBear);
                    this.timeUntil_BearAbilities = 40;
                }
            }

            //Attack Bear
            if (inv.getHelmet().equals(ItemRegistry.SKULL_AttackBear)) {
                if (this.timeUntil_BearAbilities > 0 && this.bearAbility) {
                    this.timeUntil_BearAbilities--;
                } else if (this.bearAbility){
                    inv.setItem(5, ItemRegistry.ABILITY_Numb);
                    this.timeUntil_BearAbilities = 40;
                    this.bearAbility = false;
                }

                if (inv.contains(ItemRegistry.ITEM_Numbness)) {
                    if (this.cooldown_NumbActive > 0) {
                        this.cooldown_NumbActive--;
                        if (this.cooldown_NumbActive % 5 == 0) {
                            double dX = this.randomPosition(-0.4, 0.4);
                            double dY = this.randomPosition(-0.7, 0.7);
                            double dZ = this.randomPosition(-0.4, 0.4);
                            double dA = this.randomPosition(-0.2, 0.2);
                            world.spawnParticle(Particle.REDSTONE, local.getBlockX(), local.getBlockY(), local.getBlockZ(), 2, dX, dY, dZ, dA, new Particle.DustOptions(Color.RED, 20));
                        }
                    } else {
                        player.damage(this.numbDamage);
                        inv.clear(10);
                        player.removePotionEffect(PotionEffectType.REGENERATION);
                        player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 2, false, false));
                        this.numbDamage = 0;
                        this.cooldown_NumbActive = PRESET_NumbActive;
                        this.cooldown_Numb = PRESET_Numb;
                    }
                }

                if (!inv.contains(ItemRegistry.ABILITY_Numb) && !inv.contains(ItemRegistry.ITEM_Numbness)) {
                    if (this.cooldown_Numb > 0) {
                        this.cooldown_Numb--;
                    } else {
                        inv.setItem(5, ItemRegistry.ABILITY_Numb);
                    }
                }
            }

            //Defense Bear
            if(inv.getHelmet().equals(ItemRegistry.SKULL_DefenseBear)) {
                if (this.timeUntil_BearAbilities > 0 && this.bearAbility) {
                    this.timeUntil_BearAbilities--;
                } else if (this.bearAbility){
                    inv.setItem(5, ItemRegistry.ABILITY_ChaosZone);
                    timeUntil_BearAbilities = 40;
                    this.bearAbility = false;
                }
                if(!inv.contains(ItemRegistry.ABILITY_ChaosZone)) {
                    if (this.cooldown_ChaosZone > 0) {
                        this.cooldown_ChaosZone--;
                    } else {
                        inv.setItem(5, ItemRegistry.ABILITY_ChaosZone);
                        this.cooldown_ChaosZone = PRESET_ChaosZone;
                    }
                }
            }

            //Speed Bear
            if (inv.getHelmet().equals(ItemRegistry.SKULL_SpeedBear)) {
                if(this.timeUntil_BearAbilities > 0 && this.bearAbility) {
                    this.timeUntil_BearAbilities--;
                } else if (this.bearAbility){
                    inv.setItem(5, ItemRegistry.ABILITY_StinkBomb);
                    this.timeUntil_BearAbilities = 40;
                    this.bearAbility = false;
                }
                if (!inv.contains(ItemRegistry.ABILITY_StinkBomb)) {
                    if (this.cooldown_StinkBomb > 0) {
                        this.cooldown_StinkBomb--;
                    } else {
                        inv.setItem(5, ItemRegistry.ABILITY_StinkBomb);
                        this.cooldown_StinkBomb = PRESET_StinkBomb;
                    }
                }
            }
        }

        if (this.currentKit == Kit.HEROBRINE.kitID) {
            if (!inv.contains(ItemRegistry.ABILITY_HerobrinePower)) {
                if (this.cooldown_HerobrinePower > 0) {
                    this.cooldown_HerobrinePower--;
                } else {
                    inv.setItem(3, ItemRegistry.ABILITY_HerobrinePower);
                    this.cooldown_HerobrinePower = PRESET_HerobrinePower;
                }
                if (this.cooldown_HerobrinePowerActive > 0) {
                    this.cooldown_HerobrinePowerActive--;
                } else {
                    inv.setItem(0, ItemRegistry.WEAPON_HerobrineAxe);
                    inv.setItem(1, ItemRegistry.WEAPON_HerobrineBow);
                    this.cooldown_HerobrinePowerActive = PRESET_HerobrinePowerActive;
                }
            }

            if (!inv.contains(ItemRegistry.ABILITY_FogCloak)) {
                if (!player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                    if (this.cooldown_FogCloak > 0) {
                        this.cooldown_FogCloak--;
                    } else {
                        inv.setItem(4, ItemRegistry.ABILITY_FogCloak);
                        this.cooldown_FogCloak = PRESET_FogCloak;
                        this.fogCloakCheck = false;
                    }

                    if (inv.contains(ItemRegistry.ABILITY_Uncloak)) {
                        //checks to see if fog cloak was taken away
                        inv.clear(4);
                        inv.setHelmet(ItemRegistry.SKULL_Herobrine);
                        inv.setChestplate(ItemRegistry.ARMOR_HerobrineChestplate);
                        inv.setLeggings(ItemRegistry.ARMOR_HerobrineLeggings);
                        inv.setBoots(ItemRegistry.ARMOR_HerobrineBoots);
                        inv.setItem(1, ItemRegistry.WEAPON_HerobrineBow);
                        inv.setItem(7, ItemRegistry.WEAPON_HerobrineArrows);
                        player.playSound(local, Sound.ENTITY_ENDERMAN_SCREAM, 1, 1);
                        player.spawnParticle(Particle.SMOKE_LARGE, local, 20);
                    }
                }
            }

            if (!this.getInv().contains(ItemRegistry.ABILITY_Uncloak) && !this.getInv().contains(ItemRegistry.ABILITY_FogCloak) && !this.fogCloakCheck) {
                if (this.cooldown_FogCloakMin > 0) {
                    this.cooldown_FogCloakMin--;
                } else {
                    this.getInv().setItem(4, ItemRegistry.ABILITY_Uncloak);
                    this.cooldown_FogCloakMin = PRESET_FogCloakMin;
                    this.fogCloakCheck = true;
                }
            }
        }
    }

    public boolean getTeam(Team team) {
        Player player = this.getPlayer();

        if (player != null) {
            return team.hasEntry(player.getName());
        }

        return false;
    }

    public Team getTeam() {
        if (this.getTeam(RivalsPlugin.core.TEAM_RED)) {
            return RivalsPlugin.core.TEAM_RED;
        } else if (this.getTeam(RivalsPlugin.core.TEAM_BLUE)) {
            return RivalsPlugin.core.TEAM_BLUE;
        }

        return RivalsPlugin.core.TEAM_SPECTATOR;
    }

    public void setTeam(Team team) {
        Player player = this.getPlayer();

        if (player != null) {
            player.setScoreboard(RivalsPlugin.core.board);
            RivalsCore.addEntryToTeam(team, player);
        }
    }

    public void resetTeam() {
        Player player = this.getPlayer();

        if (player != null) {
            for (Team team : RivalsPlugin.core.board.getTeams()) {
                RivalsCore.removeEntryFromTeam(team, player);
            }
        }
    }

    public void setKit(Kit kit) {
        this.currentKit = kit.kitID;
        this.activateKit(false);
    }

    public void resetKit() {
        this.currentKit = -1;
        this.getInv().clear();
        this.setTotalEnergy(0);
    }

    public void activateKit(boolean keepEnergy) {
        if (this.currentKit != -1) {
            Kit kit = Kit.KIT_LIST.get(this.currentKit);
            kit.activateKit(this.getInv());

            if (keepEnergy) {
                if (this.getTotalEnergy() < kit.getStartingEnergy()) {
                    this.setTotalEnergy(kit.getStartingEnergy());
                }
            } else {
                this.setTotalEnergy(kit.getStartingEnergy());
            }
        }
    }

    public int getTotalEnergy() {
        return this.totalEnergy;
    }

    public void setTotalEnergy(int num) {
        this.totalEnergy = num;
    }

    public void removeEnergy(int num) {
        this.totalEnergy -= num;

        if (this.totalEnergy < 0) {
            System.err.println("WARNING: " + this.getPlayer().getName() + "'s energy went below zero!");
        }
    }

    public void addEnergy(int num) {
        this.totalEnergy += num;
    }

    public void respawn() {
        Player player = this.getPlayer();

        if (player != null) {
            //FIXME:
            // need to add code for respawning at base location

            this.setGameMode(GameMode.ADVENTURE);
            this.activateKit(false);

            Vec3 spawn = this.getTeam(RivalsPlugin.core.TEAM_BLUE) ? SpawnLocations.TERRA_BLUE : SpawnLocations.TERRA_RED;
            player.teleport(new Location(player.getWorld(), spawn.posX, spawn.posY, spawn.posZ));

            this.isDead = false;
        }
    }

    public void markAsDead(int waitTime) {
        Player player = this.getPlayer();

        if (player != null) {
            //FIXME:
            // need to add code for placing player at spectator location

            this.setGameMode(GameMode.SPECTATOR);
            this.respawnTimer = waitTime;

            this.isDead = true;
        }
    }

    public void setGameMode(GameMode gamemode) {
        this.getPlayer().setGameMode(gamemode);
    }

    /**Can return null!*/
    public Player getPlayer() {
        return Util.getPlayerFromId(this.playerId);
    }

    /**Can return null!*/
    public PlayerInventory getInv() {
        return this.getPlayer().getInventory();
    }
}
