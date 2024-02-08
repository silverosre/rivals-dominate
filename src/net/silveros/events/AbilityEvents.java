package net.silveros.events;

import net.silveros.entity.User;
import net.silveros.kits.ItemAbility;
import net.silveros.kits.ItemRegistry;
import net.silveros.main.RivalsPlugin;
import net.silveros.utility.Color;
import net.silveros.utility.Util;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

public class AbilityEvents implements Listener, Color {
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
    public static void onPlayerHit(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();
        if (entity.getType().equals(EntityType.PLAYER)) {
            Player player = (Player) event.getEntity();
            //checks if player being damaged has uncloak --roasty
            if (player.getInventory().contains(ItemRegistry.ABILITY_Uncloak)) {
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    player.removePotionEffect(effect.getType());
                }
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_HURT, 1, 1);
            }
        }
        if (damager.getType().equals(EntityType.PLAYER)) {
            Player player = (Player) event.getDamager();
            //checks if a cloaked herobrine has hit an entity
            if (player.getInventory().contains(ItemRegistry.ABILITY_Uncloak)) {
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    player.removePotionEffect(effect.getType());
                }
            }
        }
    }

    @EventHandler
    public static void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        User user = Util.getUserFromId(player.getUniqueId());
        PlayerInventory inv = player.getInventory();
        Location local = player.getLocation();
        ItemStack item = event.getItem();
        World world = player.getWorld();

        if (eitherAction(event)) {
            if (item != null) {
                if (item.getType() == Material.ENCHANTED_BOOK) {
                    ItemMeta meta = item.getItemMeta();
                    String key = meta.getDisplayName();

                    ItemAbility ability = ItemRegistry.abilityMap.get(key);
                    if (ability != null) {
                        if (user.getTotalEnergy() >= ability.energyCost) {
                            user.removeEnergy(ability.energyCost);

                            switch (ability.ability) {
                                case SHIELD_UP:
                                    inv.setItemInOffHand(ItemRegistry.WEAPON_BunketShield);
                                    inv.clear(5);
                                    break;
                                case EMERGENCY_REPAIRS:
                                    //TODO make repair work

                                    //remove if you dont like my implementation --Roasty
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10, 4, false, false));
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20, 2, false, false));
                                    inv.clear(3);
                                    player.playSound(player.getLocation(), Sound.ENTITY_IRON_GOLEM_REPAIR, 1, 1);
                                    break;
                                case SELF_DESTRUCT:
                                    player.setHealth(0);
                                    world.createExplosion(local, 5f);
                                    break;
                                case FLETCH:
                                    if (player.getInventory().contains(Material.ARROW)) {
                                        inv.addItem(new ItemStack(Material.ARROW, 2));
                                        inv.clear(3);
                                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_FLETCHER, 1, 1);
                                    } else {
                                        inv.setItem(7, new ItemStack(Material.ARROW, 2));
                                        inv.clear(3);
                                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_FLETCHER, 1, 1);
                                    }
                                    break;
                                case SNARE:
                                    world.spawnEntity(local, EntityType.ARMOR_STAND);
                                    inv.clear(4);
                                    break;
                                case FROM_ABOVE:
                                    Item flare = world.dropItemNaturally(local, new ItemStack(user.getTeam(RivalsPlugin.core.TEAM_BLUE) ? Material.BLUE_CANDLE : Material.RED_CANDLE));
                                    flare.setPickupDelay(Integer.MAX_VALUE);
                                    flare.addScoreboardTag("rivals.archer_flare");
                                    flare.setVelocity(local.getDirection().add(new Vector(0, 0.1f, 0)));

                                    world.playSound(local, Sound.ENTITY_TNT_PRIMED, 1, 1);
                                    world.playSound(local, Sound.BLOCK_ANVIL_PLACE, 0.75f, 0.25f);

                                    inv.clear(5);
                                    break;
                                case SWIFT:
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 49, false, false));
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 60, 1, false, false));
                                    inv.clear(5);
                                    break;
                                case DUNE_SLICE:
                                    inv.setItem(0, ItemRegistry.WEAPON_DuneSlicer);
                                    player.playSound(player.getLocation(), Sound.ITEM_TOTEM_USE, 1, 1);
                                    inv.clear(4);
                                    break;
                                case NORMAL_BEAR:
                                    inv.clear(4);
                                    inv.clear(5);
                                    inv.setChestplate(ItemRegistry.ARMOR_NormalBearChestplate);
                                    inv.setLeggings(ItemRegistry.ARMOR_NormalBearLeggings);
                                    inv.setBoots(ItemRegistry.ARMOR_NormalBearBoots);
                                    inv.setHelmet(ItemRegistry.SKULL_GummyBear);
                                    for (PotionEffect e : player.getActivePotionEffects()) {
                                        player.removePotionEffect(e.getType());
                                    }

                                    //why not just player.getActivePotionEffects().clear()
                                    inv.setItem(3, ItemRegistry.ABILITY_DefenseBear);
                                    inv.setItem(4, ItemRegistry.ABILITY_AttackBear);
                                    inv.setItem(5, ItemRegistry.ABILITY_SpeedBear);
                                    break;
                                case DEFENSE_BEAR:
                                    inv.clear(3);
                                    inv.clear(4);
                                    inv.clear(5);
                                    inv.clear(9);
                                    ;
                                    inv.setChestplate(ItemRegistry.ARMOR_DefenseBearChestplate);
                                    inv.setLeggings(ItemRegistry.ARMOR_DefenseBearLeggings);
                                    inv.setBoots(ItemRegistry.ARMOR_DefenseBearBoots);
                                    inv.setHelmet(ItemRegistry.SKULL_DefenseBear);
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1, false, false));
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 2, false, false));
                                    inv.setItem(5, ItemRegistry.ABILITY_ChaosZone);
                                    world.playSound(player.getLocation(), Sound.ENTITY_IRON_GOLEM_ATTACK, 1, 1);
                                    break;
                                case ATTACK_BEAR:
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
                                    world.playSound(player.getLocation(), Sound.ENTITY_VINDICATOR_HURT, 1, 1);
                                    break;
                                case SPEED_BEAR:
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
                                    world.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_STARE, 1, 1);
                                    break;
                                //herobrine
                                case HEROBRINE_POWER:
                                    inv.clear(0);
                                    inv.clear(1);
                                    inv.clear(7);
                                    inv.clear(3);
                                    inv.setItem(0, ItemRegistry.WEAPON_HerobrinePowerAxe);
                                    inv.setItem(1, ItemRegistry.WEAPON_HerobrinePowerBow);
                                    inv.setItem(7, ItemRegistry.WEAPON_HerobrineArrows);
                                    break;
                                case FOG_CLOAK:
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, false, false));
                                    inv.clear(1);
                                    inv.clear(4);
                                    inv.clear(7);
                                    inv.clear(36);
                                    inv.clear(37);
                                    inv.clear(38);
                                    inv.clear(39);
                                    player.spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, player.getLocation(), 20);
                                    player.playSound(player.getLocation(), Sound.AMBIENT_CAVE, 1, 1);
                                    break;
                                case UNCLOAK:
                                    for(PotionEffect e : player.getActivePotionEffects()) {
                                        player.removePotionEffect(e.getType());
                                    }
                                    break;
                            }
                        } else {
                            player.playSound(player, Sound.ENTITY_VILLAGER_NO, 0.75f, 1);
                            player.sendMessage(RED + "Not enough energy!");
                        }
                    }
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
