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
    public static final byte SLOT_EMERGENCY_REPAIRS = 2;
    public static final byte SLOT_SELF_DESTRUCT = 3;
    public static final byte SLOT_SHIELD_UP = 4;
    private static final float EXPLOSION_POWER = 3.0f;

    public KitBunket(int id, String name) {
        super(id, name);
        this.foodCount = 4;
    }

    @Override
    public void activateKit(Player player, PlayerInventory inv) {
        super.activateKit(player, inv);

        inv.setItemInOffHand(ItemRegistry.WEAPON_BunketShield);
        inv.setItem(SLOT_EMERGENCY_REPAIRS, ItemRegistry.ABILITY_EmergencyRepairs);
        inv.setItem(SLOT_SELF_DESTRUCT, ItemRegistry.ABILITY_SelfDestruct);
        inv.setItem(SLOT_SHIELD_UP, ItemRegistry.ABILITY_ShieldUp);
        inv.setChestplate(ItemRegistry.ARMOR_BunketChestplate);
        inv.setLeggings(ItemRegistry.ARMOR_BunketLeggings);
        inv.setBoots(ItemRegistry.ARMOR_BunketBoots);
        inv.setHelmet(ItemRegistry.SKULL_Bunket);

        //player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, PotionEffect.INFINITE_DURATION, 1, false, false));
        //player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, PotionEffect.INFINITE_DURATION, 0, false, false));
    }

    @Override
    public int getHealth() {
        return 30;
    }

    @Override
    public int getStartingEnergy() {
        return 0;
    }

    public static void activateShieldUp(World world, Location local, Player player, PlayerInventory inv, User user) {
        world.playSound(local, Sound.ENTITY_VILLAGER_WORK_TOOLSMITH, 1, 1);
        inv.setItemInOffHand(ItemRegistry.WEAPON_BunketShield);
        inv.clear(SLOT_SHIELD_UP);
    }

    public static void activateEmergencyRepairs(World world, Location local, Player player, PlayerInventory inv, User user) {
        //remove if you dont like my implementation --Roasty
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10, 4, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20, 2, false, false));
        inv.clear(SLOT_EMERGENCY_REPAIRS);
        world.playSound(local, Sound.ENTITY_IRON_GOLEM_REPAIR, 1, 1);
    }

    public static void activateSelfDestruct(World world, Location local, Player player, PlayerInventory inv, User user) {
        player.setHealth(0);
        world.createExplosion(local, EXPLOSION_POWER);
    }
}
