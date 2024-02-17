package net.silveros.kits.items;

import net.silveros.kits.Abilities;
import net.silveros.kits.ItemAbility;
import net.silveros.kits.ItemRegistry;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ItemsGummybear extends Items {
    @Override
    protected void prepareFeatures() {
        generateNormalBear(0);
        generateGummyClub();
        generateGummyEssence();
    }

    @Override
    protected void prepareArmor() {
        generateNormalBearBoots();
        generateNormalBearLeggings();
        generateNormalBearChestplate();
        ItemRegistry.SKULL_GummyBear = getSkull(Skulls.GUMMY_BEAR, "Gummy Bear Head");
    }

    private static void generateNormalBear(int cost) {
        ItemAbility item = getBlankAbility(cost, Abilities.NORMAL_BEAR);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Normal Bear " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Changes you back into",
                WHITE + ITALIC + "your average bear!",
                DARK_AQUA + "Costs " + cost + " energy: 5 second cooldown"
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_NormalBear = item;
    }

    private static void generateGummyClub() {
        ItemStack item = new ItemStack(Material.DIAMOND_SHOVEL, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(YELLOW + "Gummy Club");
        meta.setUnbreakable(true);

        item.setItemMeta(meta);
        ItemRegistry.WEAPON_GummyClub = item;
    }

    private static void generateGummyEssence() {
        ItemStack item = new ItemStack(Material.SLIME_BALL, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(BLUE + ITALIC + "Gummy Essense");
        meta.setLore(addLore(
                GRAY + ITALIC + "It's a ball of gummy flesh.",
                GRAY + ITALIC + "It doesn't seem to do anything."
        ));

        item.setItemMeta(meta);
        ItemRegistry.ITEM_GummyEssence = item;
    }

    private static void generateNormalBearChestplate() {
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(org.bukkit.Color.LIME);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_NormalBearChestplate = item;
    }

    private static void generateNormalBearLeggings() {
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(org.bukkit.Color.LIME);
        meta.addEnchant(Enchantment.BINDING_CURSE,1, true);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_NormalBearLeggings = item;
    }

    private static void generateNormalBearBoots() {
        ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(org.bukkit.Color.LIME);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_NormalBearBoots = item;
    }
}
