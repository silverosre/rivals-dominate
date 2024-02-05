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

    //Ability cooldowns
    //bunket
    public int cooldown_ShieldUp = 1100;
    //archer
    public int cooldown_Fletch = 40;
    public int cooldown_Snare = 40;
    public int cooldown_ArcherCrossbow = 200;
    public int cooldown_Quickshot = 1100;
    //hamood
    public int cooldown_AbilityDuneSlice = 600;
    public int cooldown_DuneSlicer = 100;
    public int cooldown_NormalBear = 100;

    //Time until abilities can be used
    public int timeUntil_Swift = 1200; // 60 seconds

    //Misc
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
                    this.cooldown_Fletch = 40;
                }
            } else if (!this.getInv().contains(ItemRegistry.ABILITY_Snare)) {
                if (this.cooldown_Snare > 0) {
                    this.cooldown_Snare--;
                } else {
                    this.getInv().setItem(4, ItemRegistry.ABILITY_Snare);
                    this.cooldown_Snare = 40;
                }
            } else if (!this.getInv().contains(ItemRegistry.ABILITY_Quickshot)) {
                if (this.cooldown_Quickshot > 0) {
                    this.cooldown_Quickshot--;
                } else {
                    this.getInv().setItem(5, ItemRegistry.ABILITY_Quickshot);
                    this.cooldown_Quickshot = 1100;
                }
                if (this.cooldown_ArcherCrossbow > 0) {
                    this.cooldown_ArcherCrossbow--;
                } else {
                    this.getInv().setItem(1, ItemRegistry.WEAPON_ArcherBow);
                    this.cooldown_ArcherCrossbow = 200;
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
                if (this.cooldown_AbilityDuneSlice > 0) {
                    this.cooldown_AbilityDuneSlice--;
                } else {
                    this.getInv().setItem(4, ItemRegistry.ABILITY_DuneSlice);
                    this.cooldown_AbilityDuneSlice = 600;
                }
                if (this.cooldown_DuneSlicer > 0) {
                    this.cooldown_DuneSlicer--;
                } else {
                    this.getInv().setItem(0, ItemRegistry.WEAPON_HamoodSword);
                    this.cooldown_DuneSlicer = 100;
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
