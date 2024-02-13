package net.silveros.kits;

import org.bukkit.inventory.PlayerInventory;

public class KitWizard extends Kit {
    public KitWizard(int id, String name) {
        super(id, name);
        this.foodCount = 5;
    }

    @Override
    public void activateKit(PlayerInventory inv) {
        super.activateKit(inv);

        inv.setItem(0, ItemRegistry.WEAPON_WizardStaff);
        inv.setItem(2, ItemRegistry.ABILITY_Zap);
        inv.setItem(3, ItemRegistry.ABILITY_Fireball);
        inv.setItem(4, ItemRegistry.ABILITY_Freeze);

        inv.setHelmet(ItemRegistry.SKULL_Wizard);
        inv.setChestplate(ItemRegistry.ARMOR_WizardChestplate);
        inv.setLeggings(ItemRegistry.ARMOR_WizardLeggings);
        inv.setBoots(ItemRegistry.ARMOR_WizardBoots);
    }

    @Override
    public int getStartingEnergy() {
        return 8;
    }
}
