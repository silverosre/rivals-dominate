package net.silveros.kits.items;

import net.silveros.kits.ItemAbility;
import net.silveros.utility.Color;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public abstract class Items implements Color {
    public void prepareItems() {
        this.prepareFeatures();
        this.prepareArmor();
    }

    protected abstract void prepareFeatures();

    protected abstract void prepareArmor();

    //Utility
    protected static ItemAbility getBlankAbility(int cost) {
        return new ItemAbility(Material.ENCHANTED_BOOK, 1, cost);
    }

    protected static ItemStack getArrows(int count) {
        return new ItemStack(Material.ARROW, count);
    }

    protected static String itemCost(int cost) {
        return LIGHT_PURPLE + "(" + DARK_AQUA + BOLD + cost + RESET + LIGHT_PURPLE + ")";
    }

    protected static List<String> addLore(String... args) {
        return new ArrayList<>(Arrays.asList(args));
    }

    /**@param id Minecraft URL of specified skull.*/
    public static ItemStack getSkull(String id, String itemName) {
        PlayerProfile profile = Bukkit.createPlayerProfile(UUID.fromString("92864445-51c5-4c3b-9039-517c9927d1b4")); // use random ass uuid
        PlayerTextures textures = profile.getTextures();

        try {
            textures.setSkin(URI.create("https://textures.minecraft.net/texture/" + id).toURL());
        } catch (MalformedURLException exception) {
            throw new RuntimeException("Invalid URL", exception);
        }

        profile.setTextures(textures);

        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta)item.getItemMeta();
        meta.setOwnerProfile(profile);
        meta.setDisplayName(YELLOW + itemName);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);

        item.setItemMeta(meta);
        return item;
    }
}
