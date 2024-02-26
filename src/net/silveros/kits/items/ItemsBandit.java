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

public class ItemsBandit extends Items{
    @Override
    protected void prepareFeatures() {
        generateSteal(0);
        generateGive(1);
        generateReload(2);
        generateSixshooter();
    }
    @Override
    protected void prepareArmor() {
        generateBanditChestplate();
        generateBanditLeggings();
        generateBanditBoots();
        ItemRegistry.SKULL_Bandit = getSkull(Skulls.BANDIT, "Bandit Head");
    }

    private static void generateSteal(int cost) {
        ItemAbility item = getBlankAbility(cost, Abilities.STEAL);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Steal " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + BOLD + ITALIC + "Steals an Energy from a nearby enemy",
                GRAY + ITALIC + "Slow your roll cowpoke, there are more riches further west",
                DARK_AQUA + BOLD + "Costs " + cost + " energy: 1 second cooldown"
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_Steal = item;
    }
    private static void generateGive(int cost) {
        ItemAbility item = getBlankAbility(cost, Abilities.GIVE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Give " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + BOLD + ITALIC + "Gives an Energy to a nearby ally",
                GRAY + ITALIC + "They call me Robin Hood this side of the Mississippi",
                DARK_AQUA + BOLD + "Costs " + cost + " energy: 2 second cooldown"
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_Give = item;
    }
    private static void generateReload(int cost) {
        ItemAbility item = getBlankAbility(cost, Abilities.RELOAD);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Reload " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + BOLD + ITALIC + "Gives you a bullet for your Six Shooter",
                GRAY + ITALIC + "More'n one way to tango, darlin'",
                DARK_AQUA + BOLD + "Costs " + cost + " energy, 1.5 second cooldown"
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_Reload = item;
    }
    private static void generateBanditChestplate() {
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(Color.fromRGB(181, 91, 58));
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.setUnbreakable(true);

        AttributeModifier mvespd = new AttributeModifier(UUID.randomUUID(), "generic.movement_speed", 0.8, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST);
        meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, mvespd);
        AttributeModifier armor = new AttributeModifier(UUID.randomUUID(), "generic.armor", 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, armor);

        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_BanditChestplate = item;
    }
    private static void generateBanditLeggings() {
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(Color.fromRGB(38, 17, 12));
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.setUnbreakable(true);

        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_BanditLeggings = item;
    }
    private static void generateBanditBoots() {
        ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(Color.BLACK);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.setUnbreakable(true);

        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);

        ItemRegistry.ARMOR_BanditBoots = item;
    }
    private static void generateSixshooter() {
        ItemStack item = new ItemStack(Material.IRON_HORSE_ARMOR, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(YELLOW + BOLD + "Six Shooter");

        item.setItemMeta(meta);
        ItemRegistry.WEAPON_Sixshooter = item;
    }
}
