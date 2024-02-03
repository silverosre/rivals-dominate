package net.silveros.utility;

import net.silveros.entity.User;
import net.silveros.main.RivalsPlugin;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class Util {
    private static JavaPlugin mainPlugin;

    public static void initialize(JavaPlugin plugin) {
        mainPlugin = plugin;
    }

    public static void print(String s) {
        server().getConsoleSender().sendMessage(s);
    }

    public static void registerEvent(Listener eventListener) {
        server().getPluginManager().registerEvents(eventListener, getPlugin());
    }

    public static void registerCommand(String cmdName, CommandExecutor command) {
        getPlugin().getCommand(cmdName).setExecutor(command);
    }

    public static JavaPlugin getPlugin() {
        return mainPlugin;
    }

    private static Server server() {
        return getPlugin().getServer();
    }

    /**Can return null!*/
    public static User getUserFromId(UUID uuid) {
        for (User user : RivalsPlugin.players) {
            if (user.playerId.equals(uuid)) {
                return user;
            }
        }

        return null;
    }

    /**Can return null!*/
    public static Player getPlayerFromId(UUID uuid) {
        return server().getPlayer(uuid);
    }
}
