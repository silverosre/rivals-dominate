package net.silveros.kits;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemEntry extends ItemStack {
    public int energyCost;

    public ItemEntry(Material material, int amount, int cost) {
        super(material, amount);
        this.setEnergyCost(cost);
    }

    public void setEnergyCost(int cost) {
        this.energyCost = cost;
    }
}
