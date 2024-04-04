package net.silveros.kits.items;

import net.silveros.kits.Abilities;
import net.silveros.kits.ItemAbility;
import net.silveros.utility.Color;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
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
    /*protected static ItemAbility getBlankAbility(int cost, Abilities ability) {
        return new ItemAbility(Material.ENCHANTED_BOOK, 1, cost, ability);
    }*/

    protected static ItemAbility getBlankAbility(Abilities ability) {
        return new ItemAbility(Material.ENCHANTED_BOOK, 1, ability.getCost(), ability);
    }

    protected static ItemStack getArrows(int count) {
        return new ItemStack(Material.ARROW, count);
    }

    /**Used for item names.*/
    protected static String itemCost(int cost) {
        return LIGHT_PURPLE + " (" + AQUA + BOLD + cost + RESET + LIGHT_PURPLE + ")";
    }

    /**Used for item names.*/
    protected static String abilityName(String ability) {
        return GREEN + BOLD + "Ability" + GOLD + ": " + WHITE + ability;
    }

    /*protected static List<String> addLore(String... args) {
        return new ArrayList<>(Arrays.asList(args));
    }*/

    protected static List<String> addLore(Abilities ability, String... args) {
        ArrayList<String> lore = new ArrayList<>(Arrays.asList(args));

        String cooldown = ability.getCooldownForDisplay() + GRAY + "s";
        if (ability.getCooldown() < 0) {
            cooldown = "One-time use";
        }

        lore.add(AQUA + BOLD + "Energy Cost" + GOLD + ": " + GRAY + ability.getCost());
        lore.add(LIGHT_PURPLE + BOLD + "Ability Cooldown" + GOLD + ": " + GRAY + cooldown);

        return lore;
    }

    /**Do not use this for abilities.*/
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
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);
        return item;
    }
}
