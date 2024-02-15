package net.silveros.kits;

import net.silveros.entity.User;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class KitBunket extends Kit {
    public KitBunket(int id, String name) {
        super(id, name);
        this.foodCount = 4;
    }

    @Override
    public void activateKit(PlayerInventory inv) {
        super.activateKit(inv);

        inv.setItemInOffHand(ItemRegistry.WEAPON_BunketShield);
        inv.setItem(3, ItemRegistry.ABILITY_EmergencyRepairs);
        inv.setItem(4, ItemRegistry.ABILITY_SelfDestruct);
        inv.setItem(5, ItemRegistry.ABILITY_ShieldUp);
        inv.setChestplate(ItemRegistry.ARMOR_BunketChestplate);
        inv.setLeggings(ItemRegistry.ARMOR_BunketLeggings);
        inv.setBoots(ItemRegistry.ARMOR_BunketBoots);
        inv.setHelmet(ItemRegistry.SKULL_Bunket);
    }

    @Override
    public int getStartingEnergy() {
        return 1;
    }

    public static void activateShieldUp(World world, Location local, Player player, PlayerInventory inv, User user) {
        world.playSound(local, Sound.ENTITY_VILLAGER_WORK_TOOLSMITH, 1, 1);
        inv.setItemInOffHand(ItemRegistry.WEAPON_BunketShield);
        inv.clear(5);
    }

    public static void activateEmergencyRepairs(World world, Location local, Player player, PlayerInventory inv, User user) {
        //remove if you dont like my implementation --Roasty
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10, 4, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20, 2, false, false));
        inv.clear(3);
        world.playSound(local, Sound.ENTITY_IRON_GOLEM_REPAIR, 1, 1);
    }

    public static void activateSelfDestruct(World world, Location local, Player player, PlayerInventory inv, User user) {
        player.setHealth(0);
        world.createExplosion(local, 5f);
    }
}
