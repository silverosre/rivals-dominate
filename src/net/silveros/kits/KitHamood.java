package net.silveros.kits;

import net.silveros.entity.RivalsTags;
import net.silveros.entity.User;
import net.silveros.game.RivalsCore;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class KitHamood extends Kit {
    public static final byte SLOT_PHARAOHS_CURSE = 3;
    public static final byte SLOT_DUNE_SLICER = 4;
    public static final byte SLOT_SWIFT = 5;

    public KitHamood(int id, String name) {
        super(id, name);
        this.foodCount = 7;
    }

    @Override
    public void activateKit(Player player, PlayerInventory inv) {
        super.activateKit(player, inv);

        inv.setItem(0, ItemRegistry.WEAPON_HamoodSword);
        inv.setItem(SLOT_PHARAOHS_CURSE, ItemRegistry.ABILITY_PharaohsCurse);
        inv.setItem(SLOT_DUNE_SLICER, ItemRegistry.ABILITY_DuneSlicer);
        //inv.setItem(SLOT_SWIFT, ItemRegistry.ABILITY_Swift);
        inv.setChestplate(ItemRegistry.ARMOR_HamoodChestplate);
        inv.setLeggings(ItemRegistry.ARMOR_HamoodLeggings);
        inv.setBoots(ItemRegistry.ARMOR_HamoodBoots);
        inv.setHelmet(ItemRegistry.SKULL_Hamood);
    }

    @Override
    public int getHealth() {
        return 18;
    }

    @Override
    public int getStartingEnergy() {
        return 2;
    }

    public static void activateSwift(World world, Location local, Player player, PlayerInventory inv, User user) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 49, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 60, 1, false, false));
        inv.clear(SLOT_SWIFT);
    }

    public static void activateDuneSlicer(World world, Location local, Player player, PlayerInventory inv, User user) {
        inv.setItem(0, ItemRegistry.WEAPON_DuneSlicer);
        world.playSound(local, Sound.ITEM_TOTEM_USE, 1, 1);
        inv.clear(SLOT_DUNE_SLICER);
    }

    public static void activatePharaohsCurse(World world, Location local, Player player, PlayerInventory inv, User user) {
        Item curse = world.dropItemNaturally(local, new ItemStack(Material.SUSPICIOUS_SAND));
        curse.setPickupDelay(Integer.MAX_VALUE);
        curse.addScoreboardTag(RivalsTags.PHARAOHS_CURSE_ENTITY);
        curse.setVelocity(local.getDirection().multiply(0.75));
        curse.setInvulnerable(true);
        RivalsCore.addEntryToTeam(user.getTeam(), curse);

        world.playSound(local, Sound.ENTITY_TNT_PRIMED, 1, 1);

        inv.clear(SLOT_PHARAOHS_CURSE);
    }
}
