package net.silveros.kits;

import net.silveros.entity.User;
import net.silveros.game.RivalsCore;
import net.silveros.utility.Color;
import net.silveros.utility.Util;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class KitGoblin extends Kit implements Color {
    public static final byte SLOT_STEAL = 3;
    public static final byte SLOT_GIVE = 4;
    public static final byte SLOT_SWARM = 5;

    public KitGoblin(int id, String name) {
        super(id, name);
    }

    @Override
    public void activateKit(Player player, PlayerInventory inv) {
        super.activateKit(player, inv);

        inv.setItem(SLOT_STEAL, ItemRegistry.ABILITY_Steal);
        inv.setItem(SLOT_GIVE, ItemRegistry.ABILITY_Give);
        /*inv.setItem(SLOT_SWARM, ItemRegistry.ABILITY_Swarm);

        inv.setChestplate(ItemRegistry.ARMOR_GoblinChestplate);
        inv.setLeggings(ItemRegistry.ARMOR_GoblinLeggings);
        inv.setBoots(ItemRegistry.ARMOR_GoblinBoots);
        inv.setHelmet(ItemRegistry.SKULL_Goblin);*/

        //player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, PotionEffect.INFINITE_DURATION, 3, false, false));
    }

    @Override
    public int getHealth() {
        return 10;
    }

    @Override
    public int getStartingEnergy() {
        return 0;
    }

    public static void activateSteal(World world, Location local, Player player, PlayerInventory inv, User user) {
        for (Entity e : player.getNearbyEntities(2.5, 1, 2.5)) {
            if (e instanceof Player) {
                Player other = (Player)e;
                User otherUser = Util.getUserFromId(other.getUniqueId());

                if (!RivalsCore.matchingTeams(user.getTeam(), other, player)) {
                    if (otherUser.currentKit == Kit.GOBLIN.kitID) {
                        continue;
                    }
                    inv.clear(SLOT_STEAL);
                    if (otherUser.getTotalEnergy() >= 1) {
                        otherUser.removeEnergy(1);
                        user.addEnergy(1);

                        world.playSound(local, Sound.ENTITY_WITCH_CELEBRATE, 0.75f, 1.75f);

                        world.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                        world.playSound(player, Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 2);
                        return;
                    }
                }
            }
        }

        player.playSound(player, Sound.ENTITY_VILLAGER_NO, 0.75f, 1);
        player.sendMessage(RED + "No player or energy to steal from within range!");
    }

    public static void activateGive(World world, Location local, Player player, PlayerInventory inv, User user) {
        for (Entity e : player.getNearbyEntities(2.5, 1, 2.5)) {
            if (e instanceof Player) {
                Player other = (Player)e;
                User otherUser = Util.getUserFromId(other.getUniqueId());


                if (RivalsCore.matchingTeams(user.getTeam(), other, player)) {
                    if (otherUser.currentKit == Kit.GOBLIN.kitID) {
                        continue;
                    }
                    inv.clear(SLOT_GIVE);
                    otherUser.addEnergy(1);

                    world.playSound(local, Sound.ENTITY_WITCH_CELEBRATE, 0.75f, 1.75f);

                    world.playSound(other, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                    world.playSound(other, Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 2);
                    return;
                }
            }
        }

        player.playSound(player, Sound.ENTITY_VILLAGER_NO, 0.75f, 1);
        player.sendMessage(RED + "No player to give energy to within range!");
        user.addEnergy(1);
    }

    /*public static void activateSwarm(World world, Location local, Player player, PlayerInventory inv, User user) {
        for (Entity e : player.getNearbyEntities(2.5, 1, 2.5)) {
            if (e instanceof Player) {
                Player other = (Player)e;
                User otherUser = Util.getUserFromId(other.getUniqueId());

                if (RivalsCore.matchingTeams(user.getTeam(), other, player)) {
                    if (otherUser.currentKit == Kit.GOBLIN.kitID) {
                        continue;
                    }

                    inv.clear(SLOT_SWARM);

                    other.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 0, false, false));
                    other.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 0, false, false));

                    world.playSound(local, Sound.EVENT_RAID_HORN, 1.5f, 1);
                    world.playSound(local, Sound.ENTITY_WITHER_SPAWN, 0.75f, 1);
                    return;
                }
            }
        }

        player.playSound(player, Sound.ENTITY_VILLAGER_NO, 0.75f, 1);
        player.sendMessage(RED + "No player to give Swarm effects to within range!");
        user.addEnergy(ItemRegistry.ABILITY_Swarm.energyCost);
    }*/
}
