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

public class ItemsGoblin extends Items {
    @Override
    protected void prepareFeatures() {
        generateSteal(0);
        generateGive(1);
        generateSwarm(1);
    }

    @Override
    protected void prepareArmor() {
        generateGoblinChestplate();
        generateGoblinLeggings();
        generateGoblinBoots();
        //ItemRegistry.SKULL_Goblin = getSkull(Skulls.GOBLIN, "Goblin Head");
    }

    private static void generateSteal(int cost) {
        ItemAbility item = getBlankAbility(cost, Abilities.STEAL);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Steal " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Steals an energy",
                WHITE + ITALIC + "from a nearby enemy",
                DARK_AQUA + "Costs " + cost + " energy: 3 second cooldown"
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_Steal = item;
    }

    private static void generateGive(int cost) {
        ItemAbility item = getBlankAbility(cost, Abilities.GIVE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Give " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Gives an energy",
                WHITE + ITALIC + "to nearby allies",
                DARK_AQUA + "Costs " + cost  + " energy: 3 second cooldown"
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_Give = item;
    }

    private static void generateSwarm(int cost) {
        ItemAbility item = getBlankAbility(cost, Abilities.SWARM);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Swarm " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Gives you and nearby allies",
                WHITE + ITALIC + "a speed boost for a short time",
                DARK_AQUA + "Costs " + cost + " energy: 30 second cooldown"
        ));

        item.setItemMeta(meta);
        //ItemRegistry.ABILITY_Swarm = item;
    }

    private static void generateGoblinChestplate() {
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(Color.fromRGB(10338406));
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.setUnbreakable(true);

        AttributeModifier mvespd = new AttributeModifier(UUID.randomUUID(), "generic.movement_speed", 0.8, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST);
        meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, mvespd);
        AttributeModifier armor = new AttributeModifier(UUID.randomUUID(), "generic.armor", 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, armor);

        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        //meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);
        //ItemRegistry.ARMOR_GoblinChestplate = item;
    }

    private static void generateGoblinLeggings() {
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.setUnbreakable(true);

        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        //meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);
        //ItemRegistry.ARMOR_GoblinLeggings = item;
    }

    private static void generateGoblinBoots() {
        ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.setUnbreakable(true);

        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        //meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);
        //ItemRegistry.ARMOR_GoblinBoots = item;
    }
}
