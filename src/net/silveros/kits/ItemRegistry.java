package net.silveros.kits;

import net.silveros.utility.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemRegistry implements Color {
    //bunket
    public static ItemAbility ABILITY_EmergencyRepairs;
    public static ItemAbility ABILITY_SelfDestruct;
    public static ItemAbility ABILITY_ShieldUp;

    //archer
    public static ItemAbility ABILITY_Fletch;
    public static ItemAbility ABILITY_Snare;
    public static ItemAbility ABILITY_Quickshot;
    public static ItemStack WEAPON_Bow;
    public static ItemStack WEAPON_ArcherArrows;
    public static ItemStack WEAPON_WoodenKnife;

    //hamood
    public static ItemAbility ABILITY_PharaohsCurse;
    public static ItemAbility ABILITY_DuneSlice;
    public static ItemAbility ABILITY_Swift;

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
        generateQuickshot(7);

        //hamood
        generatePharaohsCurse(1);
        generateDuneSlice(3);
        generateSwift(2);
    }

    private static void generateKitItems(){
        //archer
        generateArcherBow();
        generateArcherSword();
        generateArcherArrows();
    }

    //------------
    //Bunket items
    //------------
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

    //------------
    //Archer items
    //------------
    public static void generateFletch(int cost) {
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

    public static void generateSnare(int cost) {
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

    public static void generateQuickshot(int cost) {
        ItemAbility item = getBlankAbility(cost);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Quickshot " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Shoot a ton of arrows.",
                DARK_AQUA + "Costs " + cost + " energy: 55 second cooldown"
        ));

        item.setItemMeta(meta);
        ABILITY_Quickshot = item;
    }

    public static void generateArcherBow() {
        ItemStack item = new ItemStack(Material.BOW, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(YELLOW + "Archer Bow");

        item.setItemMeta(meta);
        WEAPON_Bow = item;
    }

    public static void generateArcherSword() {
        ItemStack item = new ItemStack(Material.WOODEN_SWORD, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(YELLOW + "Wooden Knife");

        item.setItemMeta(meta);
        WEAPON_WoodenKnife = item;
    }

    public static void generateArcherArrows() {
        WEAPON_ArcherArrows = new ItemStack(Material.ARROW, 10);
    }

    //------------
    //Hamood items
    //------------
    private static void generatePharaohsCurse(int cost) {
        ItemAbility item = getBlankAbility(cost);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Pharaoh's Curse " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Quickly heal back to full health",
                WHITE + ITALIC + "at the cost of your passive ability",
                WHITE + ITALIC + "and a bit of time.",
                DARK_AQUA + "Costs " + cost + " energy: one time use"
        ));

        item.setItemMeta(meta);
        ABILITY_PharaohsCurse = item;
    }

    private static void generateDuneSlice(int cost) {
        ItemAbility item = getBlankAbility(cost);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Dune Slice " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Go out with a bang.",
                DARK_AQUA + "Costs " + cost + " energy: one time use"
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

    //Utility
    private static ItemAbility getBlankAbility(int cost) {
        return new ItemAbility(Material.ENCHANTED_BOOK, 1, cost);
    }

    private static String itemCost(int cost) {
        return LIGHT_PURPLE + "(" + DARK_AQUA + BOLD + cost + RESET + LIGHT_PURPLE + ")";
    }

    private static List<String> addLore(String... args) {
        return new ArrayList<>(Arrays.asList(args));
    }
}
