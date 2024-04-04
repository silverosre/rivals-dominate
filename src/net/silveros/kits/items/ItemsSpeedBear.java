package net.silveros.kits.items;

import net.silveros.kits.Abilities;
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
        generateSpeedBear(Abilities.SPEED_BEAR, "Speed Bear");
        generateStinkBomb(Abilities.STINK_BOMB, "Stink Bomb");
    }

    @Override
    protected void prepareArmor() {
        generateSpeedBearBoots();
        generateSpeedBearLeggings();
        generateSpeedBearChestplate();
        ItemRegistry.SKULL_SpeedBear = getSkull(Skulls.SPEED_BEAR, "Speed Bear Head");
    }

    private static void generateSpeedBear(Abilities ability, String name) {
        ItemAbility item = getBlankAbility(ability);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(abilityName(name) + itemCost(ability.getCost()));
        meta.setLore(addLore(
                ability,
                WHITE + ITALIC + "Increase Speed Substantially",
                WHITE + ITALIC + "At the cost of Defense and Attack"
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_SpeedBear = item;
    }

    private static void generateStinkBomb(Abilities ability, String name) {
        ItemAbility item = getBlankAbility(ability);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(abilityName(name) + itemCost(ability.getCost()));
        meta.setLore(addLore(
                ability,
                WHITE + ITALIC + "Creates a nauseating, blinding smoke",
                WHITE + ITALIC + "Smoke screen lasts 10 seconds"
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
