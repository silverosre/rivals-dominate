package net.silveros.kits;

import org.bukkit.inventory.PlayerInventory;

public class KitGoblin extends Kit {
    public KitGoblin(int id, String name) {
        super(id, name);
    }

    @Override
    public void activateKit(PlayerInventory inv) {
        super.activateKit(inv);

        inv.setItem(2, ItemRegistry.ABILITY_Steal);
        inv.setItem(3, ItemRegistry.ABILITY_Give);
        inv.setItem(4, ItemRegistry.ABILITY_Swarm);

        inv.setChestplate(ItemRegistry.ARMOR_GoblinChestplate);
        inv.setLeggings(ItemRegistry.ARMOR_GoblinLeggings);
        inv.setBoots(ItemRegistry.ARMOR_GoblinBoots);
        inv.setHelmet(ItemRegistry.SKULL_Goblin);
    }

    @Override
    public int getStartingEnergy(){return 0;}
}
