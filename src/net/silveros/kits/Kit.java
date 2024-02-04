package net.silveros.kits;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Kit {
    public static List<Kit> KIT_LIST = new ArrayList<>();
    public static Kit BUNKET;
    public static Kit ARCHER;
    public static Kit HAMOOD;
    public static Kit HEROBRINE;
    //public final static Kits WIZARD = new Kits("wizard");

    public final String kitName;
    public final int kitID;
    public int foodCount = 5;

    public static void init() {
        BUNKET = new KitBunket(0, "bunket");
        ARCHER = new KitArcher(1, "archer");
        HAMOOD = new KitHamood(2, "hamood");
        HEROBRINE = new KitHerobrine(3, "herobrine");
    }

    public Kit(int id, String name) {
        KIT_LIST.add(this);
        this.kitID = id;
        this.kitName = name;
    }

    public void activateKit(Inventory inv) {
        inv.clear();
        inv.setItem(8, new ItemStack(Material.APPLE, this.foodCount));
    }
}
