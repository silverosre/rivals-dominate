package net.silveros.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import java.util.HashMap;
import java.util.Map;

public abstract class Kit {
    public static Map<Integer, Kit> KIT_LIST = new HashMap<>();
    public static Kit BUNKET;
    public static Kit ARCHER;
    public static Kit ROGUE;
    public static Kit HEROBRINE;
    public static Kit GUMMY_BEAR;
    public static Kit WIZARD;
    public static Kit GOBLIN;
    public static Kit BANDIT;

    public final String kitName;
    public final int kitID;
    public int foodCount = 5;

    public static void init() {
        BUNKET = new KitBunket(0, "bunket");
        ARCHER = new KitArcher(1, "archer");
        ROGUE = new KitRogue(2, "rogue");
        HEROBRINE = new KitHerobrine(3, "herobrine");
        GUMMY_BEAR = new KitGummyBear(4, "gummy");
        WIZARD = new KitWizard(5, "wizard");
        BANDIT = new KitBandit(6, "bandit");
    }

    public Kit(int id, String name) {
        KIT_LIST.put(id, this);
        this.kitID = id;
        this.kitName = name;
    }

    public void activateKit(Player player, PlayerInventory inv) {
        player.setMaxHealth(this.getHealth());
        player.setHealth(this.getHealth());

        for (PotionEffect e : player.getActivePotionEffects()) {
            player.removePotionEffect(e.getType());
        }

        inv.clear();
        inv.setItem(8, new ItemStack(Material.APPLE, this.foodCount));
    }

    public abstract int getHealth();

    public abstract int getStartingEnergy();
}
