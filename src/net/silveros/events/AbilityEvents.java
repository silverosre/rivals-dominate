package net.silveros.events;

import net.silveros.entity.RivalsTags;
import net.silveros.entity.User;
import net.silveros.game.RivalsCore;
import net.silveros.kits.ItemAbility;
import net.silveros.kits.ItemRegistry;
import net.silveros.main.RivalsPlugin;
import net.silveros.utility.Color;
import net.silveros.utility.Util;
import org.bukkit.*;
import org.bukkit.entity.*;
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

    /*@EventHandler
    public static void onPlayerHit(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity attacker = event.getDamager();
        Player player = (Player) event.getEntity();
        Player attackplayer = (Player) event.getDamager();
        User user = Util.getUserFromId(player.getUniqueId());

        if (entity.getType().equals(EntityType.PLAYER)) {
            //checks if player being damaged has uncloak
            if (player.getInventory().contains(ItemRegistry.ABILITY_Uncloak)) {
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    player.removePotionEffect(effect.getType());
                }
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_SCREAM, 1, 1);
            }
            //damage handler for numb
            if (player.getInventory().contains(ItemRegistry.ITEM_Numbness)) {
                user.dealtDamage = event.getDamage();
                user.numbDamage = user.numbDamage + user.dealtDamage;
            }
        }

        if (attacker.getType().equals(EntityType.PLAYER)) {
            //checks if a cloaked herobrine has hit an entity
            if (attackplayer.getInventory().contains(ItemRegistry.ABILITY_Uncloak)) {
                for (PotionEffect effect : attackplayer.getActivePotionEffects()) {
                    attackplayer.removePotionEffect(effect.getType());
                }
            }
        }
    }*/

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
                                    world.playSound(local, Sound.ENTITY_VILLAGER_WORK_TOOLSMITH, 1, 1);
                                    inv.setItemInOffHand(ItemRegistry.WEAPON_BunketShield);
                                    inv.clear(5);
                                    break;
                                case EMERGENCY_REPAIRS:
                                    //TODO make repair work

                                    //remove if you dont like my implementation --Roasty
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10, 4, false, false));
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20, 2, false, false));
                                    inv.clear(3);
                                    world.playSound(local, Sound.ENTITY_IRON_GOLEM_REPAIR, 1, 1);
                                    break;
                                case SELF_DESTRUCT:
                                    player.setHealth(0);
                                    world.createExplosion(local, 5f);
                                    break;
                                case FLETCH:
                                    if (player.getInventory().contains(Material.ARROW)) {
                                        inv.addItem(new ItemStack(Material.ARROW, 2));
                                        inv.clear(3);
                                        world.playSound(local, Sound.ENTITY_VILLAGER_WORK_FLETCHER, 1, 1);
                                    } else {
                                        inv.setItem(7, new ItemStack(Material.ARROW, 2));
                                        inv.clear(3);
                                        world.playSound(local, Sound.ENTITY_VILLAGER_WORK_FLETCHER, 1, 1);
                                    }
                                    break;
                                case SNARE:
                                    Marker snare = (Marker)world.spawnEntity(local, EntityType.MARKER);
                                    snare.addScoreboardTag(RivalsTags.SNARE_ENTITY);
                                    RivalsCore.addEntryToTeam(user.getTeam(), snare);

                                    world.playSound(local, Sound.BLOCK_WET_GRASS_PLACE, 0.75f, 0.5f);
                                    inv.clear(4);
                                    break;
                                case FROM_ABOVE:
                                    Item flare = world.dropItemNaturally(local, new ItemStack(user.getTeam(RivalsPlugin.core.TEAM_BLUE) ? Material.BLUE_CANDLE : Material.RED_CANDLE));
                                    flare.setPickupDelay(Integer.MAX_VALUE);
                                    flare.addScoreboardTag(RivalsTags.FLARE_ENTITY);
                                    flare.setVelocity(local.getDirection().add(new Vector(0, 0.1f, 0)));
                                    flare.setInvulnerable(true);
                                    flare.setOwner(player.getUniqueId());

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
                                    world.playSound(local, Sound.ITEM_TOTEM_USE, 1, 1);
                                    inv.clear(4);
                                    break;
                                case PHARAOHS_CURSE:
                                    Item curse = world.dropItemNaturally(local, new ItemStack(Material.SUSPICIOUS_SAND));
                                    curse.setPickupDelay(Integer.MAX_VALUE);
                                    curse.addScoreboardTag(RivalsTags.PHARAOHS_CURSE_ENTITY);
                                    curse.setVelocity(local.getDirection().multiply(0.75));
                                    curse.setInvulnerable(true);
                                    RivalsCore.addEntryToTeam(user.getTeam(), curse);

                                    world.playSound(local, Sound.ENTITY_TNT_PRIMED, 1, 1);

                                    inv.clear(3);
                                    break;
                                case NORMAL_BEAR:
                                    inv.clear(4);
                                    inv.clear(5);
                                    inv.setChestplate(ItemRegistry.ARMOR_NormalBearChestplate);
                                    inv.setLeggings(ItemRegistry.ARMOR_NormalBearLeggings);
                                    inv.setBoots(ItemRegistry.ARMOR_NormalBearBoots);
                                    inv.setHelmet(ItemRegistry.SKULL_GummyBear);
                                    player.playSound(local, Sound.BLOCK_BEACON_DEACTIVATE, 1, 1);

                                    for (PotionEffect e : player.getActivePotionEffects()) {
                                        player.removePotionEffect(e.getType());
                                    }
                                    break;
                                case DEFENSE_BEAR:
                                    user.bearAbility = true;
                                    inv.clear(3);
                                    inv.clear(4);
                                    inv.clear(5);
                                    inv.clear(9);
                                    inv.setChestplate(ItemRegistry.ARMOR_DefenseBearChestplate);
                                    inv.setLeggings(ItemRegistry.ARMOR_DefenseBearLeggings);
                                    inv.setBoots(ItemRegistry.ARMOR_DefenseBearBoots);
                                    inv.setHelmet(ItemRegistry.SKULL_DefenseBear);
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, PotionEffect.INFINITE_DURATION, 1, false, false));
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, PotionEffect.INFINITE_DURATION, 2, false, false));
                                    world.playSound(local, Sound.ENTITY_IRON_GOLEM_ATTACK, 1, 1);
                                    break;
                                case CHAOS_ZONE:
                                    inv.clear(5);
                                    Marker chaos = (Marker)world.spawnEntity(local, EntityType.MARKER);
                                    chaos.addScoreboardTag(RivalsTags.CHAOS_ZONE_ENTITY);
                                    RivalsCore.addEntryToTeam(user.getTeam(), chaos);
                                    player.playSound(local, Sound.ENTITY_WITHER_DEATH, 1, 1);
                                    world.spawnParticle(Particle.REDSTONE, local.getBlockX()+user.randomPosition(-1, 1), local.getBlockY()+1, local.getBlockZ()+user.randomPosition(-1, 1), 1, 0.001, 0,0,0, new Particle.DustOptions(org.bukkit.Color.GRAY, 10));
                                    world.spawnParticle(Particle.REDSTONE, local.getBlockX()+user.randomPosition(-1, 1), local.getBlockY()+1, local.getBlockZ()+user.randomPosition(-1, 1), 1, 0.001, 0,0,0, new Particle.DustOptions(org.bukkit.Color.GRAY, 10));
                                    world.spawnParticle(Particle.REDSTONE, local.getBlockX()+user.randomPosition(-1, 1), local.getBlockY()+1, local.getBlockZ()+user.randomPosition(-1, 1), 1, 0.001, 0,0,0, new Particle.DustOptions(org.bukkit.Color.GRAY, 10));
                                    break;
                                case ATTACK_BEAR:
                                    user.bearAbility = true;
                                    inv.clear(3);
                                    inv.clear(4);
                                    inv.clear(5);
                                    inv.clear(9);
                                    inv.setChestplate(ItemRegistry.ARMOR_AttackBearChestplate);
                                    inv.setLeggings(ItemRegistry.ARMOR_AttackBearLeggings);
                                    inv.setBoots(ItemRegistry.ARMOR_AttackBearBoots);
                                    inv.setHelmet(ItemRegistry.SKULL_AttackBear);
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, PotionEffect.INFINITE_DURATION, 1, false, false));
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, PotionEffect.INFINITE_DURATION, 2, false, false));
                                    world.playSound(local, Sound.ENTITY_VINDICATOR_HURT, 1, 1);
                                    break;
                                case NUMB:
                                    inv.clear(5);
                                    inv.setItem(10, ItemRegistry.ITEM_Numbness);
                                    player.playSound(local, Sound.ITEM_TRIDENT_RIPTIDE_3, 1, 1);
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 99, false, false));
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 3, false, false));
                                    break;
                                case SPEED_BEAR:
                                    user.bearAbility = true;
                                    inv.clear(3);
                                    inv.clear(4);
                                    inv.clear(5);
                                    inv.clear(9);
                                    inv.setChestplate(ItemRegistry.ARMOR_SpeedBearChestplate);
                                    inv.setLeggings(ItemRegistry.ARMOR_SpeedBearLeggings);
                                    inv.setBoots(ItemRegistry.ARMOR_SpeedBearBoots);
                                    inv.setHelmet(ItemRegistry.SKULL_SpeedBear);
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, PotionEffect.INFINITE_DURATION, 2, false, false));
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, PotionEffect.INFINITE_DURATION, 7, false, false));
                                    world.playSound(local, Sound.ENTITY_ENDERMAN_STARE, 1, 1);
                                    break;
                                case STINK_BOMB:
                                    inv.clear(5);
                                    Marker stink = (Marker)world.spawnEntity(local, EntityType.MARKER);
                                    stink.addScoreboardTag(RivalsTags.STINK_BOMB_ENTITY);
                                    RivalsCore.addEntryToTeam(user.getTeam(), stink);
                                    player.playSound(local, Sound.ENTITY_WITHER_SHOOT, 1, 1);
                                    break;
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
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, PotionEffect.INFINITE_DURATION, 0, false, false));
                                    inv.clear(1);
                                    inv.clear(4);
                                    inv.clear(7);
                                    inv.clear(36);
                                    inv.clear(37);
                                    inv.clear(38);
                                    inv.clear(39);
                                    player.spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, local, 20, 0, 0, 0, 0);
                                    world.playSound(local, Sound.AMBIENT_CAVE, 1, 1);
                                    break;
                                case UNCLOAK:
                                    for (PotionEffect e : player.getActivePotionEffects()) {
                                        player.removePotionEffect(e.getType());
                                    }
                                    player.spawnParticle(Particle.SMOKE_LARGE, local, 30, 0, 0, 0, 0);
                                    world.playSound(local, Sound.ENTITY_ENDERMAN_SCREAM, 1, 1);
                                    break;
                                case ZAP:
                                    Item zap = world.dropItemNaturally(local, new ItemStack(Material.REDSTONE_LAMP));
                                    zap.setPickupDelay(Integer.MAX_VALUE);
                                    zap.addScoreboardTag(RivalsTags.ZAP_ENTITY);
                                    zap.setVelocity(local.getDirection().multiply(0.75));
                                    zap.setInvulnerable(true);
                                    RivalsCore.addEntryToTeam(user.getTeam(), zap);

                                    world.playSound(local, Sound.BLOCK_FIRE_EXTINGUISH, 1, 1);
                                    inv.clear(2);
                                    break;
                                case FIREBALL:
                                    Fireball fireball = (Fireball)world.spawnEntity(local.add(0, 1.25, 0), EntityType.FIREBALL);
                                    fireball.setDirection(local.getDirection());
                                    fireball.setYield(1.25f);
                                    fireball.setShooter(player);
                                    fireball.setVelocity(local.getDirection().multiply(0.25));

                                    world.playSound(local, Sound.ENTITY_BLAZE_SHOOT, 0.75f, 1);
                                    inv.clear(3);
                                    break;
                                case FREEZE:
                                    Marker freeze = (Marker)world.spawnEntity(local, EntityType.MARKER);

                                    for (Entity e : freeze.getNearbyEntities(2, 1, 2)) {
                                        if (e instanceof Player) {
                                            Player other = (Player)e;
                                            if (!RivalsCore.matchingTeams(user.getTeam(), player, other)) {
                                                other.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80, 1, false, false));
                                            }
                                        }
                                    }

                                    world.spawnParticle(Particle.SNOWFLAKE, local, 200, 1.25, 0, 1.25, 0);
                                    world.spawnParticle(Particle.REDSTONE, local, 200, 1.25, 0, 1.25, new Particle.DustOptions(org.bukkit.Color.fromRGB(0xE1FFFF), 1));

                                    world.playSound(local, Sound.ENTITY_PLAYER_HURT_FREEZE, 0.75f, 1);
                                    inv.clear(4);
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
