package net.silveros.events;

import net.silveros.main.RivalsPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TickEvent implements Listener {
    private final RivalsPlugin plugin;

    public TickEvent(RivalsPlugin pl) {
        this.plugin = pl;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

    }
}
