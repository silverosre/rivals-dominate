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
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

import java.util.UUID;

public class ItemsRogue extends Items{
    @Override
    protected void prepareFeatures() {
        generateCurse(1);
        generateIncantation(3);
        generateSwift(2);
        generateRogueSword();
        generateDarkSister();
    }
    @Override
    protected void prepareArmor() {
        generateRogueChestplate();
        generateRogueLeggings();
        generateRogueBoots();
        ItemRegistry.SKULL_Rogue = getSkull(Skulls.ROGUE, "Rogue Head");
    }

    private static void generateCurse(int cost) {
        ItemAbility item = getBlankAbility(cost, Abilities.CURSE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Curse " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + BOLD + "Throws a blinding bomb on the ground",
                GRAY + ITALIC + "Use it wisely...",
                DARK_AQUA + BOLD + "Costs " + cost + " energy: 10 second cooldown"
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_Curse = item;
    }
    private static void generateIncantation(int cost) {
        ItemAbility item = getBlankAbility(cost, Abilities.INCANTATION);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Incantation " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + BOLD + "Increases your sword's damage for 10 seconds",
                GRAY + ITALIC + "Let the darkness surround your blade.",
                DARK_AQUA + BOLD + "Costs " + cost + " energy: 30 second cooldown"
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_Incantation = item;
    }
    private static void generateSwift(int cost) {
        ItemAbility item = getBlankAbility(cost, Abilities.SWIFT);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Swift " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + BOLD + "Increases your speed substantially for a short time",
                GRAY + ITALIC + "Run away, all of your problems have been solved this way.",
                DARK_AQUA + BOLD + "Costs " + cost + " energy: 45 second cooldown"
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_Swift = item;
    }
    private static void generateRogueSword() {
        ItemStack item = new ItemStack(Material.GOLDEN_SWORD, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(YELLOW + "Arcane Blade");
        meta.setUnbreakable(true);

        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);
        ItemRegistry.WEAPON_RogueSword = item;
    }
    private static void generateDarkSister() {
        ItemStack item = new ItemStack(Material.GOLDEN_SWORD, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "The Dark Sister");
        meta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
        meta.setUnbreakable(true);

        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);
        ItemRegistry.WEAPON_DarkSister = item;
    }
    private static void generateRogueChestplate() {
        ItemStack item = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ArmorMeta meta = (ArmorMeta)item.getItemMeta();

        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.setUnbreakable(true);

        meta.setTrim(new ArmorTrim(TrimMaterial.REDSTONE, TrimPattern.EYE));

        AttributeModifier armor = new AttributeModifier(UUID.randomUUID(), "generic.armor", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, armor);

        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_RogueChestplate = item;
    }
    private static void generateRogueLeggings() {
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.setUnbreakable(true);
        meta.setColor(Color.fromRGB(155, 0, 0));

        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_RogueLeggings = item;
    }
    private static void generateRogueBoots() {
        ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.setUnbreakable(true);
        meta.setColor(Color.WHITE);

        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_DYE);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_RogueBoots = item;
    }
}
