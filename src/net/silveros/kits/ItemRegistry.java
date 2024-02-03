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
    public static ItemStack ABILITY_EmergencyRepairs;
    public static ItemStack ABILITY_SelfDestruct;
    public static ItemStack ABILITY_ShieldUp;

    public static void init() {
        generateAbilityItems();
    }

    private static void generateAbilityItems() {
        //bunket
        generateEmergencyRepairs();
        generateSelfDestruct();
        generateShieldUp();

    }

    //Bunket abilities
    private static void generateEmergencyRepairs() {
        ItemStack item = getBlankAbility();
        ItemMeta meta = item.getItemMeta();
        int cost = 3;

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

    private static void generateSelfDestruct() {
        ItemStack item = getBlankAbility();
        ItemMeta meta = item.getItemMeta();
        int cost = 3;

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Self Destruct " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Go out with a bang.",
                DARK_AQUA + "Costs " + cost + " energy: one time use"
        ));

        item.setItemMeta(meta);
        ABILITY_SelfDestruct = item;
    }

    private static void generateShieldUp() {
        ItemStack item = getBlankAbility();
        ItemMeta meta = item.getItemMeta();
        int cost = 2;

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Shield Up " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Recover your shield.",
                DARK_AQUA + "Costs " + cost + " energy: 55 second cooldown"
        ));

        item.setItemMeta(meta);
        ABILITY_ShieldUp = item;
    }

    //Utility
    private static ItemStack getBlankAbility() {
        return new ItemStack(Material.ENCHANTED_BOOK, 1);
    }

    private static String itemCost(int cost) {
        return LIGHT_PURPLE + "(" + DARK_AQUA + BOLD + cost + RESET + LIGHT_PURPLE + ")";
    }

    private static List<String> addLore(String... args) {
        return new ArrayList<>(Arrays.asList(args));
    }
}
