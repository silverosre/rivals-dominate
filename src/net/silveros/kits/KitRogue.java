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

public class KitRogue extends Kit {
    public static final byte SLOT_CURSE = 3;
    public static final byte SLOT_INCANTATION = 4;
    public static final byte SLOT_SWIFT = 5;

    public KitRogue(int id, String name) {
        super(id, name);
        this.foodCount = 7;
    }

    @Override
    public void activateKit(Player player, PlayerInventory inv) {
        super.activateKit(player, inv);

        inv.setItem(0, ItemRegistry.WEAPON_RogueSword);
        inv.setItem(SLOT_CURSE, ItemRegistry.ABILITY_Curse);
        inv.setItem(SLOT_INCANTATION, ItemRegistry.ABILITY_Incantation);
        inv.setChestplate(ItemRegistry.ARMOR_RogueChestplate);
        inv.setLeggings(ItemRegistry.ARMOR_RogueLeggings);
        inv.setBoots(ItemRegistry.ARMOR_RogueBoots);
        inv.setHelmet(ItemRegistry.SKULL_Rogue);
    }

    @Override
    public int getHealth() {return 18;}

    @Override
    public int getStartingEnergy() {return 2;}

    public static void activateSwift(World world, Location local, Player player, PlayerInventory inv, User user) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 38, 49, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 60, 1, true, true));
        inv.clear(SLOT_SWIFT);
    }
    public static void activateCurse(World world, Location local, Player player, PlayerInventory inv, User user) {
        Item curse = world.dropItemNaturally(local, new ItemStack(Material.RED_SAND));
        curse.setPickupDelay(Integer.MAX_VALUE);
        curse.addScoreboardTag(RivalsTags.CURSE_ENTITY);
        curse.setVelocity(local.getDirection().multiply(0.75));
        curse.setInvulnerable(true);
        RivalsCore.addEntryToTeam(user.getTeam(), curse);

        world.playSound(local, Sound.ENTITY_TNT_PRIMED, 1, 1);

        inv.clear(SLOT_CURSE);
    }
    public static void activateIncantation(World world, Location local, Player player, PlayerInventory inv, User user) {
        inv.setItem(0, ItemRegistry.WEAPON_DarkSister);
        world.playSound(local, Sound.ITEM_TOTEM_USE, 1, 1);
        inv.clear(SLOT_INCANTATION);
    }
}
