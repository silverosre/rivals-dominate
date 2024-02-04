package net.silveros.events;

import net.silveros.main.RivalsPlugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerEvents implements Listener {
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

    @EventHandler
    public static void onConsumption(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        if (event.getItem().getType() == Material.APPLE) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 30, 2, false, false));
        }
    }
}
