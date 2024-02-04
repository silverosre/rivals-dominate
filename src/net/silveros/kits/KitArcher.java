package net.silveros.kits;

import org.bukkit.inventory.Inventory;


public class KitArcher extends Kit{
    public KitArcher(int id, String name) {
        super(id, name);
    }

    @Override
    public void activateKit(Inventory inv) {
        super.activateKit(inv);

        inv.setItem(3, ItemRegistry.ABILITY_Fletch);
        inv.setItem(4, ItemRegistry.ABILITY_Snare);
        inv.setItem(5, ItemRegistry.ABILITY_Quickshot);
        inv.setItem(1, ItemRegistry.WEAPON_Bow);
        inv.setItem(0, ItemRegistry.WEAPON_WoodenKnife);
        inv.setItem(7, ItemRegistry.WEAPON_ArcherArrows);
        inv.setItem(38, ItemRegistry.ARMOR_ArcherChestplate);
    }
}
