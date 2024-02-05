package net.silveros.kits;

import net.silveros.utility.Color;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ItemRegistry implements Color {
    //Bunket
    public static ItemAbility ABILITY_EmergencyRepairs;
    public static ItemAbility ABILITY_SelfDestruct;
    public static ItemAbility ABILITY_ShieldUp;
    public static ItemStack WEAPON_BunketShield;
    public static ItemStack ARMOR_BunketChestplate;
    public static ItemStack ARMOR_BunketLeggings;
    public static ItemStack ARMOR_BunketBoots;
    public static ItemStack SKULL_Bunket;

    //Archer
    public static ItemAbility ABILITY_Fletch;
    public static ItemAbility ABILITY_Snare;
    public static ItemAbility ABILITY_FromAbove;
    public static ItemStack WEAPON_ArcherBow;
    public static ItemStack WEAPON_ArcherArrows;
    public static ItemStack WEAPON_WoodenKnife;
    public static ItemStack ARMOR_ArcherChestplate;
    public static ItemStack ARMOR_ArcherLeggings;
    public static ItemStack ARMOR_ArcherBoots;
    public static ItemStack SKULL_Archer;

    //Hamood
    public static ItemAbility ABILITY_PharaohsCurse;
    public static ItemAbility ABILITY_DuneSlice;
    public static ItemAbility ABILITY_Swift;
    public static ItemStack WEAPON_HamoodSword;
    public static ItemStack WEAPON_DuneSlicer;
    public static ItemStack ARMOR_HamoodChestplate;
    public static ItemStack ARMOR_HamoodLeggings;
    public static ItemStack ARMOR_HamoodBoots;
    public static ItemStack SKULL_Hamood;

    //Herobrine
    public static ItemAbility ABILITY_HerobrinePower;
    public static ItemAbility ABILITY_FogCloak;
    public static ItemStack WEAPON_HerobrineAxe;
    public static ItemStack WEAPON_HerobrineArrows;
    public static ItemStack WEAPON_HerobrineBow;
    public static ItemStack SKULL_Herobrine;

    public static void init() {
        generateAbilityItems();
        generateKitItems();
    }

    private static void generateAbilityItems() {
        //bunket
        generateEmergencyRepairs(3);
        generateSelfDestruct(3);
        generateShieldUp(2);

        //archer
        generateFletch(1);
        generateSnare(1);
        generateFromAbove(3);

        //hamood
        generatePharaohsCurse(1);
        generateDuneSlice(3);
        generateSwift(2);

        //herobrine
        generateHerobrinePower(6);
        generateFogCloak(2);
    }

    private static void generateKitItems() {
        //bunket
        generateBunketShield(35);
        generateBunketChestplate();
        generateBunketLeggings();
        generateBunketBoots();
        SKULL_Bunket = getSkull(Skulls.BUNKET, "Bunket Head");

        //archer
        generateArcherBow();
        generateArcherSword();
        WEAPON_ArcherArrows = getArrows(10);
        generateArcherBoots();
        generateArcherLeggings();
        generateArcherChestplate();
        SKULL_Archer = getSkull(Skulls.ARCHER, "Archer Head");

        //hamood
        generateHamoodSword();
        generateHamoodChestplate();
        generateHamoodLeggings();
        generateHamoodBoots();
        generateDuneSlicer();
        SKULL_Hamood = getSkull(Skulls.HAMOOD, "Hamood Head");

        //herobrine
        generateHerobrineAxe();
        generateHerobrineBow();
        WEAPON_HerobrineArrows = getArrows(3);
        SKULL_Herobrine = getSkull(Skulls.HEROBRINE, "Herobrine Head");
    }

    //------------
    //Bunket items
    //------------
    private static void generateBunketShield(int durability) {
        ItemStack item = new ItemStack(Material.SHIELD, 1);
        Damageable meta = (Damageable)item.getItemMeta();

        meta.setDamage(Material.SHIELD.getMaxDurability() - durability);
        meta.setDisplayName(YELLOW + "Shield");

        item.setItemMeta(meta);
        WEAPON_BunketShield = item;
    }

    private static void generateEmergencyRepairs(int cost) {
        ItemAbility item = getBlankAbility(cost);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Emergency Repairs " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Quickly heal back to full health",
                WHITE + ITALIC + "at the cost of your passive ability",
                WHITE + ITALIC + "and a bit of time.",
                DARK_AQUA + "Costs " + cost + " energy: one time use"
        ));

        item.setItemMeta(meta);
        ABILITY_EmergencyRepairs = item;
    }

    private static void generateSelfDestruct(int cost) {
        ItemAbility item = getBlankAbility(cost);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Self Destruct " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Go out with a bang.",
                DARK_AQUA + "Costs " + cost + " energy: one time use"
        ));

        item.setItemMeta(meta);
        ABILITY_SelfDestruct = item;
    }

    private static void generateShieldUp(int cost) {
        ItemAbility item = getBlankAbility(cost);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Shield Up " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Recover your shield.",
                DARK_AQUA + "Costs " + cost + " energy: 55 second cooldown"
        ));

        item.setItemMeta(meta);
        ABILITY_ShieldUp = item;
    }

    private static void generateBunketChestplate() {
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(org.bukkit.Color.WHITE);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 5, true);

        item.setItemMeta(meta);
        ARMOR_BunketChestplate = item;
    }

    private static void generateBunketLeggings() {
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(org.bukkit.Color.WHITE);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);

        item.setItemMeta(meta);
        ARMOR_BunketLeggings = item;
    }

    private static void generateBunketBoots() {
        ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(org.bukkit.Color.WHITE);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);

        item.setItemMeta(meta);
        ARMOR_BunketBoots = item;
    }

    //------------
    //Archer items
    //------------
    private static void generateFletch(int cost) {
        ItemAbility item = getBlankAbility(cost);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Fletch " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Fletch some arrows.",
                DARK_AQUA + "Costs " + cost + " energy: 5 second cooldown"
        ));

        item.setItemMeta(meta);
        ABILITY_Fletch = item;
    }

    private static void generateSnare(int cost) {
        ItemAbility item = getBlankAbility(cost);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Snare " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Places a small trap that slows",
                WHITE + ITALIC + "enemies.",
                DARK_AQUA + "Costs " + cost + " energy: 30 second cooldown"
        ));

        item.setItemMeta(meta);
        ABILITY_Snare = item;
    }

    private static void generateFromAbove(int cost) {
        ItemAbility item = getBlankAbility(cost);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: From Above " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Tosses a flare that marks an",
                WHITE + ITALIC + "area for air strike by arrows.",
                DARK_AQUA + "Costs " + cost + " energy: 20 second cooldown"
        ));

        item.setItemMeta(meta);
        ABILITY_FromAbove = item;
    }

    private static void generateArcherBow() {
        ItemStack item = new ItemStack(Material.BOW, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(YELLOW + "Archer Bow");

        item.setItemMeta(meta);
        WEAPON_ArcherBow = item;
    }

    private static void generateArcherSword() {
        ItemStack item = new ItemStack(Material.WOODEN_SWORD, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(YELLOW + "Wooden Knife");

        item.setItemMeta(meta);
        WEAPON_WoodenKnife = item;
    }

    private static void generateArcherChestplate() {
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(org.bukkit.Color.LIME); // i was going to fix this and then i saw the horrors -silver
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
        meta.setDisplayName(YELLOW + "Archer Chestplate");

        item.setItemMeta(meta);
        ARMOR_ArcherChestplate = item;
    }

    private static void generateArcherLeggings() {
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(org.bukkit.Color.LIME);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.setDisplayName(YELLOW + "Archer Leggings");

        item.setItemMeta(meta);
        ARMOR_ArcherLeggings = item;
    }

    private static void generateArcherBoots() {
        ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(org.bukkit.Color.LIME);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.setDisplayName(YELLOW + "Archer Boots");

        item.setItemMeta(meta);
        ARMOR_ArcherBoots = item;
    }

    //------------
    //Hamood items
    //------------
    private static void generatePharaohsCurse(int cost) {
        ItemAbility item = getBlankAbility(cost);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Pharaoh's Curse " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Throw a blinding sand block",
                DARK_AQUA + "Costs " + cost + " energy: 10 second cooldown"
        ));

        item.setItemMeta(meta);
        ABILITY_PharaohsCurse = item;
    }

    private static void generateDuneSlice(int cost) {
        ItemAbility item = getBlankAbility(cost);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Dune Slice " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Increase your sword's damage",
                WHITE + ITALIC + "Effect lasts for 10 seconds",
                DARK_AQUA + "Costs " + cost + " energy: 30 second cooldown"
        ));

        item.setItemMeta(meta);
        ABILITY_DuneSlice = item;
    }

    private static void generateSwift(int cost) {
        ItemAbility item = getBlankAbility(cost);
        ItemMeta meta = item.getItemMeta();


        meta.setDisplayName(LIGHT_PURPLE + "Ability: Swift " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Gain insane speed and improved",
                WHITE + ITALIC + "swimming abilities for a short time.",
                DARK_AQUA + "Costs " + cost + " energy: one time use"
        ));

        item.setItemMeta(meta);
        ABILITY_Swift = item;
    }

    private static void generateHamoodSword() {
        ItemStack item = new ItemStack(Material.GOLDEN_SWORD, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(YELLOW + "Hamood Sword");

        item.setItemMeta(meta);
        WEAPON_HamoodSword = item;
    }

    private static void generateDuneSlicer() {
        ItemStack item = new ItemStack(Material.GOLDEN_SWORD, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Dune Slicer");
        meta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);

        item.setItemMeta(meta);
        WEAPON_DuneSlicer = item;
    }

    private static void generateHamoodChestplate() {
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(org.bukkit.Color.LIME);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);

        item.setItemMeta(meta);
        ARMOR_HamoodChestplate = item;
    }

    private static void generateHamoodLeggings() {
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(org.bukkit.Color.MAROON);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);

        item.setItemMeta(meta);
        ARMOR_HamoodLeggings = item;
    }

    private static void generateHamoodBoots() {
        ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();

        meta.setColor(org.bukkit.Color.BLACK);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);

        item.setItemMeta(meta);

        ARMOR_HamoodBoots = item;
    }

    //-----------
    //Herobrine Items
    //-----------
    private static void generateHerobrinePower(int cost) {
        ItemAbility item = getBlankAbility(cost);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Herobrine Power " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Gain Herobrine's ultimate power.",
                WHITE + ITALIC + "Increases axe and bow power for",
                WHITE + ITALIC + "a short time.",
                DARK_AQUA + "Costs " + cost + " energy: 55 second cooldown"
        ));

        item.setItemMeta(meta);
        ABILITY_HerobrinePower = item;
    }

    private static void generateFogCloak(int cost) {
        ItemAbility item = getBlankAbility(cost);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Fog Cloak " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Cloak yourself in the mist.",
                WHITE + ITALIC + "Makes you invisible until you take",
                WHITE + ITALIC + "damage, attack, or step on a point.",
                DARK_AQUA + "Costs " + cost + " energy: 30 second cooldown"
        ));

        item.setItemMeta(meta);
        ABILITY_FogCloak = item;
    }

    private static void generateHerobrineAxe() {
        ItemStack item = new ItemStack(Material.DIAMOND_AXE, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(YELLOW + "Herobrine Axe");
        //meta.setLore(addLore(GRAY + ITALIC + "The memories call out to you..."));

        item.setItemMeta(meta);
        WEAPON_HerobrineAxe = item;
    }

    private static void generateHerobrineBow() {
        ItemStack item = new ItemStack(Material.BOW, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(YELLOW + "Herobrine Bow");
        //meta.setLore(addLore(GRAY + ITALIC + "Stand in the distance..."));

        item.setItemMeta(meta);
        WEAPON_HerobrineBow = item;
    }

    //Utility
    private static ItemAbility getBlankAbility(int cost) {
        return new ItemAbility(Material.ENCHANTED_BOOK, 1, cost);
    }

    private static ItemStack getArrows(int count) {
        return new ItemStack(Material.ARROW, count);
    }

    private static String itemCost(int cost) {
        return LIGHT_PURPLE + "(" + DARK_AQUA + BOLD + cost + RESET + LIGHT_PURPLE + ")";
    }

    private static List<String> addLore(String... args) {
        return new ArrayList<>(Arrays.asList(args));
    }

    /**@param id Minecraft URL of specified skull.*/
    public static ItemStack getSkull(String id, String itemName) {
        PlayerProfile profile = Bukkit.createPlayerProfile(UUID.fromString("92864445-51c5-4c3b-9039-517c9927d1b4")); // use random ass uuid
        PlayerTextures textures = profile.getTextures();

        try {
            textures.setSkin(URI.create("https://textures.minecraft.net/texture/" + id).toURL());
        } catch (MalformedURLException exception) {
            throw new RuntimeException("Invalid URL", exception);
        }

        profile.setTextures(textures);

        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta)item.getItemMeta();
        meta.setOwnerProfile(profile);
        meta.setDisplayName(YELLOW + itemName);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);

        item.setItemMeta(meta);
        return item;
    }
}
