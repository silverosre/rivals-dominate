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

public class KitBandit extends Kit implements Color {
    public static final byte SLOT_STEAL = 3;
    public static final byte SLOT_GIVE = 4;
    public static final byte SLOT_RELOAD = 5;

    public KitBandit(int id, String name) {super(id, name);}

    @Override
    public void activateKit(Player player, PlayerInventory inv) {
        super.activateKit(player, inv);

        inv.setItem(0, ItemRegistry.WEAPON_Sixshooter);
        inv.setItem(SLOT_STEAL, ItemRegistry.ABILITY_Steal);
        inv.setItem(SLOT_GIVE, ItemRegistry.ABILITY_Give);
        inv.setItem(SLOT_RELOAD, ItemRegistry.ABILITY_Reload);

        inv.setChestplate(ItemRegistry.ARMOR_BanditChestplate);
        inv.setLeggings(ItemRegistry.ARMOR_BanditLeggings);
        inv.setBoots(ItemRegistry.ARMOR_BanditBoots);
        inv.setHelmet(ItemRegistry.SKULL_Bandit);
    }
    @Override
    public int getHealth() {return 10;}

    @Override
    public int getStartingEnergy() {return 0;}

    public static void activateSteal(World world, Location local, Player player, PlayerInventory inv, User user) {
        for (Entity e : player.getNearbyEntities(2.5, 1, 2.5)) {
            if (e instanceof Player) {
                Player other = (Player)e;
                User otherUser = Util.getUserFromId(other.getUniqueId());

                if (!RivalsCore.matchingTeams(user.getTeam(), other, player)) {
                    if (otherUser.currentKit == Kit.BANDIT.kitID) {
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
                    if (otherUser.currentKit == Kit.BANDIT.kitID) {
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
    public static void activateReload(World world, Location local, Player player, PlayerInventory inv, User user) {
        if (user.bulletCount < 6) {
            user.bulletCount++;
            user.bulletCount++;
        }
        player.setLevel(user.bulletCount);
    }

}
