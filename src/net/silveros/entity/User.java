package net.silveros.entity;

import net.silveros.game.RivalsCore;
import net.silveros.game.SpawnLocations;
import net.silveros.kits.*;
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

import java.util.UUID;

public class User {
    public UUID playerId;
    public int currentKit = -1; // -1 means no kit, refer to Kit.java for kit values

    //Ability cooldown presets (formula: seconds * 20)
    private static final int PRESET_ShieldUp = 55 * 20;
    private static final int PRESET_Fletch = 2 * 20;
    private static final int PRESET_Snare = 2 * 20;
    private static final int PRESET_FromAbove = 55 * 20;
    private static final int PRESET_Incantation = 30 * 20;
    private static final int PRESET_IncantationActive = 5 * 20;
    private static final int PRESET_Curse = 10 * 20;
    private static final int PRESET_NormalBear = 5 * 20;
    private static final int PRESET_NumbActive = 10 * 20;
    private static final int PRESET_Numb = 30 * 20;
    private static final int PRESET_ChaosZone = 30 * 20;
    private static final int PRESET_StinkBomb = 30 * 20;
    private static final int PRESET_FogCloak = 30 * 20;
    private static final int PRESET_Uncloak = 2 * 20;
    private static final int PRESET_FogCloakMin = 2 * 20;
    private static final int PRESET_HerobrinePower = 55 * 20;
    private static final int PRESET_HerobrinePowerActive = 10 * 20;
    private static final int PRESET_Zap = 5 * 20;
    private static final int PRESET_Fireball = 5 * 20;
    private static final int PRESET_Freeze = 20 * 20;
    private static final int PRESET_Steal = 1 * 20;
    private static final int PRESET_Give = 2 * 20;

    //Ability cooldowns

    //bunket
    public int cooldown_ShieldUp = PRESET_ShieldUp;
    //archer
    public int cooldown_Fletch = PRESET_Fletch;
    public int cooldown_Snare = PRESET_Snare;
    public int cooldown_FromAbove = PRESET_FromAbove;
    //rogue
    public int cooldown_Incantation = PRESET_Incantation;
    public int cooldown_IncantationActive = PRESET_IncantationActive;
    public int cooldown_Curse = PRESET_Curse;
    //gummybear
    public int cooldown_NormalBear = PRESET_NormalBear;
    public int cooldown_Numb = PRESET_Numb;
    public int cooldown_NumbActive = PRESET_NumbActive;
    public int cooldown_ChaosZone = PRESET_ChaosZone;
    public int cooldown_StinkBomb = PRESET_StinkBomb;
    //herobrine
    public int cooldown_FogCloak = PRESET_FogCloak;
    public int cooldown_Uncloak = PRESET_Uncloak;
    public int cooldown_FogCloakMin = PRESET_FogCloakMin;
    public int cooldown_HerobrinePower = PRESET_HerobrinePower;
    public int cooldown_HerobrinePowerActive = PRESET_HerobrinePowerActive;
    //wizard
    public int cooldown_Zap = PRESET_Zap;
    public int cooldown_Fireball = PRESET_Fireball;
    public int cooldown_Freeze = PRESET_Freeze;
    //goblin
    public int cooldown_Steal = PRESET_Steal;
    public int cooldown_Give = PRESET_Give;

    //Time until abilities can be used
    public int timeUntil_Swift = 45 * 20;
    public int timeUntil_BearAbilities = 40;

    //Misc
    public boolean bearAbility = false;
    public double numbDamage = 0;
    private boolean usingFogCloak = false;
    private int cloakTime = 0;
    private static final int MAX_CLOAK_TIME = 20 * 20;

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
        if (RivalsCore.gameInProgress) {
            ItemStack energyItem = inv.getItem(6);
            if (energyItem == null && this.totalEnergy > 0 || energyItem != null && energyItem.getAmount() != this.totalEnergy) {
                inv.setItem(6, ItemRegistry.getEnergy(this.totalEnergy));
            }
        }

        //Misc gameplay
        if (this.isDead) {
            if (this.respawnTimer > 0) {
                if (!this.getTeam(RivalsPlugin.core.TEAM_SPECTATOR) && player.getGameMode() == GameMode.SPECTATOR) {
                    Vec3 spawn = SpawnLocations.getSpectatorSpawn(RivalsPlugin.core.currentMap);
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
                    if (player.getGameMode() != GameMode.SPECTATOR) {
                        if (!RivalsCore.matchingTeams(this.getTeam(), e, player)) {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 160, 2, false, false));
                            world.playSound(l, Sound.BLOCK_BIG_DRIPLEAF_BREAK, 1, 0.75f);
                            e.remove();
                        }
                    }
                }
            }

            //Energy block check
            if (e.getScoreboardTags().contains(RivalsTags.ENERGY_BLOCK_ENTITY)) {
                Location l = e.getLocation().subtract(0, 1, 0);

                if (world.getBlockAt(l).getType() == Material.DIAMOND_BLOCK) {
                    if (e.getNearbyEntities(0.5, 1, 0.5).contains(player)) {
                        if (player.getGameMode() != GameMode.SPECTATOR) {
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
            }

            //Restock block check
            if (e.getScoreboardTags().contains(RivalsTags.RESTOCK_BLOCK_ENTITY)) {
                Location l = e.getLocation().subtract(0, 1, 0);

                if (world.getBlockAt(l).getType() == Material.GOLD_BLOCK) {
                    if (e.getNearbyEntities(0.5, 1, 0.5).contains(player)) {
                        if (player.getGameMode() != GameMode.SPECTATOR) {
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
            }

            //Score block check
            if (e.getScoreboardTags().contains(RivalsTags.SCORE_BLOCK_ENTITY)) {
                Location l = e.getLocation().subtract(0, 1, 0);

                if (world.getBlockAt(l).getType() == Material.EMERALD_BLOCK) {
                    if (e.getNearbyEntities(0.5, 1, 0.5).contains(player)) {
                        if (player.getGameMode() != GameMode.SPECTATOR) {
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

        if (this.currentKit == Kit.WIZARD.kitID) {
            if (!inv.contains(ItemRegistry.ABILITY_Zap)) {
                if (this.cooldown_Zap > 0) {
                    this.cooldown_Zap--;
                } else {
                    inv.setItem(KitWizard.SLOT_ZAP, ItemRegistry.ABILITY_Zap);
                    this.cooldown_Zap = PRESET_Zap;
                }
            }

            if (!inv.contains(ItemRegistry.ABILITY_Fireball)) {
                if (this.cooldown_Fireball > 0) {
                    this.cooldown_Fireball--;
                } else {
                    inv.setItem(KitWizard.SLOT_FIREBALL, ItemRegistry.ABILITY_Fireball);
                    this.cooldown_Fireball = PRESET_Fireball;
                }
            }

            if (!inv.contains(ItemRegistry.ABILITY_Freeze)) {
                if (this.cooldown_Freeze > 0) {
                    this.cooldown_Freeze--;
                } else {
                    inv.setItem(KitWizard.SLOT_FREEZE, ItemRegistry.ABILITY_Freeze);
                    this.cooldown_Freeze = PRESET_Freeze;
                }
            }
        }

        if (this.currentKit == Kit.ARCHER.kitID) {
            if (!inv.contains(ItemRegistry.ABILITY_Fletch)) {
                if (this.cooldown_Fletch > 0) {
                    this.cooldown_Fletch--;
                } else {
                    inv.setItem(KitArcher.SLOT_FLETCH, ItemRegistry.ABILITY_Fletch);
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_FLETCHER, 10, 1);
                    this.cooldown_Fletch = PRESET_Fletch;
                }
            } else if (!inv.contains(ItemRegistry.ABILITY_Snare)) {
                if (this.cooldown_Snare > 0) {
                    this.cooldown_Snare--;
                } else {
                    inv.setItem(KitArcher.SLOT_SNARE, ItemRegistry.ABILITY_Snare);
                    this.cooldown_Snare = PRESET_Snare;
                }
            } else if (!inv.contains(ItemRegistry.ABILITY_FromAbove)) {
                if (this.cooldown_FromAbove > 0) {
                    this.cooldown_FromAbove--;
                } else {
                    inv.setItem(KitArcher.SLOT_FROM_ABOVE, ItemRegistry.ABILITY_FromAbove);
                    this.cooldown_FromAbove = PRESET_FromAbove;
                }
            }
        }

        if (this.currentKit == Kit.ROGUE.kitID) {
            if (!inv.contains(ItemRegistry.ABILITY_Swift)) {
                if (this.timeUntil_Swift > 0) {
                    this.timeUntil_Swift--;
                } else {
                    inv.setItem(KitRogue.SLOT_SWIFT, ItemRegistry.ABILITY_Swift);
                    this.timeUntil_Swift = 45 * 20;
                }
            }

            if (!inv.contains(ItemRegistry.ABILITY_Incantation)) {
                if (this.cooldown_Incantation > 0) {
                    this.cooldown_Incantation--;
                } else {
                    inv.setItem(KitRogue.SLOT_INCANTATION, ItemRegistry.ABILITY_Incantation);
                    this.cooldown_Incantation = PRESET_Incantation;
                }

                if (this.cooldown_IncantationActive > 0) {
                    this.cooldown_IncantationActive--;
                } else {
                    inv.setItem(0, ItemRegistry.WEAPON_RogueSword);
                    this.cooldown_IncantationActive = PRESET_IncantationActive;
                }
            }

            if (!inv.contains(ItemRegistry.ABILITY_Curse)) {
                if (this.cooldown_Curse > 0) {
                    this.cooldown_Curse--;
                } else {
                    inv.setItem(KitRogue.SLOT_CURSE, ItemRegistry.ABILITY_Curse);
                    this.cooldown_Curse = PRESET_Curse;
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
                    inv.setItem(KitHerobrine.SLOT_HEROBRINE_POWER, ItemRegistry.ABILITY_HerobrinePower);
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

            if (this.getFogCloak()) {
                this.cloakTime++;

                if (this.cloakTime >= MAX_CLOAK_TIME) {
                    this.setFogCloakState(false);
                }

                if (this.cooldown_Uncloak > 0) {
                    this.cooldown_Uncloak--;
                } else {
                    inv.setItem(KitHerobrine.SLOT_FOG_CLOAK, ItemRegistry.ABILITY_Uncloak);
                    this.cooldown_Uncloak = PRESET_Uncloak;
                }
            } else {
                if (this.cloakTime > 0) {
                    KitHerobrine.activateUncloak(world, local, player, inv, this);
                }

                if (inv.contains(ItemRegistry.ABILITY_Uncloak)) {
                    inv.clear(KitHerobrine.SLOT_FOG_CLOAK);
                }

                if (!inv.contains(ItemRegistry.ABILITY_FogCloak)) {
                    if (this.cooldown_FogCloak > 0) {
                        this.cooldown_FogCloak--;
                    } else {
                        inv.setItem(KitHerobrine.SLOT_FOG_CLOAK, ItemRegistry.ABILITY_FogCloak);
                        this.cooldown_FogCloak = PRESET_FogCloak;
                    }
                }
            }
        }

        if (this.currentKit == Kit.GOBLIN.kitID) {
            if (this.cooldown_Steal > 0) {
                this.cooldown_Steal--;
            } else {
                this.getInv().setItem(KitGoblin.SLOT_STEAL, ItemRegistry.ABILITY_Steal);
                this.cooldown_Steal = PRESET_Steal;
            }

            if (this.cooldown_Give > 0) {
                this.cooldown_Give--;
            } else {
                this.getInv().setItem(KitGoblin.SLOT_GIVE, ItemRegistry.ABILITY_Give);
                this.cooldown_Give = PRESET_Give;
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

        Player player = this.getPlayer();
        player.resetMaxHealth();
        player.setHealth(20);
    }

    public void activateKit(boolean keepEnergy) {
        if (this.currentKit != -1) {
            Player player = this.getPlayer();

            Kit kit = Kit.KIT_LIST.get(this.currentKit);
            kit.activateKit(player, this.getInv());

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

            //potential fix, check if player isnt a spectator, then assume rest of players either on spectator or without a team are on spectator
            if (!this.getTeam().equals(RivalsPlugin.core.TEAM_SPECTATOR)) {
                this.setGameMode(GameMode.ADVENTURE);
                this.activateKit(false);
                Vec3 spawn = this.getTeam(RivalsPlugin.core.TEAM_BLUE) ? SpawnLocations.getBlueSpawn(RivalsPlugin.core.currentMap) : SpawnLocations.getRedSpawn(RivalsPlugin.core.currentMap);
                player.teleport(new Location(player.getWorld(), spawn.posX, spawn.posY, spawn.posZ));
            } else {
                this.setGameMode(GameMode.SPECTATOR);
                Vec3 spawn = SpawnLocations.getSpectatorSpawn(RivalsPlugin.core.currentMap);
                player.teleport(new Location(player.getWorld(), spawn.posX, spawn.posY, spawn.posZ));
            }

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

    public boolean getFogCloak() {
        if (this.currentKit == Kit.HEROBRINE.kitID) {
            return this.usingFogCloak;
        }

        return false;
    }

    public void setFogCloakState(boolean state) {
        if (!state) {
            this.cloakTime = 0;
        }

        this.usingFogCloak = state;
    }
}
