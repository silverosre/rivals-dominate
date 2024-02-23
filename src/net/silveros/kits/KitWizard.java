package net.silveros.kits;

import net.silveros.entity.RivalsTags;
import net.silveros.entity.User;
import net.silveros.game.RivalsCore;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class KitWizard extends Kit {
    public static final byte SLOT_ZAP = 2;
    public static final byte SLOT_FIREBALL = 3;
    public static final byte SLOT_FREEZE = 4;

    public KitWizard(int id, String name) {
        super(id, name);
        this.foodCount = 5;
    }

    @Override
    public void activateKit(Player player, PlayerInventory inv) {
        super.activateKit(player, inv);

        inv.setItem(0, ItemRegistry.WEAPON_WizardStaff);
        inv.setItem(SLOT_ZAP, ItemRegistry.ABILITY_Zap);
        inv.setItem(SLOT_FIREBALL, ItemRegistry.ABILITY_Fireball);
        inv.setItem(SLOT_FREEZE, ItemRegistry.ABILITY_Freeze);

        inv.setHelmet(ItemRegistry.SKULL_Wizard);
        inv.setChestplate(ItemRegistry.ARMOR_WizardChestplate);
        inv.setLeggings(ItemRegistry.ARMOR_WizardLeggings);
        inv.setBoots(ItemRegistry.ARMOR_WizardBoots);
    }

    @Override
    public int getHealth() {
        return 16;
    }

    @Override
    public int getStartingEnergy() {
        return 8;
    }

    public static void activateZap(World world, Location local, Player player, PlayerInventory inv, User user) {
        Item zap = world.dropItemNaturally(local, new ItemStack(Material.REDSTONE_LAMP));
        zap.setPickupDelay(Integer.MAX_VALUE);
        zap.addScoreboardTag(RivalsTags.ZAP_ENTITY);
        zap.setVelocity(local.getDirection().multiply(0.75));
        zap.setInvulnerable(true);
        RivalsCore.addEntryToTeam(user.getTeam(), zap);

        world.playSound(local, Sound.BLOCK_FIRE_EXTINGUISH, 1, 1);
        inv.clear(SLOT_ZAP);
    }

    public static void activateFireball(World world, Location local, Player player, PlayerInventory inv, User user) {
        Fireball fireball = (Fireball)world.spawnEntity(local.add(0, 1.25, 0), EntityType.FIREBALL);
        fireball.setDirection(local.getDirection());
        fireball.setYield(1.25f);
        fireball.setShooter(player);
        fireball.setVelocity(local.getDirection().multiply(0.25));

        world.playSound(local, Sound.ENTITY_BLAZE_SHOOT, 0.75f, 1);
        inv.clear(SLOT_FIREBALL);
    }

    public static void activateFreeze(World world, Location local, Player player, PlayerInventory inv, User user) {
        Marker freeze = (Marker)world.spawnEntity(local, EntityType.MARKER);

        for (Entity e : freeze.getNearbyEntities(2, 1, 2)) {
            if (e instanceof Player) {
                Player other = (Player)e;
                if (!RivalsCore.matchingTeams(user.getTeam(), player, other)) {
                    other.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80, 1, false, false));
                }
            }
        }

        world.spawnParticle(Particle.SNOWFLAKE, local, 200, 1.25, 0, 1.25, 0);
        world.spawnParticle(Particle.REDSTONE, local, 200, 1.25, 0, 1.25, new Particle.DustOptions(org.bukkit.Color.fromRGB(0xE1FFFF), 1));

        world.playSound(local, Sound.ENTITY_PLAYER_HURT_FREEZE, 0.75f, 1);
        inv.clear(SLOT_FREEZE);
    }
}
