package net.silveros.events;

import net.silveros.entity.User;
import net.silveros.kits.*;
import net.silveros.utility.Color;
import net.silveros.utility.Util;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

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
                                    KitBunket.activateShieldUp(world, local, player, inv, user);
                                    break;
                                case EMERGENCY_REPAIRS:
                                    KitBunket.activateEmergencyRepairs(world, local, player, inv, user);
                                    break;
                                case SELF_DESTRUCT:
                                    KitBunket.activateSelfDestruct(world, local, player, inv, user);
                                    break;
                                case FLETCH:
                                    KitArcher.activateFletch(world, local, player, inv, user);
                                    break;
                                case SNARE:
                                    KitArcher.activateSnare(world, local, player, inv, user);
                                    break;
                                case FROM_ABOVE:
                                    KitArcher.activateFromAbove(world, local, player, inv, user);
                                    break;
                                case SWIFT:
                                    KitRogue.activateSwift(world, local, player, inv, user);
                                    break;
                                case INCANTATION:
                                    KitRogue.activateIncantation(world, local, player, inv, user);
                                    break;
                                case CURSE:
                                    KitRogue.activateCurse(world, local, player, inv, user);
                                    break;
                                case HEROBRINE_POWER:
                                    KitHerobrine.activateHerobrinePower(world, local, player, inv, user);
                                    break;
                                case FOG_CLOAK:
                                    KitHerobrine.activateFogCloak(world, local, player, inv, user);
                                    break;
                                case UNCLOAK:
                                    KitHerobrine.activateUncloak(world, local, player, inv, user);
                                    break;
                                case ZAP:
                                    KitWizard.activateZap(world, local, player, inv, user);
                                    break;
                                case FIREBALL:
                                    KitWizard.activateFireball(world, local, player, inv, user);
                                    break;
                                case FREEZE:
                                    KitWizard.activateFreeze(world, local, player, inv, user);
                                    break;
                                case STEAL:
                                    KitGoblin.activateSteal(world, local, player, inv, user);
                                    break;
                                case GIVE:
                                    KitGoblin.activateGive(world, local, player, inv, user);
                                    break;
                                case SWARM:
                                    KitGoblin.activateSwarm(world, local, player, inv, user);
                                    break;
                                case NORMAL_BEAR:
                                    KitGummyBear.activateNormalBear(world, local, player, inv, user);
                                    break;
                                case DEFENSE_BEAR:
                                    KitGummyBear.activateDefenseBear(world, local, player, inv, user);
                                    break;
                                case CHAOS_ZONE:
                                    KitGummyBear.activateChaosZone(world, local, player, inv, user);
                                    break;
                                case ATTACK_BEAR:
                                    KitGummyBear.activateAttackBear(world, local, player, inv, user);
                                    break;
                                case NUMB:
                                    KitGummyBear.activateNumb(world, local, player, inv, user);
                                    break;
                                case SPEED_BEAR:
                                    KitGummyBear.activateSpeedBear(world, local, player, inv, user);
                                    break;
                                case STINK_BOMB:
                                    KitGummyBear.activateStinkBomb(world, local, player, inv, user);
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
}
