package net.silveros.kits;

import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class Kit {
    public static List<Kit> KIT_LIST = new ArrayList<>();
    public static Kit BUNKET;
    public static Kit ARCHER;
    public static Kit HAMOOD;
    //public final static Kits WIZARD = new Kits("wizard");

    public final String kitName;
    public final int kitID;

    public static void init() {
        BUNKET = new KitBunket(0, "bunket");
        ARCHER = new Kit(1, "archer");
        HAMOOD = new Kit(2, "hamood");
    }

    public Kit(int id, String name) {
        KIT_LIST.add(this);
        this.kitID = id;
        this.kitName = name;
    }

    public void activateKit(Inventory inv) {
        inv.clear();
    }
}
