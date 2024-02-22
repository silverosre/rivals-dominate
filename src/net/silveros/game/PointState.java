package net.silveros.game;

import org.bukkit.ChatColor;

public enum PointState {
    UNDEFINED(ChatColor.WHITE),
    RED(ChatColor.RED),
    BLUE(ChatColor.BLUE);

    public ChatColor color;

    PointState(ChatColor c) {
        this.color = c;
    }
}
