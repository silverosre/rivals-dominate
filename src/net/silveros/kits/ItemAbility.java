package net.silveros.kits;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemAbility extends ItemStack {
    public int energyCost;
    public Abilities ability;

    public ItemAbility(Material material, int amount, int cost, Abilities a) {
        super(material, amount);
        this.setEnergyCost(cost);
        this.setAbility(a);
    }

    public void setEnergyCost(int cost) {
        this.energyCost = cost;
    }
    public void setAbility(Abilities a) {
        this.ability = a;
    }
}
