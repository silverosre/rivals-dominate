package net.silveros.entity;

import net.silveros.kits.Kit;
import net.silveros.utility.Util;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.UUID;

public class User {
    public UUID playerId;
    public int currentKit = -1; // -1 means no kit, refer to Kit.java for kit values

    //Ability cooldowns
    public int cooldown_ShieldUp = 0;
    public static final int COOLDOWN_ShieldUp_RESET = 1100; // 55 seconds

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
            if (this.cooldown_ShieldUp > 0) {
                this.cooldown_ShieldUp--;
            } else {

            }
        }

        if (this.currentKit == Kit.ARCHER.kitID) {

        }

        if (this.currentKit == Kit.HAMOOD.kitID) {
            if (this.timeUntil_Swift > 0) {
                this.timeUntil_Swift--;
            } else {

            }
        }
    }

    public void respawn() {
        Player player = this.getPlayer();

        if (player != null) {
            //FIXME:
            // need to add code for respawning at base location

            this.setGameMode(GameMode.ADVENTURE);
            if (this.currentKit != -1) {
                Kit.KIT_LIST.get(this.currentKit).activateKit(this.getPlayer().getInventory());
            }
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
}
