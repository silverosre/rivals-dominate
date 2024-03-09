package net.silveros.utility;

import net.silveros.entity.User;
import net.silveros.main.RivalsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

public class Util {
    private static JavaPlugin mainPlugin;
    public static Random rand = new Random();

    public static void initialize(JavaPlugin plugin) {
        mainPlugin = plugin;
    }

    public static void print(String s) {
        server().getConsoleSender().sendMessage("[Rivals Plugin] " + s);
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

    public static Server server() {
        return getPlugin().getServer();
    }

    public static World getWorld() {
        return server().getWorld(server().getWorldType());
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

    public static float getTickDelta() {
        return Bukkit.getServer().getServerTickManager().getTickRate();
    }

    /**
     * formula: start + (end - start) * percentage
     */
    public static float lerp_f(float percentage, float start, float end) {
        return start + (end - start) * percentage;
    }

    /**Can return null!*/
    public static Player getPlayerFromId(UUID uuid) {
        return server().getPlayer(uuid);
    }

    //credit: blog.jeff-media.com
    private static final UUID RANDOM_UUID = UUID.fromString("92864445-51c5-4c3b-9039-517c9927d1b4"); // We reuse the same "random" UUID all the time

    public static PlayerProfile getProfile(String url) {
        PlayerProfile profile = Bukkit.createPlayerProfile(RANDOM_UUID); // Get a new player profile
        PlayerTextures textures = profile.getTextures();
        URL urlObject;
        try {
            urlObject = new URL(url); // The URL to the skin, for example: https://textures.minecraft.net/texture/18813764b2abc94ec3c3bc67b9147c21be850cdf996679703157f4555997ea63a
        } catch (MalformedURLException exception) {
            throw new RuntimeException("Invalid URL", exception);
        }
        textures.setSkin(urlObject); // Set the skin of the player profile to the URL
        profile.setTextures(textures); // Set the textures back to the profile
        return profile;
    }

    public static double range(double radius) {
        return radius - ((rand.nextDouble() * radius) * 2);
    }
}
