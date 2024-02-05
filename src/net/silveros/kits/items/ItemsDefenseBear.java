package net.silveros.kits.items;

import net.silveros.kits.ItemAbility;
import net.silveros.kits.ItemRegistry;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ItemsDefenseBear extends Items {
    @Override
    protected void prepareFeatures() {
        generateDefenseBear(1);
        generateChaosZone(3);
    }

    @Override
    protected void prepareArmor() {
        generateDefenseBearBoots();
        generateDefenseBearLeggings();
        generateDefenseBearChestplate();
        ItemRegistry.SKULL_DefenseBear = getSkull(Skulls.DEFENSE_BEAR, "Defense Bear Head");
    }

    private static void generateDefenseBear(int cost) {
        ItemAbility item = getBlankAbility(cost);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Defense Bear " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Increases Defensive abilities",
                WHITE + ITALIC + "at the cost of some Attack and Speed.",
                DARK_AQUA + "Costs " + cost + " energy: 5 second cooldown"
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_DefenseBear = item;
    }

    private static void generateChaosZone(int cost) {
        ItemAbility item = getBlankAbility(cost);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Chaos Zone " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Places a circle at your feet",
                WHITE + ITALIC + "that increases you and your team's attack",
                WHITE + ITALIC + "and damages enemy players in the zone",
                DARK_AQUA + "Costs " + cost + " energy: 30 second cooldown"
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_ChaosZone = item;
    }

    private static void generateDefenseBearChestplate() {
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(org.bukkit.Color.BLUE);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 2, true);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_DefenseBearChestplate = item;
    }

    private static void generateDefenseBearLeggings() {
        ItemStack item =  new ItemStack(Material.LEATHER_LEGGINGS, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(org.bukkit.Color.BLUE);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_DefenseBearLeggings = item;
    }

    private static void generateDefenseBearBoots() {
        ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(org.bukkit.Color.BLUE);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_DefenseBearBoots = item;
    }
}
