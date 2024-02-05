package net.silveros.kits;

import org.bukkit.entity.Item;
import org.bukkit.inventory.PlayerInventory;

public class KitGummyBear extends Kit{
    public KitGummyBear(int id, String name){
        super(id, name);
        this.foodCount = 5;
    }

    @Override
    public void activateKit(PlayerInventory inv) {
        super.activateKit(inv);

        inv.setItem(0, ItemRegistry.WEAPON_GummyClub);
        inv.setItem(3, ItemRegistry.ABILITY_DefenseBear);
        inv.setItem(4, ItemRegistry.ABILITY_AttackBear);
        inv.setItem(5, ItemRegistry.ABILITY_SpeedBear);
        inv.setItem(9, ItemRegistry.ITEM_GummyEssence);
        inv.setChestplate(ItemRegistry.ARMOR_NormalBearChestplate);
        inv.setLeggings(ItemRegistry.ARMOR_NormalBearLeggings);
        inv.setBoots(ItemRegistry.ARMOR_NormalBearBoots);
        inv.setHelmet(ItemRegistry.SKULL_GummyBear);
    }
}
