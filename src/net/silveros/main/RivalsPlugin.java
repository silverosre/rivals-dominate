package net.silveros.main;

import net.silveros.commands.*;
import net.silveros.entity.User;
import net.silveros.events.AbilityEvents;
import net.silveros.events.PlayerEvents;
import net.silveros.game.RivalsCore;
import net.silveros.kits.ItemRegistry;
import net.silveros.kits.Kit;
import net.silveros.utility.Color;
import net.silveros.utility.Util;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class RivalsPlugin extends JavaPlugin implements Color {
    public static List<User> players = new ArrayList<>();
    public static RivalsCore core;

    public static final String WELCOME_MESSAGE = LIGHT_PURPLE + "Welcome to Rivals: Dominate!";

    @Override
    public void onEnable() {
        Util.initialize(this);
        Util.print(GREEN + "Enabling test plugin...");

        //item and kit initialization
        ItemRegistry.init();
        Kit.init();

        //register events & commands
        Util.registerEvent(new AbilityEvents());
        Util.registerEvent(new PlayerEvents());

        Util.registerCommand("kit", new CommandKit(this));
        Util.registerCommand("kitp", new CommandKitP(this));
        Util.registerCommand("rteam", new CommandRTeam(this));
        Util.registerCommand("energy", new CommandEnergy(this));
        Util.registerCommand("score", new CommandScore(this));
        Util.registerCommand("setuppoints", new CommandSetupPoints(this));

        //misc & tick
        this.startTicking();
    }

    private void startTicking() {
        //initialize Rivals core after the server has officially finished booting up
        Bukkit.getScheduler().runTask(this, () -> {
            core = new RivalsCore();
        });

        //main tick loop
        new BukkitRunnable() {
            @Override
            public void run() {
                for (User user : players) {
                    user.onTick();
                }

                if (getWorld() != null) {
                    core.onTick(getWorld());
                }
            }
        }.runTaskTimer(this, 0L, 1L);
    }

    @Override
    public void onDisable() {
        Util.print(RED + "Disabling test plugin...");
    }

    public static void addPlayer(Player player) {
        players.add(new User(player.getUniqueId()));
        Util.print("Added player " + player.getName() + ":" + player.getUniqueId());
    }

    public static void removePlayer(Player player) {
        User user = Util.getUserFromId(player.getUniqueId());
        if (user != null) {
            players.remove(user);
            Util.print("Removed player " + player.getName() + ":" + player.getUniqueId());
        } else {
            Util.print("Could not remove player " + player.getName() + ":" + player.getUniqueId());
        }
    }

    /**Can return null!*/
    public static World getWorld() {
        if (!players.isEmpty()) {
            World world = players.get(0).getPlayer().getWorld();
            return world;
        }

        return null;
    }
}
