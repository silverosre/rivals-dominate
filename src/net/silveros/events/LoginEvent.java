package net.silveros.events;

import net.silveros.main.RivalsPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LoginEvent implements Listener {
    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage(RivalsPlugin.WELCOME_MESSAGE);

        RivalsPlugin.addPlayer(player);
    }

    @EventHandler
    public static void onPlayerLeave(PlayerQuitEvent event) {
        RivalsPlugin.removePlayer(event.getPlayer());
    }
}
