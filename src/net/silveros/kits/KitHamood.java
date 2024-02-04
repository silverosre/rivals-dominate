package net.silveros.kits;

import org.bukkit.inventory.PlayerInventory;

public class KitHamood extends Kit {
    public KitHamood(int id, String name) {
        super(id, name);
        this.foodCount = 7;
    }

    @Override
    public void activateKit(PlayerInventory inv) {
        super.activateKit(inv);

        inv.setItem(3, ItemRegistry.ABILITY_PharaohsCurse);
        inv.setItem(4, ItemRegistry.ABILITY_DuneSlice);
        //inv.setItem(5, ItemRegistry.ABILITY_Swift);
    }
}
