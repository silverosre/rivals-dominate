package net.silveros.kits.items;

import net.silveros.kits.ItemAbility;
import net.silveros.kits.ItemRegistry;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ItemsSpeedBear extends Items {
    @Override
    protected void prepareFeatures() {
        generateSpeedBear(1);
        generateStinkBomb(2);
    }

    @Override
    protected void prepareArmor() {
        generateSpeedBearBoots();
        generateSpeedBearLeggings();
        generateSpeedBearChestplate();
        ItemRegistry.SKULL_SpeedBear = getSkull(Skulls.SPEED_BEAR, "Speed Bear Head");
    }

    private static void generateSpeedBear(int cost) {
        ItemAbility item = getBlankAbility(cost);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Speed Bear " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Increase Speed Substantially",
                WHITE + ITALIC + "At the cost of Defense and Attack",
                DARK_AQUA + "Costs " + cost + " energy: 5 second cooldown"
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_SpeedBear = item;
    }

    private static void generateStinkBomb(int cost) {
        ItemAbility item = getBlankAbility(cost);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Stink Bomb " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Creates a nauseating, blinding smoke",
                WHITE + ITALIC + "Smoke screen lasts 10 seconds",
                DARK_AQUA + "Costs " + cost + " energy: 30 second cooldown"
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_StinkBomb = item;
    }

    private static void generateSpeedBearChestplate() {
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(org.bukkit.Color.YELLOW);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_SpeedBearChestplate = item;
    }

    private static void generateSpeedBearLeggings() {
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(org.bukkit.Color.YELLOW);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_SpeedBearLeggings = item;
    }

    private static void generateSpeedBearBoots() {
        ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(org.bukkit.Color.YELLOW);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_SpeedBearBoots = item;
    }
}
