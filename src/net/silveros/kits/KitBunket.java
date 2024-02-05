package net.silveros.kits;

import org.bukkit.inventory.PlayerInventory;

public class KitBunket extends Kit {
    public KitBunket(int id, String name) {
        super(id, name);
        this.foodCount = 4;
    }

    @Override
    public void activateKit(PlayerInventory inv) {
        super.activateKit(inv);

        inv.setItemInOffHand(ItemRegistry.WEAPON_BunketShield);
        inv.setItem(3, ItemRegistry.ABILITY_EmergencyRepairs);
        inv.setItem(4, ItemRegistry.ABILITY_SelfDestruct);
        inv.setItem(5, ItemRegistry.ABILITY_ShieldUp);
        inv.setChestplate(ItemRegistry.ARMOR_BunketChestplate);
        inv.setLeggings(ItemRegistry.ARMOR_BunketLeggings);
        inv.setBoots(ItemRegistry.ARMOR_BunketBoots);
        inv.setHelmet(ItemRegistry.SKULL_Bunket);
    }

    @Override
    public int getStartingEnergy() {
        return 1;
    }
}
