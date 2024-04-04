package net.silveros.kits.items;

import net.silveros.kits.Abilities;
import net.silveros.kits.ItemAbility;
import net.silveros.kits.ItemRegistry;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ItemsAttackBear extends Items {
    @Override
    protected void prepareFeatures() {
        generateAttackBear(Abilities.ATTACK_BEAR, "Attack Bear");
        generateNumb(Abilities.NUMB, "Numb");
        generateNumbness();
    }

    @Override
    protected void prepareArmor() {
        generateAttackBearBoots();
        generateAttackBearLeggings();
        generateAttackBearChestplate();
        ItemRegistry.SKULL_AttackBear = getSkull(Skulls.ATTACK_BEAR, "Attack Bear Head");
    }

    private static void generateAttackBear(Abilities ability, String name) {
        ItemAbility item = getBlankAbility(ability);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(abilityName(name) + itemCost(ability.getCost()));
        meta.setLore(addLore(
                ability,
                WHITE + ITALIC + "Increases Attack",
                WHITE + ITALIC + "At the cost of Defense and some Speed"
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_AttackBear = item;
    }

    private static void generateNumb(Abilities ability, String name) {
        ItemAbility item = getBlankAbility(ability);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(abilityName(name) + itemCost(ability.getCost()));
        meta.setLore(addLore(
                ability,
                WHITE + ITALIC + "Damage you take is tallied up",
                WHITE + ITALIC + "and delivered after 10 seconds"
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_Numb = item;
    }

    private static void generateAttackBearChestplate() {
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(org.bukkit.Color.RED);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.setUnbreakable(true);

        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        //meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_AttackBearChestplate = item;
    }

    private static void generateAttackBearLeggings() {
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(org.bukkit.Color.RED);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.setUnbreakable(true);

        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        //meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_AttackBearLeggings = item;
    }

    private static void generateAttackBearBoots() {
        ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(org.bukkit.Color.RED);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.setUnbreakable(true);

        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        //meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_AttackBearBoots = item;
    }

    private static void generateNumbness() {
        ItemStack item = new ItemStack(Material.MAGMA_CREAM, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(RED + BOLD + "Numbness");
        meta.setLore(addLore(
                GRAY + ITALIC + "You arent sure what it does",
                GRAY + ITALIC + "But this ball makes you numb."
        ));

        item.setItemMeta(meta);
        ItemRegistry.ITEM_Numbness = item;
    }
}
