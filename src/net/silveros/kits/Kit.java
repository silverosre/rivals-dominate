package net.silveros.kits;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Map;

public abstract class Kit {
    public static Map<Integer, Kit> KIT_LIST = new HashMap<>();
    public static Kit BUNKET;
    public static Kit ARCHER;
    public static Kit HAMOOD;
    public static Kit HEROBRINE;
    public static Kit GUMMY_BEAR;
    public static Kit WIZARD;
    public static Kit GOBLIN;

    public final String kitName;
    public final int kitID;
    public int foodCount = 5;

    public static void init() {
        BUNKET = new KitBunket(0, "bunket");
        ARCHER = new KitArcher(1, "archer");
        HAMOOD = new KitHamood(2, "hamood");
        HEROBRINE = new KitHerobrine(3, "herobrine");
        GUMMY_BEAR = new KitGummyBear(4, "gummy");
        WIZARD = new KitWizard(5, "wizard");
        GOBLIN = new KitGoblin(6, "goblin");
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

    public abstract int getStartingEnergy();
}
