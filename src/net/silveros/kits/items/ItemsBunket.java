package net.silveros.kits.items;

import net.silveros.kits.Abilities;
import net.silveros.kits.ItemAbility;
import net.silveros.kits.ItemRegistry;
import net.silveros.utility.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.UUID;

public class ItemsBunket extends Items {
    @Override
    protected void prepareFeatures() {
        generateBunketShield(35);
        generateEmergencyRepairs(Abilities.EMERGENCY_REPAIRS, "Emergency Repairs");
        generateSelfDestruct(Abilities.SELF_DESTRUCT, "Self Destruct");
        generateShieldUp(Abilities.SHIELD_UP, "Shield Up");
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

    private static void generateEmergencyRepairs(Abilities ability, String name) {
        ItemAbility item = getBlankAbility(ability);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(abilityName(name) + itemCost(ability.getCost()));
        meta.setLore(addLore(
                ability,
                WHITE + ITALIC + "Quickly heal back to full health",
                WHITE + ITALIC + "at the cost of your passive ability",
                WHITE + ITALIC + "and a bit of time."
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_EmergencyRepairs = item;
    }

    private static void generateSelfDestruct(Abilities ability, String name) {
        ItemAbility item = getBlankAbility(ability);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(abilityName(name) + itemCost(ability.getCost()));
        meta.setLore(addLore(
                ability,
                WHITE + ITALIC + "Go out with a bang."
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_SelfDestruct = item;
    }

    private static void generateShieldUp(Abilities ability, String name) {
        ItemAbility item = getBlankAbility(ability);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(abilityName(name) + itemCost(ability.getCost()));
        meta.setLore(addLore(
                ability,
                WHITE + ITALIC + "Recover your shield."
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
        meta.setUnbreakable(true);

        AttributeModifier atkspd = new AttributeModifier(UUID.randomUUID(), "generic.attack_speed", -0.75, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, atkspd);
        AttributeModifier mvespd = new AttributeModifier(UUID.randomUUID(), "generic.movement_speed", -0.6, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST);
        meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, mvespd);
        AttributeModifier atkdmg = new AttributeModifier(UUID.randomUUID(), "generic.attack_damage", 4.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, atkdmg);
        AttributeModifier armor = new AttributeModifier(UUID.randomUUID(), "generic.armor", 9.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, armor);

        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        //meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_BunketChestplate = item;
    }

    private static void generateBunketLeggings() {
        ItemStack item = new ItemStack(Material.IRON_LEGGINGS, 1);
        ItemMeta meta = item.getItemMeta();

        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.setUnbreakable(true);

        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        //meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_BunketLeggings = item;
    }

    private static void generateBunketBoots() {
        ItemStack item = new ItemStack(Material.IRON_BOOTS, 1);
        ItemMeta meta = item.getItemMeta();

        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.setUnbreakable(true);

        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        //meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_BunketBoots = item;
    }
}
