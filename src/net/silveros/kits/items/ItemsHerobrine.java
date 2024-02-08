package net.silveros.kits.items;

import net.silveros.kits.Abilities;
import net.silveros.kits.ItemAbility;
import net.silveros.kits.ItemRegistry;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ItemsHerobrine extends Items {
    @Override
    protected void prepareFeatures() {
        generateHerobrinePower(6);
        generateFogCloak(2);
        generateHerobrineAxe();
        generateHerobrineBow();
        ItemRegistry.WEAPON_HerobrineArrows = getArrows(3);
    }

    @Override
    protected void prepareArmor() {
        ItemRegistry.SKULL_Herobrine = getSkull(Skulls.HEROBRINE, "Herobrine Head");
    }

    private static void generateHerobrinePower(int cost) {
        ItemAbility item = getBlankAbility(cost, Abilities.HEROBRINE_POWER);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Herobrine Power " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Gain Herobrine's ultimate power.",
                WHITE + ITALIC + "Increases axe and bow power for",
                WHITE + ITALIC + "a short time.",
                DARK_AQUA + "Costs " + cost + " energy: 55 second cooldown"
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_HerobrinePower = item;
    }

    private static void generateFogCloak(int cost) {
        ItemAbility item = getBlankAbility(cost, Abilities.FOG_CLOAK);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(LIGHT_PURPLE + "Ability: Fog Cloak " + itemCost(cost));
        meta.setLore(addLore(
                WHITE + ITALIC + "Cloak yourself in the mist.",
                WHITE + ITALIC + "Makes you invisible until you take",
                WHITE + ITALIC + "damage, attack, or step on a point.",
                DARK_AQUA + "Costs " + cost + " energy: 30 second cooldown"
        ));

        item.setItemMeta(meta);
        ItemRegistry.ABILITY_FogCloak = item;
    }

    private static void generateHerobrineAxe() {
        ItemStack item = new ItemStack(Material.DIAMOND_AXE, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(YELLOW + "Herobrine Axe");
        //meta.setLore(addLore(GRAY + ITALIC + "The memories call out to you..."));

        item.setItemMeta(meta);
        ItemRegistry.WEAPON_HerobrineAxe = item;
    }

    private static void generateHerobrineBow() {
        ItemStack item = new ItemStack(Material.BOW, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(YELLOW + "Herobrine Bow");
        //meta.setLore(addLore(GRAY + ITALIC + "Stand in the distance..."));

        item.setItemMeta(meta);
        ItemRegistry.WEAPON_HerobrineBow = item;
    }
}
