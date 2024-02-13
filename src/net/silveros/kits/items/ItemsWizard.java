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

public class ItemsWizard extends Items {
    @Override
    protected void prepareFeatures() {
        generateWizardStaff();
        generateZap(2);
        generateFireball(5);
        generateFreeze(1);
    }

    @Override
    protected void prepareArmor() {
        generateWizardChestplate();
        generateWizardLeggings();
        generateWizardBoots();
        ItemRegistry.SKULL_Wizard = getSkull(Skulls.WIZARD, "Wizard Head");
    }

    private static void generateZap(int cost) {
        ItemAbility item = getBlankAbility(cost, Abilities.ZAP);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Zap " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Places a bomb on the ground",
                WHITE + ITALIC + "that after a second explodes",
                DARK_AQUA + "Costs " + cost + " energy: 5 second cooldown"
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_Zap = item;
    }

    private static void generateFireball(int cost) {
        ItemAbility item = getBlankAbility(cost, Abilities.FIREBALL);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Fireball " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Shoots a huge",
                WHITE + ITALIC + "fireball that explodes",
                WHITE + ITALIC + "when it hits something.",
                DARK_AQUA + "Costs " + cost + " energy: 5 second cooldown"
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_Fireball = item;
    }

    private static void generateFreeze(int cost) {
        ItemAbility item = getBlankAbility(cost, Abilities.FREEZE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Freeze " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Freezes your feet",
                WHITE + ITALIC + "allows you to walk on water",
                WHITE + ITALIC + "and slows down nearby enemies",
                DARK_AQUA + "Costs " + cost + " energy: 20 second cooldown"
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_Freeze = item;
    }

    private static void generateWizardStaff() {
        ItemStack item = new ItemStack(Material.BLAZE_ROD, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(YELLOW + "Wizard Staff");
        meta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
        meta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);

        item.setItemMeta(meta);
        ItemRegistry.WEAPON_WizardStaff = item;
    }

    private static void generateWizardChestplate() {
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(Color.fromRGB(3113429));
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.addEnchant(Enchantment.PROTECTION_FIRE, 1, true);
        meta.setUnbreakable(true);

        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        //meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_WizardChestplate = item;
    }

    private static void generateWizardLeggings() {
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(Color.fromRGB(3113429));
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.setUnbreakable(true);

        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        //meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_WizardLeggings = item;
    }

    private static void generateWizardBoots() {
        ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(Color.fromRGB(1908001));
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.setUnbreakable(true);

        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        //meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_WizardBoots = item;
    }
}
