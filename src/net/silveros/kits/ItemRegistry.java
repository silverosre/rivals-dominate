package net.silveros.kits;

import net.silveros.kits.items.*;
import net.silveros.utility.Color;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ItemRegistry implements Color {
    public static Map<String, ItemAbility> abilityMap = new HashMap<>();

    public static void init() {
        new ItemsBunket().prepareItems();
        new ItemsArcher().prepareItems();
        new ItemsHamood().prepareItems();
        new ItemsHerobrine().prepareItems();
        new ItemsGummybear().prepareItems();
        new ItemsDefenseBear().prepareItems();
        new ItemsAttackBear().prepareItems();
        new ItemsSpeedBear().prepareItems();

        //Bunket
        addToMap(ABILITY_ShieldUp);
        addToMap(ABILITY_EmergencyRepairs);
        addToMap(ABILITY_SelfDestruct);
        //Archer
        addToMap(ABILITY_Snare);
        addToMap(ABILITY_Fletch);
        addToMap(ABILITY_FromAbove);
        //Hamood
        addToMap(ABILITY_DuneSlice);
        addToMap(ABILITY_PharaohsCurse);
        addToMap(ABILITY_Swift);
        //Herobrine
        addToMap(ABILITY_HerobrinePower);
        addToMap(ABILITY_FogCloak);
        //Gummybear
        addToMap(ABILITY_NormalBear);
        addToMap(ABILITY_DefenseBear);
        addToMap(ABILITY_AttackBear);
        addToMap(ABILITY_SpeedBear);
        addToMap(ABILITY_Numb);
        addToMap(ABILITY_StinkBomb);
        addToMap(ABILITY_ChaosZone);
    }

    private static void addToMap(ItemAbility ability) {
        abilityMap.put(ability.getItemMeta().getDisplayName(), ability);
    }

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

    //Gummy Bear
    public static ItemAbility ABILITY_NormalBear;
    public static ItemAbility ABILITY_AttackBear;
    public static ItemAbility ABILITY_DefenseBear;
    public static ItemAbility ABILITY_SpeedBear;
    public static ItemAbility ABILITY_Numb;
    public static ItemAbility ABILITY_ChaosZone;
    public static ItemAbility ABILITY_StinkBomb;
    public static ItemStack WEAPON_GummyClub;
    public static ItemStack SKULL_GummyBear;
    public static ItemStack SKULL_AttackBear;
    public static ItemStack SKULL_DefenseBear;
    public static ItemStack SKULL_SpeedBear;
    public static ItemStack ARMOR_NormalBearChestplate;
    public static ItemStack ARMOR_AttackBearChestplate;
    public static ItemStack ARMOR_DefenseBearChestplate;
    public static ItemStack ARMOR_SpeedBearChestplate;
    public static ItemStack ARMOR_NormalBearLeggings;
    public static ItemStack ARMOR_AttackBearLeggings;
    public static ItemStack ARMOR_DefenseBearLeggings;
    public static ItemStack ARMOR_SpeedBearLeggings;
    public static ItemStack ARMOR_NormalBearBoots;
    public static ItemStack ARMOR_AttackBearBoots;
    public static ItemStack ARMOR_DefenseBearBoots;
    public static ItemStack ARMOR_SpeedBearBoots;
    public static ItemStack ITEM_GummyEssence;
}
