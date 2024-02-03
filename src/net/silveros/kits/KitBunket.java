package net.silveros.kits;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class KitBunket extends Kit {
    public KitBunket(int id, String name) {
        super(id, name);
    }

    @Override
    public void activateKit(Inventory inv) {
        super.activateKit(inv);

        inv.setItem(3, ItemRegistry.ABILITY_EmergencyRepairs);
        inv.setItem(4, ItemRegistry.ABILITY_SelfDestruct);
        inv.setItem(5, ItemRegistry.ABILITY_ShieldUp);
    }

    public static ItemStack getShieldItem() {
        ItemStack item = new ItemStack(Material.SHIELD, 1);
        ItemMeta meta = item.getItemMeta();

        ((Damageable)meta).setDamage(35);

        item.setItemMeta(meta);
        return item;
    }
}
