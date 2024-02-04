package net.silveros.events;

import net.silveros.kits.ItemRegistry;
import net.silveros.kits.KitBunket;
import net.silveros.main.RivalsPlugin;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilityEvents implements Listener {
    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage(ChatColor.LIGHT_PURPLE + "Welcome to the server!");

        RivalsPlugin.addPlayer(player);
    }


    @EventHandler
    public static void onPlayerLeave(PlayerQuitEvent event) {
        RivalsPlugin.removePlayer(event.getPlayer());
    }

    @EventHandler
    public static void onPlayerWalk(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location local = player.getLocation();
        int x = local.getBlockX();
        int y = local.getBlockY();
        int z = local.getBlockZ();

        Material block = player.getWorld().getBlockAt(x, y - 1, z).getType();

        if (block == Material.STONE) {
            player.sendMessage("You are on a stone block.");
        }
    }

    @EventHandler
    public static void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Location local = player.getLocation();
        ItemStack item = event.getItem();
        World world = player.getWorld();

        if (eitherAction(event)) {
            if (item != null) {
                if (equals(item, ItemRegistry.ABILITY_SelfDestruct)) {
                    world.createExplosion(local, 5f);
                } else if (equals(item, ItemRegistry.ABILITY_ShieldUp)) {
                    player.getInventory().setItemInOffHand(KitBunket.getShieldItem());
                } else if (equals(item, ItemRegistry.ABILITY_Swift)) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 49, false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 60, 1, false, false));
                    player.getInventory().clear(5);
                } else if (equals(item, ItemRegistry.ABILITY_Fletch)) {
                    player.getInventory().addItem(new ItemStack(Material.ARROW, 2));
                    player.getInventory().clear(3);
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_FLETCHER, 10, 1);
                } else if (equals(item, ItemRegistry.ABILITY_Snare)) {
                    world.spawnEntity(local, EntityType.ARMOR_STAND);
                    player.getInventory().clear(4);
                }
            }
        }
    }

    private static boolean eitherAction(PlayerInteractEvent event) {
        return event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR;
    }

    private static boolean equals(ItemStack a, ItemStack b) {
        ItemMeta meta = a.getItemMeta();
        return meta != null && meta.equals(b.getItemMeta());
    }
}
