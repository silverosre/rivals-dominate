package net.silveros.kits;

import net.silveros.entity.User;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class KitHerobrine extends Kit {
    public KitHerobrine(int id, String name) {
        super(id, name);
    }

    @Override
    public void activateKit(Player player, PlayerInventory inv) {
        super.activateKit(player, inv);

        inv.setItem(0, ItemRegistry.WEAPON_HerobrineAxe);
        inv.setItem(1, ItemRegistry.WEAPON_HerobrineBow);
        inv.setItem(3, ItemRegistry.ABILITY_HerobrinePower);
        inv.setItem(4, ItemRegistry.ABILITY_FogCloak);
        inv.setItem(7, ItemRegistry.WEAPON_HerobrineArrows);

        inv.setHelmet(ItemRegistry.SKULL_Herobrine);
        inv.setChestplate(ItemRegistry.ARMOR_HerobrineChestplate);
        inv.setLeggings(ItemRegistry.ARMOR_HerobrineLeggings);
        inv.setBoots(ItemRegistry.ARMOR_HerobrineBoots);
    }

    @Override
    public int getHealth() {
        return 20;
    }

    @Override
    public int getStartingEnergy() {
        return 3;
    }

    public static void activateHerobrinePower(World world, Location local, Player player, PlayerInventory inv, User user) {
        inv.clear(3);

        inv.clear(0);
        inv.clear(1);
        inv.clear(7);
        inv.setItem(0, ItemRegistry.WEAPON_HerobrinePowerAxe);
        inv.setItem(1, ItemRegistry.WEAPON_HerobrinePowerBow);
        inv.setItem(7, ItemRegistry.WEAPON_HerobrineArrows);
    }

    public static void activateFogCloak(World world, Location local, Player player, PlayerInventory inv, User user) {
        inv.clear(4);

        inv.clear(1);
        inv.clear(7);

        //clear armor
        inv.clear(36);
        inv.clear(37);
        inv.clear(38);
        inv.clear(39);

        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, PotionEffect.INFINITE_DURATION, 0, false, false));

        world.spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, local.add(0, 0.25, 0), 40, 0.25, 1, 0.25, 0.1);
        world.playSound(local, Sound.AMBIENT_CAVE, 1, 1);

        user.setFogCloakState(true);
    }

    public static void activateUncloak(World world, Location local, Player player, PlayerInventory inv, User user) {
        for (PotionEffect e : player.getActivePotionEffects()) {
            player.removePotionEffect(e.getType());
        }

        world.spawnParticle(Particle.SMOKE_LARGE, local, 30, 0.25, 1, 0.25, 0);
        world.playSound(local, Sound.ENTITY_ENDERMAN_SCREAM, 1, 1);
    }
}
