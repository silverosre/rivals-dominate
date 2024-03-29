package net.silveros.kits;

import net.silveros.entity.RivalsTags;
import net.silveros.entity.User;
import net.silveros.game.RivalsCore;
import net.silveros.main.RivalsPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Marker;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

public class KitArcher extends Kit {
    private static final int ARROWS_FLETCHED = 2;
    public static final int FROMABOVE_ARROWS = 12;
    public static final byte SLOT_FLETCH = 3;
    public static final byte SLOT_SNARE = 4;
    public static final byte SLOT_FROM_ABOVE = 5;

    public KitArcher(int id, String name) {
        super(id, name);
    }

    @Override
    public void activateKit(Player player, PlayerInventory inv) {
        super.activateKit(player, inv);

        inv.setItem(0, ItemRegistry.WEAPON_WoodenKnife);
        inv.setItem(1, ItemRegistry.WEAPON_ArcherBow);
        inv.setItem(SLOT_FLETCH, ItemRegistry.ABILITY_Fletch);
        inv.setItem(SLOT_SNARE, ItemRegistry.ABILITY_Snare);
        inv.setItem(SLOT_FROM_ABOVE, ItemRegistry.ABILITY_FromAbove);
        inv.setItem(7, ItemRegistry.WEAPON_ArcherArrows);

        inv.setHelmet(ItemRegistry.SKULL_Archer);
        inv.setChestplate(ItemRegistry.ARMOR_ArcherChestplate);
        inv.setLeggings(ItemRegistry.ARMOR_ArcherLeggings);
    }

    @Override
    public int getHealth() {
        return 16;
    }

    @Override
    public int getStartingEnergy() {
        return 3;
    }

    public static void activateFletch(World world, Location local, Player player, PlayerInventory inv, User user) {
        if (player.getInventory().contains(Material.ARROW)) {
            inv.addItem(new ItemStack(Material.ARROW, ARROWS_FLETCHED));
        } else {
            inv.setItem(7, new ItemStack(Material.ARROW, ARROWS_FLETCHED));
        }

        inv.clear(SLOT_FLETCH);
        world.playSound(local, Sound.ENTITY_VILLAGER_WORK_FLETCHER, 1, 1);
    }

    public static void activateSnare(World world, Location local, Player player, PlayerInventory inv, User user) {
        Marker snare = (Marker)world.spawnEntity(local, EntityType.MARKER);
        snare.addScoreboardTag(RivalsTags.SNARE_ENTITY);
        snare.addScoreboardTag(RivalsTags.REMOVABLE);
        RivalsCore.addEntryToTeam(user.getTeam(), snare);

        world.playSound(local, Sound.BLOCK_WET_GRASS_PLACE, 0.75f, 0.5f);
        inv.clear(SLOT_SNARE);
    }

    public static void activateFromAbove(World world, Location local, Player player, PlayerInventory inv, User user) {
        Item flare = world.dropItemNaturally(local, new ItemStack(user.getTeam(RivalsPlugin.core.TEAM_BLUE) ? Material.BLUE_CANDLE : Material.RED_CANDLE));
        flare.setPickupDelay(Integer.MAX_VALUE);
        flare.addScoreboardTag(RivalsTags.FLARE_ENTITY);
        flare.addScoreboardTag(RivalsTags.REMOVABLE);
        flare.setVelocity(local.getDirection().add(new Vector(0, 0.1f, 0)));
        flare.setInvulnerable(true);
        flare.setOwner(player.getUniqueId());

        world.playSound(local, Sound.ENTITY_TNT_PRIMED, 1, 1);
        world.playSound(local, Sound.BLOCK_ANVIL_PLACE, 0.75f, 0.25f);

        inv.clear(SLOT_FROM_ABOVE);
    }
}
