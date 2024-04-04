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
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListSet;

public class ItemsBandit extends Items{
    @Override
    protected void prepareFeatures() {
        generateSteal(Abilities.STEAL, "Steal");
        generateGive(Abilities.GIVE, "Give");
        generateReload(Abilities.RELOAD, "Reload");
        generateSixShooter();
    }
    @Override
    protected void prepareArmor() {
        generateBanditChestplate();
        generateBanditLeggings();
        generateBanditBoots();
        ItemRegistry.SKULL_Bandit = getSkull(Skulls.BANDIT, "Bandit Head");
    }

    private static void generateSteal(Abilities ability, String name) {
        ItemAbility item = getBlankAbility(ability);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(abilityName(name) + itemCost(ability.getCost()));
        meta.setLore(addLore(
                ability,
                WHITE + ITALIC + "Steals an Energy from a nearby enemy"
                //GRAY + ITALIC + "Slow your roll cowpoke, there are more riches further west",
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_Steal = item;
    }

    private static void generateGive(Abilities ability, String name) {
        ItemAbility item = getBlankAbility(ability);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(abilityName(name) + itemCost(ability.getCost()));
        meta.setLore(addLore(
                ability,
                WHITE + ITALIC + "Gives an Energy to a nearby ally"
                //GRAY + ITALIC + "They call me Robin Hood this side of the Mississippi"
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_Give = item;
    }

    private static void generateReload(Abilities ability, String name) {
        ItemAbility item = getBlankAbility(ability);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(abilityName(name) + itemCost(ability.getCost()));
        meta.setLore(addLore(
                ability,
                WHITE + ITALIC + "Loads two bullets into your Six-Shooter"
                //GRAY + ITALIC + "More'n one way to tango, darlin'"
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_Reload = item;
    }

    private static void generateBanditChestplate() {
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(Color.fromRGB(11885370));
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.setUnbreakable(true);

        ((ArmorMeta)meta).setTrim(new ArmorTrim(TrimMaterial.NETHERITE, TrimPattern.VEX));

        AttributeModifier mvespd = new AttributeModifier(UUID.randomUUID(), "generic.movement_speed", 0.8, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST);
        meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, mvespd);
        AttributeModifier armor = new AttributeModifier(UUID.randomUUID(), "generic.armor", 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, armor);

        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_BanditChestplate = item;
    }

    private static void generateBanditLeggings() {
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(Color.fromRGB(0x62412C));
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.setUnbreakable(true);

        ((ArmorMeta)meta).setTrim(new ArmorTrim(TrimMaterial.NETHERITE, TrimPattern.SILENCE));

        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_BanditLeggings = item;
    }

    private static void generateBanditBoots() {
        ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(Color.fromRGB(0x52392A));
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.setUnbreakable(true);

        ((ArmorMeta)meta).setTrim(new ArmorTrim(TrimMaterial.NETHERITE, TrimPattern.SNOUT));

        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        ItemRegistry.ARMOR_BanditBoots = item;
    }

    private static void generateSixShooter() {
        ItemStack item = new ItemStack(Material.IRON_HORSE_ARMOR, 1);
        ItemMeta meta = item.getItemMeta();

        if (item.getType() == Material.CROSSBOW) {
            CrossbowMeta crossbowMeta = (CrossbowMeta)meta;
            crossbowMeta.addChargedProjectile(new ItemStack(Material.ARROW));
        }

        meta.setDisplayName(YELLOW + BOLD + "Six Shooter");
        meta.setLore(addLore(
                WHITE + ITALIC + "Right-click to use.  Ammo shown above hotbar.",
                GRAY + ITALIC + "Quicker than lightnin'"
        ));

        item.setItemMeta(meta);
        ItemRegistry.WEAPON_SixShooter = item;
    }
}
