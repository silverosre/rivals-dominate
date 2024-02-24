package net.silveros.commands;

import net.silveros.game.RivalsCore;
import net.silveros.game.RivalsMap;
import net.silveros.main.RivalsPlugin;
import net.silveros.utility.Color;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandStart implements CommandExecutor, Color {
    private RivalsPlugin plugin;
    public CommandStart(RivalsPlugin plugin) {this.plugin = plugin;}

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("start")) {
            RivalsCore core = RivalsPlugin.core;
            if (args.length == 1) {
                String s = args[0];
                RivalsMap selectedMap = null;

                for (RivalsMap map : RivalsMap.values()) {
                    if (s.equalsIgnoreCase(map.displayName)) {
                        selectedMap = map;
                    }
                }

                if (selectedMap != null) {
                    if (!RivalsCore.gameInProgress) {
                        core.startDominateGame(selectedMap);
                        Bukkit.broadcastMessage(DARK_GREEN + BOLD + "Force Starting a new game on: " + WHITE + selectedMap);
                        return true;
                    } else {
                        err(sender, "Game is already in progress!");
                        return true;
                    }
                } else {
                    err(sender, "Invalid map name.");
                    return true;
                }
            }
            if (args.length == 0) {
                core.gameInit = true;
                return true;
            } else {
                err(sender, "Invalid usage.");
                return true;
            }
        }
        return true;
    }

    private static void err(CommandSender sender, String s) {
        sender.sendMessage(RED + s);
    }
}
