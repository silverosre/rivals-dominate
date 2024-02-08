package net.silveros.entity;

import net.silveros.game.RivalsCore;
import net.silveros.kits.ItemRegistry;
import net.silveros.kits.Kit;
import net.silveros.main.RivalsPlugin;
import net.silveros.utility.Util;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scoreboard.Team;

import java.util.UUID;

public class User {
    public int team = 0;
    public int fogCloakCheck = 0;

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
    //herobrine
    public int cooldown_FogCloak = PRESET_FogCloak;
    public int cooldown_FogCloakMin = PRESET_FogCloakMin;
    public int cooldown_HerobrinePower = PRESET_HerobrinePower;
    public int cooldown_HerobrinePowerActive = PRESET_HerobrinePowerActive;

    //Time until abilities can be used
    public int timeUntil_Swift = 60 * 20;

    //Misc
    private int totalEnergy = 0;
    private int respawnTimer = 0; // will tick down if above zero
    private boolean isDead = false;

    public User(UUID uuid) {
        this.playerId = uuid;
    }

    /**Player tick loop*/
    public void onTick() {
        //Update energy
        ItemStack energyItem = this.getInv().getItem(6);
        if (energyItem == null && this.totalEnergy > 0 || energyItem != null && energyItem.getAmount() != this.totalEnergy) {
            this.getInv().setItem(6, ItemRegistry.getEnergy(this.totalEnergy));
        }

        //Misc gameplay
        if (this.isDead) {
            if (this.respawnTimer > 0) {
                this.respawnTimer--;
            } else {
                this.respawn();
            }
        }

        //Kit cooldowns
        if (this.currentKit == Kit.BUNKET.kitID) {
            if (!this.getInv().contains(ItemRegistry.ABILITY_ShieldUp)) {
                if (this.cooldown_ShieldUp > 0) {
                    this.cooldown_ShieldUp--;
                } else {
                    this.getInv().setItem(5, ItemRegistry.ABILITY_ShieldUp);
                    this.cooldown_ShieldUp = PRESET_ShieldUp;
                }
            }
        }

        if (this.currentKit == Kit.ARCHER.kitID) {
            Player player = this.getPlayer();
            if (!this.getInv().contains(ItemRegistry.ABILITY_Fletch)) {
                if (this.cooldown_Fletch > 0) {
                    this.cooldown_Fletch--;
                } else {
                    this.getInv().setItem(3, ItemRegistry.ABILITY_Fletch);
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_FLETCHER, 10, 1);
                    this.cooldown_Fletch = PRESET_Fletch;
                }
            } else if (!this.getInv().contains(ItemRegistry.ABILITY_Snare)) {
                if (this.cooldown_Snare > 0) {
                    this.cooldown_Snare--;
                } else {
                    this.getInv().setItem(4, ItemRegistry.ABILITY_Snare);
                    this.cooldown_Snare = PRESET_Snare;
                }
            } else if (!this.getInv().contains(ItemRegistry.ABILITY_FromAbove)) {
                if (this.cooldown_FromAbove > 0) {
                    this.cooldown_FromAbove--;
                } else {
                    this.getInv().setItem(5, ItemRegistry.ABILITY_FromAbove);
                    this.cooldown_FromAbove = PRESET_FromAbove;
                }
            }
        }

        if (this.currentKit == Kit.HAMOOD.kitID) {
            if (!this.getInv().contains(ItemRegistry.ABILITY_Swift)) {
                if (this.timeUntil_Swift > 0) {
                    this.timeUntil_Swift--;
                } else {
                    this.getInv().setItem(5, ItemRegistry.ABILITY_Swift);
                }
            }

            if (!this.getInv().contains(ItemRegistry.ABILITY_DuneSlice)) {
                if (this.cooldown_DuneSlice > 0) {
                    this.cooldown_DuneSlice--;
                } else {
                    this.getInv().setItem(4, ItemRegistry.ABILITY_DuneSlice);
                    this.cooldown_DuneSlice = PRESET_DuneSlice;
                }

                if (this.cooldown_DuneSlicerActive > 0) {
                    this.cooldown_DuneSlicerActive--;
                } else {
                    this.getInv().setItem(0, ItemRegistry.WEAPON_HamoodSword);
                    this.cooldown_DuneSlicerActive = PRESET_DuneSlicerActive;
                }
            }
        }
        if (this.currentKit == Kit.GUMMY_BEAR.kitID) {
            if (!this.getInv().contains(ItemRegistry.ITEM_GummyEssence)) {
                if (this.cooldown_NormalBear > 0) {
                    this.cooldown_NormalBear--;
                } else {
                    this.getInv().setItem(4, ItemRegistry.ABILITY_NormalBear);
                    this.getInv().setItem(9, ItemRegistry.ITEM_GummyEssence);
                    this.cooldown_NormalBear = PRESET_NormalBear;
                }
            }
        }
        if (this.currentKit == Kit.HEROBRINE.kitID) {
            if(!this.getInv().contains(ItemRegistry.ABILITY_HerobrinePower)) {
                if (this.cooldown_HerobrinePower > 0) {
                    this.cooldown_HerobrinePower--;
                } else {
                    this.getInv().setItem(3, ItemRegistry.ABILITY_HerobrinePower);
                    this.cooldown_HerobrinePower = PRESET_HerobrinePower;
                }
                if (this.cooldown_HerobrinePowerActive > 0) {
                    this.cooldown_HerobrinePowerActive--;
                } else {
                    this.getInv().setItem(0, ItemRegistry.WEAPON_HerobrineAxe);
                    this.getInv().setItem(1, ItemRegistry.WEAPON_HerobrineBow);
                    this.cooldown_HerobrinePowerActive = PRESET_HerobrinePowerActive;
                }
            }
            if(!this.getInv().contains(ItemRegistry.ABILITY_FogCloak)) {
                if(this.getPlayer().getActivePotionEffects().isEmpty()){
                    if (this.cooldown_FogCloak > 0) {
                        this.cooldown_FogCloak--;
                    } else {
                        this.getInv().setItem(4, ItemRegistry.ABILITY_FogCloak);
                        this.cooldown_FogCloak = PRESET_FogCloak;
                        this.fogCloakCheck = 0;
                    }
                    if(this.getInv().contains(ItemRegistry.ABILITY_Uncloak)){
                        //checks to see if fog cloak was taken away
                        Player player = this.getPlayer();
                        Location local = this.getPlayer().getLocation();
                        Inventory inv = this.getInv();
                        inv.clear(4);
                        this.getInv().setHelmet(ItemRegistry.SKULL_Herobrine);
                        this.getInv().setChestplate(ItemRegistry.ARMOR_HerobrineChestplate);
                        this.getInv().setLeggings(ItemRegistry.ARMOR_HerobrineLeggings);
                        this.getInv().setBoots(ItemRegistry.ARMOR_HerobrineBoots);
                        inv.setItem(1, ItemRegistry.WEAPON_HerobrineBow);
                        inv.setItem(7, ItemRegistry.WEAPON_HerobrineArrows);
                        player.playSound(local, Sound.ENTITY_ENDERMAN_HURT, 1, 1);
                        player.spawnParticle(Particle.SMOKE_LARGE, local, 20);
                    }
                }
            }
            if(!this.getInv().contains(ItemRegistry.ABILITY_Uncloak) && !this.getInv().contains(ItemRegistry.ABILITY_FogCloak) && this.fogCloakCheck == 0) {
                if (this.cooldown_FogCloakMin > 0) {
                    this.cooldown_FogCloakMin--;
                } else {
                    this.getInv().setItem(4, ItemRegistry.ABILITY_Uncloak);
                    this.cooldown_FogCloakMin = PRESET_FogCloakMin;
                    this.fogCloakCheck = 1;
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
        this.activateKit();
    }

    public void resetKit() {
        this.currentKit = -1;
        this.getInv().clear();
    }

    public void activateKit() {
        if (this.currentKit != -1) {
            Kit kit = Kit.KIT_LIST.get(this.currentKit);

            this.setTotalEnergy(kit.getStartingEnergy());
            kit.activateKit(this.getInv());
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

    public void respawn() {
        Player player = this.getPlayer();

        if (player != null) {
            //FIXME:
            // need to add code for respawning at base location

            this.setGameMode(GameMode.ADVENTURE);
            this.activateKit();
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
