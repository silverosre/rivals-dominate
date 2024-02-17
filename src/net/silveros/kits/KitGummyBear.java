package net.silveros.kits;

import net.silveros.entity.RivalsTags;
import net.silveros.entity.User;
import net.silveros.game.RivalsCore;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Marker;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class KitGummyBear extends Kit {
    public KitGummyBear(int id, String name) {
        super(id, name);
        this.foodCount = 5;
    }

    @Override
    public void activateKit(PlayerInventory inv) {
        super.activateKit(inv);

        inv.setItem(0, ItemRegistry.WEAPON_GummyClub);
        inv.setItem(3, ItemRegistry.ABILITY_DefenseBear);
        inv.setItem(4, ItemRegistry.ABILITY_AttackBear);
        inv.setItem(5, ItemRegistry.ABILITY_SpeedBear);
        inv.setItem(9, ItemRegistry.ITEM_GummyEssence);
        inv.setChestplate(ItemRegistry.ARMOR_NormalBearChestplate);
        inv.setLeggings(ItemRegistry.ARMOR_NormalBearLeggings);
        inv.setBoots(ItemRegistry.ARMOR_NormalBearBoots);
        inv.setHelmet(ItemRegistry.SKULL_GummyBear);
    }

    public static void activateNormalBear(World world, Location local, Player player, PlayerInventory inv, User user){
        inv.clear(4);
        inv.clear(5);
        inv.clear(9);
        inv.setHelmet(ItemRegistry.SKULL_GummyBear);
        inv.setChestplate(ItemRegistry.ARMOR_NormalBearChestplate);
        inv.setLeggings(ItemRegistry.ARMOR_NormalBearLeggings);
        inv.setBoots(ItemRegistry.ARMOR_NormalBearBoots);
        user.bearAbility = true;
    }
    public static void activateDefenseBear(World world, Location local, Player player, PlayerInventory inv, User user){
        inv.clear(4);
        inv.clear(5);
        inv.clear(9);
        inv.setHelmet(ItemRegistry.SKULL_DefenseBear);
        inv.setChestplate(ItemRegistry.ARMOR_DefenseBearChestplate);
        inv.setLeggings(ItemRegistry.ARMOR_DefenseBearLeggings);
        inv.setBoots(ItemRegistry.ARMOR_DefenseBearBoots);
        user.bearAbility = true;
    }
    public static void activateChaosZone(World world, Location local, Player player, PlayerInventory inv, User user){
        inv.clear(5);
        Marker chaos_zone = (Marker)world.spawnEntity(local, EntityType.MARKER);
        chaos_zone.addScoreboardTag(RivalsTags.CHAOS_ZONE_ENTITY);
        RivalsCore.addEntryToTeam(user.getTeam(), chaos_zone);
    }

    @Override
    public int getStartingEnergy() {
        return 3;
    }
}
