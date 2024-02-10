package net.silveros.kits.items;

import net.silveros.kits.Abilities;
import net.silveros.kits.ItemAbility;
import net.silveros.kits.ItemRegistry;
import net.silveros.utility.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ItemsBunket extends Items {
    @Override
    protected void prepareFeatures() {
        generateBunketShield(35);
        generateEmergencyRepairs(3);
        generateSelfDestruct(3);
        generateShieldUp(2);
    }

    @Override
    protected void prepareArmor() {
        generateBunketChestplate();
        generateBunketLeggings();
        generateBunketBoots();
        ItemRegistry.SKULL_Bunket = getSkull(Skulls.BUNKET, "Bunket Head");
    }

    private static void generateBunketShield(int durability) {
        ItemStack item = new ItemStack(Material.SHIELD, 1);
        Damageable meta = (Damageable)item.getItemMeta();

        meta.setDamage(Material.SHIELD.getMaxDurability() - durability);
        meta.setDisplayName(YELLOW + "Shield");

        item.setItemMeta(meta);
        ItemRegistry.WEAPON_BunketShield = item;
    }

    private static void generateEmergencyRepairs(int cost) {
        ItemAbility item = getBlankAbility(cost, Abilities.EMERGENCY_REPAIRS);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Emergency Repairs " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Quickly heal back to full health",
                WHITE + ITALIC + "at the cost of your passive ability",
                WHITE + ITALIC + "and a bit of time.",
                DARK_AQUA + "Costs " + cost + " energy: one time use"
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_EmergencyRepairs = item;
    }

    private static void generateSelfDestruct(int cost) {
        ItemAbility item = getBlankAbility(cost, Abilities.SELF_DESTRUCT);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Self Destruct " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Go out with a bang.",
                DARK_AQUA + "Costs " + cost + " energy: one time use"
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_SelfDestruct = item;
    }

    private static void generateShieldUp(int cost) {
        ItemAbility item = getBlankAbility(cost, Abilities.SHIELD_UP);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Shield Up " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Recover your shield.",
                DARK_AQUA + "Costs " + cost + " energy: 55 second cooldown"
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_ShieldUp = item;
    }

    private static void generateBunketChestplate() {
        ItemStack item = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemMeta meta = item.getItemMeta();

        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 1, true);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_BunketChestplate = item;
    }

    private static void generateBunketLeggings() {
        ItemStack item = new ItemStack(Material.IRON_LEGGINGS, 1);
        ItemMeta meta = item.getItemMeta();

        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_BunketLeggings = item;
    }

    private static void generateBunketBoots() {
        ItemStack item = new ItemStack(Material.IRON_BOOTS, 1);
        ItemMeta meta = item.getItemMeta();

        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_BunketBoots = item;
    }
}
