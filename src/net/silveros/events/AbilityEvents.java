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
                //bunket abilities
                if (equals(item, ItemRegistry.ABILITY_EmergencyRepairs)) {
                    //TODO make repair work

                    //remove if you dont like my implementation --Roasty
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10, 4, false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20, 2, false, false));
                    inv.clear(3);
                    player.playSound(player.getLocation(), Sound.ENTITY_IRON_GOLEM_REPAIR, 1, 1);
                } else if (equals(item, ItemRegistry.ABILITY_SelfDestruct)) {
                    player.setHealth(0);
                    world.createExplosion(local, 5f);
                } else if (equals(item, ItemRegistry.ABILITY_ShieldUp)) {
                    inv.setItemInOffHand(ItemRegistry.WEAPON_BunketShield);
                    inv.clear(5);
                }
                //hamood abilities
                else if (equals(item, ItemRegistry.ABILITY_Swift)) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 49, false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 60, 1, false, false));
                    inv.clear(5);
                } else if (equals(item, ItemRegistry.ABILITY_DuneSlice)) {
                    inv.setItem(0, ItemRegistry.WEAPON_DuneSlicer);
                    player.playSound(player.getLocation(), Sound.ITEM_TOTEM_USE, 1, 1);
                    inv.clear(4);
                }
                //archer abilities
                else if (equals(item, ItemRegistry.ABILITY_Fletch)) {
                    if (player.getInventory().contains(Material.ARROW)){
                        inv.addItem(new ItemStack(Material.ARROW, 2));
                        inv.clear(3);
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_FLETCHER, 1, 1);
                    } else {
                        inv.setItem(7, new ItemStack(Material.ARROW, 2));
                        inv.clear(3);
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_FLETCHER, 1, 1);
                    }
                } else if (equals(item, ItemRegistry.ABILITY_Snare)) {
                    //TODO finish snare
                    world.spawnEntity(local, EntityType.ARMOR_STAND);
                    inv.clear(4);
                } else if (equals(item, ItemRegistry.ABILITY_Quickshot)) {
                    inv.setItem(1, ItemRegistry.WEAPON_ArcherCrossbow);
                    inv.clear(5);
                    player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, 1, 1);
                }
                //gummy bear
                else if (equals(item, ItemRegistry.ABILITY_DefenseBear)) {
                    inv.clear(3);
                    inv.clear(4);
                    inv.clear(5);
                    inv.clear(9);;
                    inv.setChestplate(ItemRegistry.ARMOR_DefenseBearChestplate);
                    inv.setLeggings(ItemRegistry.ARMOR_DefenseBearLeggings);
                    inv.setBoots(ItemRegistry.ARMOR_DefenseBearBoots);
                    inv.setHelmet(ItemRegistry.SKULL_DefenseBear);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1, false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 2, false, false));
                    inv.setItem(5, ItemRegistry.ABILITY_ChaosZone);
                    player.playSound(player.getLocation(), Sound.ENTITY_IRON_GOLEM_ATTACK, 1, 1);
                } else if (equals(item, ItemRegistry.ABILITY_NormalBear)) {
                    inv.clear(4);
                    inv.clear(5);
                    inv.setChestplate(ItemRegistry.ARMOR_NormalBearChestplate);
                    inv.setLeggings(ItemRegistry.ARMOR_NormalBearLeggings);
                    inv.setBoots(ItemRegistry.ARMOR_NormalBearBoots);
                    inv.setHelmet(ItemRegistry.SKULL_GummyBear);
                    for(PotionEffect e : player.getActivePotionEffects()){
                        player.removePotionEffect(e.getType());
                    }
                    inv.setItem(3, ItemRegistry.ABILITY_DefenseBear);
                    inv.setItem(4, ItemRegistry.ABILITY_AttackBear);
                    inv.setItem(5, ItemRegistry.ABILITY_SpeedBear);
                } else if (equals(item, ItemRegistry.ABILITY_AttackBear)) {
                    inv.clear(3);
                    inv.clear(4);
                    inv.clear(5);
                    inv.clear(9);
                    inv.setChestplate(ItemRegistry.ARMOR_AttackBearChestplate);
                    inv.setLeggings(ItemRegistry.ARMOR_AttackBearLeggings);
                    inv.setBoots(ItemRegistry.ARMOR_AttackBearBoots);
                    inv.setHelmet(ItemRegistry.SKULL_AttackBear);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1, false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 2, false, false));
                    inv.setItem(5, ItemRegistry.ABILITY_Numb);
                    player.playSound(player.getLocation(), Sound.ENTITY_VINDICATOR_HURT, 1, 1);
                } else if (equals(item, ItemRegistry.ABILITY_SpeedBear)) {
                    inv.clear(3);
                    inv.clear(4);
                    inv.clear(5);
                    inv.clear(9);
                    inv.setChestplate(ItemRegistry.ARMOR_SpeedBearChestplate);
                    inv.setLeggings(ItemRegistry.ARMOR_SpeedBearLeggings);
                    inv.setBoots(ItemRegistry.ARMOR_SpeedBearBoots);
                    inv.setHelmet(ItemRegistry.SKULL_SpeedBear);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE, 2, false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 7, false, false));
                    inv.setItem(5, ItemRegistry.ABILITY_StinkBomb);
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_STARE, 1, 1);
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
