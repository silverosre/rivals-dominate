package net.silveros.entity;

import net.silveros.kits.ItemRegistry;
import net.silveros.kits.Kit;
import net.silveros.utility.Util;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.UUID;

public class User {
    public UUID playerId;
    public int currentKit = -1; // -1 means no kit, refer to Kit.java for kit values

    //Ability cooldown presets (formula: seconds * 20)
    private static final int PRESET_ShieldUp = 55 * 20;
    private static final int PRESET_Fletch = 2 * 20;
    private static final int PRESET_Snare = 2 * 20;
    private static final int PRESET_FromAbove = 55 * 20;
    private static final int PRESET_DuneSlice = 30 * 20;
    private static final int PRESET_DuneSlicerActive = 5 * 20;

    //Ability cooldowns

    //bunket
    public int cooldown_ShieldUp = PRESET_ShieldUp;
    //archer
    public int cooldown_Fletch = PRESET_Fletch;
    public int cooldown_Snare = PRESET_Snare;
    public int cooldown_FromAbove = PRESET_FromAbove;
    //hamood
    public int cooldown_DuneSlice = PRESET_DuneSlice;
    public int cooldown_DuneSlicerActive = 5 * 20;
    //gummybear
    public int cooldown_NormalBear = 100;

    //Time until abilities can be used
    public int timeUntil_Swift = 60 * 20;



    //Misc
    public int totalEnergy = 0;
    public int respawnTimer = 0; // will tick down if above zero
    private boolean isDead = false;

    public User(UUID uuid) {
        this.playerId = uuid;
    }

    /**Player tick loop*/
    public void onTick() {
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
                    this.cooldown_ShieldUp = 1100;
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
                    this.cooldown_NormalBear = 100;
                }
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
            Kit.KIT_LIST.get(this.currentKit).activateKit(this.getInv());
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
