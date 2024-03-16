package net.silveros.kits;

import net.silveros.entity.User;
import net.silveros.game.RivalsCore;
import net.silveros.utility.Color;
import net.silveros.utility.Util;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Husk;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class KitBandit extends Kit implements Color {
    public static final byte SLOT_STEAL = 3;
    public static final byte SLOT_GIVE = 4;
    public static final byte SLOT_RELOAD = 5;
    public static final int DEFAULT_BULLETS = 3;
    public static final byte SLOT_SIX_SHOOTER = 0;

    public KitBandit(int id, String name) {
        super(id, name);
    }

    @Override
    public void activateKit(Player player, PlayerInventory inv) {
        super.activateKit(player, inv);

        inv.setItem(0, ItemRegistry.WEAPON_SixShooter);
        inv.setItem(SLOT_STEAL, ItemRegistry.ABILITY_Steal);
        inv.setItem(SLOT_GIVE, ItemRegistry.ABILITY_Give);
        inv.setItem(SLOT_RELOAD, ItemRegistry.ABILITY_Reload);

        inv.setChestplate(ItemRegistry.ARMOR_BanditChestplate);
        inv.setLeggings(ItemRegistry.ARMOR_BanditLeggings);
        inv.setBoots(ItemRegistry.ARMOR_BanditBoots);
        inv.setHelmet(ItemRegistry.SKULL_Bandit);

        Util.getUserFromId(player.getUniqueId()).setBulletCount(DEFAULT_BULLETS);
    }

    @Override
    public int getHealth() {
        return 10;
    }

    @Override
    public int getStartingEnergy() {
        return 0;
    }

    public static void activateSteal(World world, Location local, Player player, PlayerInventory inv, User user) {
        for (Entity e : player.getNearbyEntities(2.5, 1, 2.5)) {
            if (e instanceof Player) {
                Player other = (Player)e;
                User otherUser = Util.getUserFromId(other.getUniqueId());

                if (!RivalsCore.matchingTeams(user.getTeam(), other, player)) {
                    if (otherUser.currentKit == Kit.BANDIT.kitID) {
                        continue;
                    }
                    inv.clear(SLOT_STEAL);
                    if (otherUser.getTotalEnergy() >= 1) {
                        otherUser.removeEnergy(1);
                        user.addEnergy(1);

                        world.playSound(local, Sound.ENTITY_WITCH_CELEBRATE, 0.75f, 1.75f);

                        world.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                        world.playSound(player, Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 2);
                        return;
                    }
                }
            }
        }

        player.playSound(player, Sound.ENTITY_VILLAGER_NO, 0.75f, 1);
        player.sendMessage(RED + "No player or energy to steal from within range!");
    }

    public static void activateGive(World world, Location local, Player player, PlayerInventory inv, User user) {
        for (Entity e : player.getNearbyEntities(2.5, 1, 2.5)) {
            if (e instanceof Player) {
                Player other = (Player)e;
                User otherUser = Util.getUserFromId(other.getUniqueId());


                if (RivalsCore.matchingTeams(user.getTeam(), other, player)) {
                    if (otherUser.currentKit == Kit.BANDIT.kitID) {
                        continue;
                    }
                    inv.clear(SLOT_GIVE);
                    otherUser.addEnergy(1);

                    world.playSound(local, Sound.ENTITY_WITCH_CELEBRATE, 0.75f, 1.75f);

                    world.playSound(other, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                    world.playSound(other, Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 2);
                    return;
                }
            }
        }

        player.playSound(player, Sound.ENTITY_VILLAGER_NO, 0.75f, 1);
        player.sendMessage(RED + "No player to give energy to within range!");
        user.addEnergy(1);
    }

    public static void activateReload(World world, Location local, Player player, PlayerInventory inv, User user) {
        if (user.bulletCount >= 6) {
            player.playSound(player, Sound.ENTITY_VILLAGER_NO, 0.75f, 1);
            player.sendMessage(RED + "Already at max ammo capacity!");
            user.addEnergy(2);
        } else {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 3, false, false));

            user.bulletCount += 2;

            if (user.bulletCount > 6) {
                user.bulletCount = 6;
            }

            world.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1.5f);
            world.playSound(player.getLocation(), Sound.ENTITY_HORSE_GALLOP, 1, 0.5f);

            Vector dir = local.getDirection().multiply(1.25);
            world.spawnParticle(Particle.BLOCK_CRACK, local.add(dir.getX(), 1.25, dir.getZ()), 2, 0, 0, 0, 0.1, Material.GOLD_BLOCK.createBlockData());
        }
    }

    public static void useSixShooter(World world, Player player, User user) {
        Location eyes = player.getEyeLocation();
        Vector dir = eyes.getDirection().multiply(1.5);
        int distance = 16;
        int dmg = 6;

        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10, 3, false, false));

        world.spawnParticle(Particle.SMOKE_NORMAL, eyes.add(dir), 40, 0.2, 0.2, 0.2, 0.1);
        world.playSound(eyes, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1.5f, 2);

        BlockIterator blocks = new BlockIterator(eyes, 1, distance);
        while(blocks.hasNext()) {
            Location l = blocks.next().getLocation();
            if (l.getBlock().getType().isSolid()) {
                break;
            } else {
                //world.setBlockData(l, Material.GLASS.createBlockData());
                world.spawnParticle(Particle.REDSTONE, l, 5, 0.25, 0.25, 0.25, 0.01, new Particle.DustOptions(org.bukkit.Color.fromRGB(0x111111), 1));

                for (Entity e : world.getNearbyEntities(l, 1, 1, 1)) {
                    if (e instanceof Player) {
                        User other = Util.getUserFromId(e.getUniqueId());

                        if (!RivalsCore.matchingTeams(user.getTeam(), e, player)) {
                            other.getPlayer().damage(dmg, player);
                            break;
                        }
                    } else if (e instanceof Husk) {
                        ((Husk) e).damage(dmg);
                        break;
                    }
                }
            }
        }
    }
}
