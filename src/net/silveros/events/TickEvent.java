package net.silveros.events;

import net.silveros.main.MainPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class TickEvent implements Listener {
    private final MainPlugin plugin;

    public TickEvent(MainPlugin pl) {
        this.plugin = pl;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

    }
}
