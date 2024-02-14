package net.silveros.events;

import net.silveros.entity.RivalsTags;
import net.silveros.entity.User;
import net.silveros.game.RivalsCore;
import net.silveros.game.SpawnLocations;
import net.silveros.main.RivalsPlugin;
import net.silveros.utility.Color;
import net.silveros.utility.Util;
import net.silveros.utility.Vec3;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerEvents implements Listener, Color {
    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage(RivalsPlugin.WELCOME_MESSAGE);

        RivalsPlugin.addPlayer(player);

        if (RivalsCore.gameInProgress) {
            RivalsPlugin.core.addPlayerToBossbars(player);
        }
    }

    @EventHandler
    public static void onPlayerLeave(PlayerQuitEvent event) {
        RivalsPlugin.removePlayer(event.getPlayer());
    }

    @EventHandler
    public static void onConsumption(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        if (event.getItem().getType() == Material.APPLE) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 30, 2, false, false));
        }
    }

    @EventHandler
    public static void onMessageSent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        User user = Util.getUserFromId(player.getUniqueId());
        String prefix = "";
        String color = "";

        if (user != null) {
            if (user.getTeam(RivalsPlugin.core.TEAM_RED)) {
                prefix = RivalsPlugin.core.TEAM_RED.getPrefix();
                color = RED;
            } else if (user.getTeam(RivalsPlugin.core.TEAM_BLUE)) {
                prefix = RivalsPlugin.core.TEAM_BLUE.getPrefix();
                color = BLUE;
            } else if (user.getTeam(RivalsPlugin.core.TEAM_SPECTATOR)) {
                prefix = RivalsPlugin.core.TEAM_SPECTATOR.getPrefix();
                color = GRAY;
            }
        }

        event.setFormat(prefix + RESET + "<" + color + player.getName() + RESET + "> " + event.getMessage());
    }

    @EventHandler
    public static void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        User user = Util.getUserFromId(player.getUniqueId());

        user.markAsDead(RivalsCore.RESPAWN_TIME);
        player.sendMessage("You will respawn in 10 seconds.");
    }

    @EventHandler
    public static void onPlayerHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Firework) {
            Firework fw = (Firework)event.getDamager();
            if (fw.hasMetadata("nodamage")) {
                event.setCancelled(true);
            }
        }
    }

    /*@EventHandler
    public static void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        User user = Util.getUserFromId(player.getUniqueId());

        if (!user.getTeam(RivalsPlugin.core.TEAM_SPECTATOR) && player.getGameMode() == GameMode.SPECTATOR && !user.isDead) {
            Vec3 spawn = SpawnLocations.TERRA_SPECTATOR;
            player.teleport(new Location(player.getWorld(), spawn.posX, spawn.posY, spawn.posZ));

            user.isDead = true;
        }
    }*/
}
