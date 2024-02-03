package net.silveros.kits;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class KitHamood extends Kit {
    public KitHamood(int id, String name) {
        super(id, name);
        this.foodCount = 7;
    }

    @Override
    public void activateKit(Inventory inv) {
        super.activateKit(inv);

        inv.setItem(3, ItemRegistry.ABILITY_PharaohsCurse);
        inv.setItem(4, ItemRegistry.ABILITY_DuneSlice);
        //inv.setItem(5, ItemRegistry.ABILITY_Swift);
    }
}
