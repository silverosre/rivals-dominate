package net.silveros.kits.items;

import net.silveros.kits.Abilities;
import net.silveros.kits.ItemAbility;
import net.silveros.kits.ItemRegistry;
import org.bukkit.Color;
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

public class ItemsHerobrine extends Items {
    @Override
    protected void prepareFeatures() {
        generateHerobrinePower(Abilities.HEROBRINE_POWER, "Herobrine Power");
        generateFogCloak(Abilities.FOG_CLOAK, "Fog Cloak");
        generateUncloak(Abilities.UNCLOAK, "Uncloak");
        generateHerobrineAxe();
        generateHerobrineBow();
        generateHerobrinePowerAxe();
        generateHerobrinePowerBow();
        ItemRegistry.WEAPON_HerobrineArrows = getArrows(3);
    }

    @Override
    protected void prepareArmor() {
        ItemRegistry.SKULL_Herobrine = getSkull(Skulls.HEROBRINE, "Herobrine Head");
        generateHerobrineChestplate();
        generateHerobrineLeggings();
        generateHerobrineBoots();
    }

    private static void generateHerobrinePower(Abilities ability, String name) {
        ItemAbility item = getBlankAbility(ability);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(abilityName(name) + itemCost(ability.getCost()));
        meta.setLore(addLore(
                ability,
                WHITE + ITALIC + "Gain Herobrine's ultimate power.",
                WHITE + ITALIC + "Increases axe and bow power for",
                WHITE + ITALIC + "a short time."
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_HerobrinePower = item;
    }

    private static void generateFogCloak(Abilities ability, String name) {
        ItemAbility item = getBlankAbility(ability);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(abilityName(name) + itemCost(ability.getCost()));
        meta.setLore(addLore(
                ability,
                WHITE + ITALIC + "Cloak yourself in the mist.",
                WHITE + ITALIC + "Makes you invisible until you take",
                WHITE + ITALIC + "damage, attack, or step on a point."
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_FogCloak = item;
    }

    private static void generateUncloak(Abilities ability, String name) {
        ItemAbility item = getBlankAbility(ability);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(abilityName(name) + itemCost(ability.getCost()));
        meta.setLore(addLore(
                ability,
                WHITE + ITALIC + "Uncloak yourself"
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_Uncloak = item;
    }

    private static void generateHerobrineAxe() {
        ItemStack item = new ItemStack(Material.DIAMOND_AXE, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(YELLOW + "Herobrine Axe");
        //meta.setLore(addLore(GRAY + ITALIC + "The memories call out to you..."));
        meta.setUnbreakable(true);

        AttributeModifier atkdmg = new AttributeModifier(UUID.randomUUID(), "generic.attack_damage", 5.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, atkdmg);
        AttributeModifier atkspd = new AttributeModifier(UUID.randomUUID(), "generic.attack_speed", -0.7, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, atkspd);

        item.setItemMeta(meta);
        ItemRegistry.WEAPON_HerobrineAxe = item;
    }

    private static void generateHerobrineBow() {
        ItemStack item = new ItemStack(Material.BOW, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(YELLOW + "Herobrine Bow");
        //meta.setLore(addLore(GRAY + ITALIC + "Stand in the distance..."));
        meta.setUnbreakable(true);

        item.setItemMeta(meta);
        ItemRegistry.WEAPON_HerobrineBow = item;
    }

    private static void generateHerobrinePowerAxe() {
        ItemStack item = new ItemStack(Material.DIAMOND_AXE, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Herobrine Power Axe");
        meta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
        meta.setUnbreakable(true);

        AttributeModifier atkdmg = new AttributeModifier(UUID.randomUUID(), "generic.attack_damage", 5.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, atkdmg);
        AttributeModifier atkspd = new AttributeModifier(UUID.randomUUID(), "generic.attack_speed", -0.5, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, atkspd);

        item.setItemMeta(meta);
        ItemRegistry.WEAPON_HerobrinePowerAxe = item;
    }

    private static void generateHerobrinePowerBow() {
        ItemStack item = new ItemStack(Material.BOW, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Herobrine Power Bow");
        meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        meta.setUnbreakable(true);

        item.setItemMeta(meta);
        ItemRegistry.WEAPON_HerobrinePowerBow = item;
    }

    private static void generateHerobrineChestplate() {
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();

        meta.setColor(Color.AQUA);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.setUnbreakable(true);

        AttributeModifier armor = new AttributeModifier(UUID.randomUUID(), "generic.armor", 7.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, armor);

        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        //meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_HerobrineChestplate = item;
    }

    private static void generateHerobrineLeggings() {
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();

        meta.setColor(Color.fromRGB(6437809));
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.setUnbreakable(true);

        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        //meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_HerobrineLeggings = item;
    }

    private static void generateHerobrineBoots() {
        ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();

        meta.setColor(Color.GRAY);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.setUnbreakable(true);

        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        //meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_HerobrineBoots = item;
    }
}
