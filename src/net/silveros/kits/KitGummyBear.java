package net.silveros.kits;

import net.silveros.entity.RivalsTags;
import net.silveros.entity.User;
import net.silveros.game.RivalsCore;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Marker;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.awt.*;

public class KitGummyBear extends Kit {
    public KitGummyBear(int id, String name) {
        super(id, name);
        this.foodCount = 5;
    }

    @Override
    public void activateKit(Player player, PlayerInventory inv) {
        super.activateKit(player, inv);

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
        player.setMaxHealth(18);
        player.playSound(local, Sound.BLOCK_BEACON_DEACTIVATE, 1, 1);
        user.bearAbility = true;
    }

    public static void activateDefenseBear(World world, Location local, Player player, PlayerInventory inv, User user){
        inv.clear(3);
        inv.clear(4);
        inv.clear(5);
        inv.clear(9);
        inv.setHelmet(ItemRegistry.SKULL_DefenseBear);
        inv.setChestplate(ItemRegistry.ARMOR_DefenseBearChestplate);
        inv.setLeggings(ItemRegistry.ARMOR_DefenseBearLeggings);
        inv.setBoots(ItemRegistry.ARMOR_DefenseBearBoots);
        player.setMaxHealth(20);
        //sets defense bear to max health if normal bear is at full health
        if (player.getHealth() > 17) {
            player.setHealth(20);
        }
        player.playSound(local, Sound.BLOCK_SUSPICIOUS_SAND_BREAK, 1, 1);
        user.bearAbility = true;
    }

    public static void activateChaosZone(World world, Location local, Player player, PlayerInventory inv, User user){
        double x = local.getBlockX();
        double y = local.getBlockY();
        double z = local.getBlockZ();
        double dX = user.randomPosition(-0.7, 0.7);
        double dY = user.randomPosition(-0.7, 0.7);
        double dZ = user.randomPosition(-0.7, 0.7);
        double dA = user.randomPosition(-0.4, 0.4);

        inv.clear(5);
        Marker chaos_zone = (Marker)world.spawnEntity(local, EntityType.MARKER);
        chaos_zone.addScoreboardTag(RivalsTags.CHAOS_ZONE_ENTITY);
        RivalsCore.addEntryToTeam(user.getTeam(), chaos_zone);
        player.getActivePotionEffects().clear();
        player.playSound(local, Sound.BLOCK_ANVIL_PLACE, 1, 1);
        player.spawnParticle(Particle.REDSTONE, x, y + 1, z, 10, dX, dY, dZ, dA, new Particle.DustOptions(Color.GRAY, 10));
    }

    public static void activateAttackBear(World world, Location local, Player player, PlayerInventory inv, User user) {
        inv.clear(3);
        inv.clear(4);
        inv.clear(5);
        inv.clear(9);
        inv.setHelmet(ItemRegistry.SKULL_AttackBear);
        inv.setChestplate(ItemRegistry.ARMOR_AttackBearChestplate);
        inv.setLeggings(ItemRegistry.ARMOR_AttackBearLeggings);
        inv.setBoots(ItemRegistry.ARMOR_AttackBearBoots);
        player.playSound(local, Sound.ENTITY_VINDICATOR_HURT, 1, 1);
        player.setMaxHealth(16);

        user.bearAbility = true;
    }

    public static void activateNumb(World world, Location local, Player player, PlayerInventory inv, User user) {
        inv.clear(5);
        player.playSound(local, Sound.ITEM_TRIDENT_RIPTIDE_2, 1, 1);
        inv.setItem(10, ItemRegistry.ITEM_Numbness);
    }

    public static void activateSpeedBear(World world, Location local, Player player, PlayerInventory inv, User user) {
        inv.clear(3);
        inv.clear(4);
        inv.clear(5);
        inv.clear(9);
        inv.setHelmet(ItemRegistry.SKULL_SpeedBear);
        inv.setChestplate(ItemRegistry.ARMOR_SpeedBearChestplate);
        inv.setLeggings(ItemRegistry.ARMOR_SpeedBearLeggings);
        inv.setBoots(ItemRegistry.ARMOR_SpeedBearBoots);
        player.playSound(local, Sound.ENTITY_ELDER_GUARDIAN_CURSE, 1, 1);
        player.setMaxHealth(12);

        user.bearAbility = true;
    }

    public static void activateStinkBomb(World world, Location local, Player player, PlayerInventory inv, User user) {
        inv.clear(5);
        Marker stink = (Marker)world.spawnEntity(local, EntityType.MARKER);
        stink.addScoreboardTag(RivalsTags.STINK_BOMB_ENTITY);
        RivalsCore.addEntryToTeam(user.getTeam(), stink);
        player.playSound(local, Sound.ENTITY_WITHER_SHOOT, 1, 1);
    }

    @Override
    public int getHealth() {
        return 18;
    }

    @Override
    public int getStartingEnergy() {
        return 3;
    }
}
