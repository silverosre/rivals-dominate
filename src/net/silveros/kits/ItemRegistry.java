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
    public static ItemEntry ABILITY_EmergencyRepairs;
    public static ItemEntry ABILITY_SelfDestruct;
    public static ItemEntry ABILITY_ShieldUp;

    //hamood
    public static ItemEntry ABILITY_PharaohsCurse;
    public static ItemEntry ABILITY_DuneSlice;
    public static ItemEntry ABILITY_Swift;

    public static void init() {
        generateAbilityItems();
    }

    private static void generateAbilityItems() {
        //input: cost, slot

        //bunket
        generateEmergencyRepairs(3);
        generateSelfDestruct(3);
        generateShieldUp(2);

        //hamood
        generatePharaohsCurse(1);
        generateDuneSlice(3);
        generateSwift(2);
    }

    //Bunket abilities
    private static void generateEmergencyRepairs(int cost) {
        ItemEntry item = getBlankAbility(cost);
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
        ItemEntry item = getBlankAbility(cost);
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
        ItemEntry item = getBlankAbility(cost);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Shield Up " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Recover your shield.",
                DARK_AQUA + "Costs " + cost + " energy: 55 second cooldown"
        ));

        item.setItemMeta(meta);
        ABILITY_ShieldUp = item;
    }

    //Hamood abilities
    private static void generatePharaohsCurse(int cost) {
        ItemEntry item = getBlankAbility(cost);
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
        ItemEntry item = getBlankAbility(cost);
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
        ItemEntry item = getBlankAbility(cost);
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
    private static ItemEntry getBlankAbility(int cost) {
        return new ItemEntry(Material.ENCHANTED_BOOK, 1, cost);
    }

    private static String itemCost(int cost) {
        return LIGHT_PURPLE + "(" + DARK_AQUA + BOLD + cost + RESET + LIGHT_PURPLE + ")";
    }

    private static List<String> addLore(String... args) {
        return new ArrayList<>(Arrays.asList(args));
    }
}
