package net.silveros.utility;

import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

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
}
