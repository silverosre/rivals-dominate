package net.silveros.main;

import net.silveros.commands.RDCommands;
import net.silveros.entity.User;
import net.silveros.events.AbilityEvents;
import net.silveros.events.TickEvent;
import net.silveros.kits.ItemRegistry;
import net.silveros.kits.Kit;
import net.silveros.utility.Color;
import net.silveros.utility.Util;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainPlugin extends JavaPlugin implements Color {
    public static List<User> players = new ArrayList<>();

    @Override
    public void onEnable() {
        Util.initialize(this);
        Util.print(GREEN + "Enabling test plugin...");

        //item and kit initialization
        ItemRegistry.init();
        Kit.init();

        //register events & commands
        //Util.registerEvent(new TickEvent(this));
        Util.registerEvent(new AbilityEvents());

        Util.registerCommand("kit", new RDCommands(this));
        //this.getCommand("kit").setExecutor(new RDCommands(this));

        this.startTicking();
    }

    //Main tick loop
    private void startTicking() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (User user : players) {
                    user.onTick();
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
}
