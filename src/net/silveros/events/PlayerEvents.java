package net.silveros.events;

import net.silveros.entity.User;
import net.silveros.game.RivalsCore;
import net.silveros.kits.ItemRegistry;
import net.silveros.main.RivalsPlugin;
import net.silveros.utility.Color;
import net.silveros.utility.Util;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class PlayerEvents implements Listener, Color {
    private List<Integer> bullets = new ArrayList<>();
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

    @EventHandler(ignoreCancelled = true)
    public void onDamage(EntityDamageEvent event) {
        for(Entity entity : event.getEntity().getNearbyEntities(5, 5, 5)) {
            if (!(entity instanceof Firework)) {
                continue;
            }

            if (RivalsCore.fireworks.contains(entity)) {
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onExplosion(FireworkExplodeEvent event) {
        final Firework firework = event.getEntity();
        RivalsCore.fireworks.add(firework);
        new BukkitRunnable() {
            @Override
            public void run() {
                RivalsCore.fireworks.remove(firework);
            }
        }.runTaskLater(Util.getPlugin(), 5);
    }

    //prevent offhand from being used during game
    @EventHandler
    public static void onItemsSwapped(PlayerSwapHandItemsEvent event) {
        if (RivalsCore.gameInProgress) {
            event.setCancelled(true);
        }
    }

    //prevent items in inventory from being moved during game
    @EventHandler
    public static void onItemsMoved(InventoryClickEvent event) {
        if (RivalsCore.gameInProgress) {
            Entity e = event.getWhoClicked();
            if (e instanceof Player) {
                Player player = (Player)e;

                event.setCancelled(true);
                player.updateInventory();
            }
        }
    }

    //prevent items from being dropped during game
    @EventHandler
    public static void onItemDropped(PlayerDropItemEvent event) {
        if (RivalsCore.gameInProgress) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Player player = event.getPlayer();
        User user = Util.getUserFromId(player.getUniqueId());
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if (item != null) {
                if (user != null) {
                    if (!user.usedSixshooter) {
                        if (user.bulletCount > 0) {
                            if (item.equals(ItemRegistry.WEAPON_Sixshooter)) {
                                user.usedSixshooter = true;
                            }
                        } else {
                            player.sendMessage(RED + "No bullets!");
                            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                        }
                    }
                }
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
