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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilityEvents implements Listener {
    /*@EventHandler
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
    }*/

    @EventHandler
    public static void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        PlayerInventory inv = player.getInventory();
        Location local = player.getLocation();
        ItemStack item = event.getItem();
        World world = player.getWorld();

        if (eitherAction(event)) {
            if (item != null) {
                if (equals(item, ItemRegistry.ABILITY_EmergencyRepairs)) {
                    //TODO make repair work

                    inv.clear(3);
                } else if (equals(item, ItemRegistry.ABILITY_SelfDestruct)) {
                    player.setHealth(0);
                    world.createExplosion(local, 5f);
                } else if (equals(item, ItemRegistry.ABILITY_ShieldUp)) {
                    inv.setItemInOffHand(ItemRegistry.WEAPON_BunketShield);
                    inv.clear(5);
                } else if (equals(item, ItemRegistry.ABILITY_Swift)) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 49, false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 60, 1, false, false));
                    inv.clear(5);
                } else if (equals(item, ItemRegistry.ABILITY_Fletch)) {
                    inv.addItem(new ItemStack(Material.ARROW, 2));
                    inv.clear(3);
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_FLETCHER, 10, 1);
                } else if (equals(item, ItemRegistry.ABILITY_Snare)) {
                    //TODO finish snare
                    world.spawnEntity(local, EntityType.ARMOR_STAND);
                    inv.clear(4);
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
