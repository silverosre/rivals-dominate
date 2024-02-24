package net.silveros.kits;

import net.silveros.kits.items.*;
import net.silveros.utility.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class ItemRegistry implements Color {
    public static Map<String, ItemAbility> abilityMap = new HashMap<>();
    public static ItemStack ITEM_Energy;

    public static void init() {
        generateEnergyItem();

        new ItemsBunket().prepareItems();
        new ItemsArcher().prepareItems();
        new ItemsRogue().prepareItems();
        new ItemsHerobrine().prepareItems();
        new ItemsGummybear().prepareItems();
        new ItemsDefenseBear().prepareItems();
        new ItemsAttackBear().prepareItems();
        new ItemsSpeedBear().prepareItems();
        new ItemsWizard().prepareItems();
        new ItemsGoblin().prepareItems();

        //Bunket
        addToMap(ABILITY_ShieldUp);
        addToMap(ABILITY_EmergencyRepairs);
        addToMap(ABILITY_SelfDestruct);
        //Archer
        addToMap(ABILITY_Snare);
        addToMap(ABILITY_Fletch);
        addToMap(ABILITY_FromAbove);
        //Rogue
        addToMap(ABILITY_Incantation);
        addToMap(ABILITY_Curse);
        addToMap(ABILITY_Swift);
        //Herobrine
        addToMap(ABILITY_HerobrinePower);
        addToMap(ABILITY_FogCloak);
        addToMap(ABILITY_Uncloak);
        //Gummybear
        addToMap(ABILITY_NormalBear);
        addToMap(ABILITY_DefenseBear);
        addToMap(ABILITY_AttackBear);
        addToMap(ABILITY_SpeedBear);
        addToMap(ABILITY_Numb);
        addToMap(ABILITY_StinkBomb);
        addToMap(ABILITY_ChaosZone);
        //Wizard
        addToMap(ABILITY_Zap);
        addToMap(ABILITY_Fireball);
        addToMap(ABILITY_Freeze);
        //Goblin
        addToMap(ABILITY_Steal);
        addToMap(ABILITY_Give);
        addToMap(ABILITY_Swarm);
    }

    private static void addToMap(ItemAbility ability) {
        abilityMap.put(ability.getItemMeta().getDisplayName(), ability);
    }

    private static void generateEnergyItem() {
        ItemStack item = new ItemStack(Material.DIAMOND, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(DARK_AQUA + BOLD + "Energy");

        item.setItemMeta(meta);
        ITEM_Energy = item;
    }

    public static ItemStack getEnergy(int amount) {
        ItemStack item = ITEM_Energy.clone();
        item.setAmount(amount);
        return item;
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
    public static ItemStack SKULL_Archer;
    //Rogue
    public static ItemAbility ABILITY_Curse;
    public static ItemAbility ABILITY_Incantation;
    public static ItemAbility ABILITY_Swift;
    public static ItemStack WEAPON_RogueSword;
    public static ItemStack WEAPON_DarkSister;
    public static ItemStack ARMOR_RogueChestplate;
    public static ItemStack ARMOR_RogueLeggings;
    public static ItemStack ARMOR_RogueBoots;
    public static ItemStack SKULL_Rogue;

    //Herobrine
    public static ItemAbility ABILITY_HerobrinePower;
    public static ItemAbility ABILITY_FogCloak;
    public static ItemAbility ABILITY_Uncloak;
    public static ItemStack WEAPON_HerobrineAxe;
    public static ItemStack WEAPON_HerobrineArrows;
    public static ItemStack WEAPON_HerobrineBow;
    public static ItemStack WEAPON_HerobrinePowerAxe;
    public static ItemStack WEAPON_HerobrinePowerBow;
    public static ItemStack ARMOR_HerobrineChestplate;
    public static ItemStack ARMOR_HerobrineLeggings;
    public static ItemStack ARMOR_HerobrineBoots;
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
    public static ItemStack ITEM_Numbness;

    //Wizard
    public static ItemAbility ABILITY_Zap;
    public static ItemAbility ABILITY_Fireball;
    public static ItemAbility ABILITY_Freeze;
    public static ItemStack WEAPON_WizardStaff;
    public static ItemStack SKULL_Wizard;
    public static ItemStack ARMOR_WizardChestplate;
    public static ItemStack ARMOR_WizardLeggings;
    public static ItemStack ARMOR_WizardBoots;

    //Goblin
    public static ItemAbility ABILITY_Steal;
    public static ItemAbility ABILITY_Give;
    public static ItemAbility ABILITY_Swarm;
    public static ItemStack SKULL_Goblin;
    public static ItemStack ARMOR_GoblinChestplate;
    public static ItemStack ARMOR_GoblinLeggings;
    public static ItemStack ARMOR_GoblinBoots;
}
