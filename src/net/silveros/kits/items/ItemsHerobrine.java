package net.silveros.kits.items;

import net.silveros.kits.Abilities;
import net.silveros.kits.ItemAbility;
import net.silveros.kits.ItemRegistry;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ItemsHerobrine extends Items {
    @Override
    protected void prepareFeatures() {
        generateHerobrinePower(6);
        generateFogCloak(2);
        generateUncloak(0);
        generateHerobrineAxe();
        generateHerobrineBow();
        generateHerobrinePowerAxe();
        generateHerobrinePowerBow();
        ItemRegistry.WEAPON_HerobrineArrows = getArrows(3);
    }

    @Override
    protected void prepareArmor() {
        ItemRegistry.SKULL_Herobrine = getSkull(Skulls.HEROBRINE, "Herobrine Head");
        generateHerobrineChestplate();
        generateHerobrineLeggings();
        generateHerobrineBoots();
    }

    private static void generateHerobrinePower(int cost) {
        ItemAbility item = getBlankAbility(cost, Abilities.HEROBRINE_POWER);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Herobrine Power " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Gain Herobrine's ultimate power.",
                WHITE + ITALIC + "Increases axe and bow power for",
                WHITE + ITALIC + "a short time.",
                DARK_AQUA + "Costs " + cost + " energy: 55 second cooldown"
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_HerobrinePower = item;
    }

    private static void generateFogCloak(int cost) {
        ItemAbility item = getBlankAbility(cost, Abilities.FOG_CLOAK);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Fog Cloak " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Cloak yourself in the mist.",
                WHITE + ITALIC + "Makes you invisible until you take",
                WHITE + ITALIC + "damage, attack, or step on a point.",
                DARK_AQUA + "Costs " + cost + " energy: 30 second cooldown"
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_FogCloak = item;
    }
    private static void generateUncloak(int cost) {
        ItemAbility item = getBlankAbility(cost, Abilities.UNCLOAK);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Uncloak " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Uncloak yourself",
                DARK_AQUA + "Costs " + cost + " energy: 30 second cooldown"
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_Uncloak = item;
    }

    private static void generateHerobrineAxe() {
        ItemStack item = new ItemStack(Material.DIAMOND_AXE, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(YELLOW + "Herobrine Axe");
        //meta.setLore(addLore(GRAY + ITALIC + "The memories call out to you..."));
        meta.setUnbreakable(true);

        item.setItemMeta(meta);
        ItemRegistry.WEAPON_HerobrineAxe = item;
    }

    private static void generateHerobrineBow() {
        ItemStack item = new ItemStack(Material.BOW, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(YELLOW + "Herobrine Bow");
        //meta.setLore(addLore(GRAY + ITALIC + "Stand in the distance..."));
        meta.setUnbreakable(true);

        item.setItemMeta(meta);
        ItemRegistry.WEAPON_HerobrineBow = item;
    }
    private static void generateHerobrinePowerAxe() {
        ItemStack item = new ItemStack(Material.DIAMOND_AXE, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Herobrine Power Axe");
        meta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
        meta.setUnbreakable(true);

        item.setItemMeta(meta);
        ItemRegistry.WEAPON_HerobrinePowerAxe = item;
    }
    private static void generateHerobrinePowerBow(){
        ItemStack item = new ItemStack(Material.BOW, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Herobrine Power Bow");
        meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        meta.setUnbreakable(true);

        item.setItemMeta(meta);
        ItemRegistry.WEAPON_HerobrinePowerBow = item;
    }
    private static void generateHerobrineChestplate(){
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();

        meta.setColor(Color.AQUA);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_HerobrineChestplate = item;
    }
    private static void generateHerobrineLeggings(){
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();

        meta.setColor(Color.TEAL);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_HerobrineLeggings = item;
    }
    private static void generateHerobrineBoots() {
        ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();

        meta.setColor(Color.GRAY);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);

        item.setItemMeta(meta);
        ItemRegistry.ARMOR_HerobrineBoots = item;
    }
}
