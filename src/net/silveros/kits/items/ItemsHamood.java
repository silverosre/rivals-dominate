package net.silveros.kits.items;

import net.silveros.kits.Abilities;
import net.silveros.kits.ItemAbility;
import net.silveros.kits.ItemRegistry;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ItemsHamood extends Items {
    @Override
    protected void prepareFeatures() {
        generatePharaohsCurse(1);
        generateDuneSlice(3);
        generateSwift(2);
        generateHamoodSword();
        generateDuneSlicer();
    }

    @Override
    protected void prepareArmor() {
        generateHamoodChestplate();
        generateHamoodLeggings();
        generateHamoodBoots();
        ItemRegistry.SKULL_Hamood = getSkull(Skulls.HAMOOD, "Hamood Head");
    }

    private static void generatePharaohsCurse(int cost) {
        ItemAbility item = getBlankAbility(cost, Abilities.PHARAOHS_CURSE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Pharaoh's Curse " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Throw a blinding sand block",
                DARK_AQUA + "Costs " + cost + " energy: 10 second cooldown"
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_PharaohsCurse = item;
    }

    private static void generateDuneSlice(int cost) {
        ItemAbility item = getBlankAbility(cost, Abilities.DUNE_SLICE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Dune Slice " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Increase your sword's damage",
                WHITE + ITALIC + "Effect lasts for 10 seconds",
                DARK_AQUA + "Costs " + cost + " energy: 30 second cooldown"
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_DuneSlice = item;
    }

    private static void generateSwift(int cost) {
        ItemAbility item = getBlankAbility(cost, Abilities.SWIFT);
        ItemMeta meta = item.getItemMeta();


        meta.setDisplayName(LIGHT_PURPLE + "Ability: Swift " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Gain insane speed and improved",
                WHITE + ITALIC + "swimming abilities for a short time.",
                DARK_AQUA + "Costs " + cost + " energy: 45 second cooldown"
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_Swift = item;
    }

    private static void generateHamoodSword() {
        ItemStack item = new ItemStack(Material.GOLDEN_SWORD, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(YELLOW + "Hamood Sword");
        meta.setUnbreakable(true);

        item.setItemMeta(meta);
        ItemRegistry.WEAPON_HamoodSword = item;
    }

    private static void generateDuneSlicer() {
        ItemStack item = new ItemStack(Material.GOLDEN_SWORD, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Dune Slicer");
        meta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
        meta.setUnbreakable(true);

        item.setItemMeta(meta);
        ItemRegistry.WEAPON_DuneSlicer = item;
    }

    private static void generateHamoodChestplate() {
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(Color.fromRGB(11796252));
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        meta.setUnbreakable(true);

        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        //meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_HamoodChestplate = item;
    }

    private static void generateHamoodLeggings() {
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(Color.fromRGB(13187864));
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.setUnbreakable(true);

        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        //meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_HamoodLeggings = item;
    }

    private static void generateHamoodBoots() {
        ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(Color.fromRGB(0));
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.setUnbreakable(true);

        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        //meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);

        ItemRegistry.ARMOR_HamoodBoots = item;
    }
}
