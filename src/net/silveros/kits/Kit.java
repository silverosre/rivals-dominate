package net.silveros.kits;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Map;

public class Kit {
    public static Map<Integer, Kit> KIT_LIST = new HashMap<>();
    public static Kit BUNKET;
    public static Kit ARCHER;
    public static Kit HAMOOD;
    public static Kit HEROBRINE;
    public static Kit GUMMY_BEAR;
    //public final static Kits WIZARD = new Kits("wizard");

    public final String kitName;
    public final int kitID;
    public int foodCount = 5;

    public static void init() {
        BUNKET = new KitBunket(0, "bunket");
        ARCHER = new KitArcher(1, "archer");
        HAMOOD = new KitHamood(2, "hamood");
        HEROBRINE = new KitHerobrine(3, "herobrine");
        GUMMY_BEAR = new KitGummyBear(4, "gummy");
    }

    public Kit(int id, String name) {
        KIT_LIST.put(id, this);
        this.kitID = id;
        this.kitName = name;
    }

    public void activateKit(PlayerInventory inv) {
        inv.clear();
        inv.setItem(8, new ItemStack(Material.APPLE, this.foodCount));
    }
}
