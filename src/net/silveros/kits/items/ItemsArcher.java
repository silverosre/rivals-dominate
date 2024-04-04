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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.UUID;

public class ItemsArcher extends Items {
    @Override
    protected void prepareFeatures() {
        generateFletch(Abilities.FLETCH, "Fletch");
        generateSnare(Abilities.SNARE, "Snare");
        generateFromAbove(Abilities.FROM_ABOVE, "From Above");
        generateArcherBow();
        generateArcherSword();
        ItemRegistry.WEAPON_ArcherArrows = getArrows(10);
    }

    @Override
    protected void prepareArmor() {
        generateArcherLeggings();
        generateArcherChestplate();
        ItemRegistry.SKULL_Archer = getSkull(Skulls.ARCHER, "Archer Head");
    }

    private static void generateFletch(Abilities ability, String name) {
        ItemAbility item = getBlankAbility(ability);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(abilityName(name) + itemCost(ability.getCost()));
        meta.setLore(addLore(
                ability,
                WHITE + ITALIC + "Fletch some arrows."
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_Fletch = item;
    }

    private static void generateSnare(Abilities ability, String name) {
        ItemAbility item = getBlankAbility(ability);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(abilityName(name) + itemCost(ability.getCost()));
        meta.setLore(addLore(
                ability,
                WHITE + ITALIC + "Places a small trap that slows",
                WHITE + ITALIC + "enemies."
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_Snare = item;
    }

    private static void generateFromAbove(Abilities ability, String name) {
        ItemAbility item = getBlankAbility(ability);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(abilityName(name) + itemCost(ability.getCost()));
        meta.setLore(addLore(
                ability,
                WHITE + "Tosses a flare that marks an",
                WHITE + "area for air strike by arrows."
                //GRAY + ITALIC + "\"And just when we thought we could",
                //GRAY + ITALIC + "escape, they attacked us from above.\""
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_FromAbove = item;
    }

    private static void generateArcherBow() {
        ItemStack item = new ItemStack(Material.BOW, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(YELLOW + "Archer Bow");
        meta.setUnbreakable(true);

        item.setItemMeta(meta);
        ItemRegistry.WEAPON_ArcherBow = item;
    }

    private static void generateArcherSword() {
        ItemStack item = new ItemStack(Material.STICK, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(YELLOW + "Small Dagger");
        meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        meta.setUnbreakable(true);

        AttributeModifier atkspd = new AttributeModifier(UUID.randomUUID(), "generic.attack_speed", -0.3, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, atkspd);

        item.setItemMeta(meta);
        ItemRegistry.WEAPON_WoodenKnife = item;
    }

    private static void generateArcherChestplate() {
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(Color.fromRGB(9491738)); // i fixed the horrors -silver
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
        meta.setDisplayName(YELLOW + "Archer Chestplate");
        meta.setUnbreakable(true);

        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        //meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_ArcherChestplate = item;
    }

    private static void generateArcherLeggings() {
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(Color.fromRGB(9491738));
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.setDisplayName(YELLOW + "Archer Leggings");
        meta.setUnbreakable(true);

        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        //meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_ArcherLeggings = item;
    }
}
