package net.silveros.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerEvents implements Listener {
    @EventHandler
    public static void onConsumption(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        if (event.getItem().getType() == Material.APPLE) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 30, 2, false, false));
        }
    }
}
