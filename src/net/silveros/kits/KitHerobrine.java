package net.silveros.kits;

import org.bukkit.inventory.PlayerInventory;

public class KitHerobrine extends Kit {
    public KitHerobrine(int id, String name) {
        super(id, name);
    }

    @Override
    public void activateKit(PlayerInventory inv) {
        super.activateKit(inv);

        inv.setItem(0, ItemRegistry.WEAPON_HerobrineAxe);
        inv.setItem(1, ItemRegistry.WEAPON_HerobrineBow);
        inv.setItem(3, ItemRegistry.ABILITY_HerobrinePower);
        inv.setItem(4, ItemRegistry.ABILITY_FogCloak);
        inv.setItem(7, ItemRegistry.WEAPON_HerobrineArrows);

        inv.setHelmet(ItemRegistry.SKULL_Herobrine);
    }
}
