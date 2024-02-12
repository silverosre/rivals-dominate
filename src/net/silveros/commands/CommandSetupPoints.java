package net.silveros.commands;

import net.silveros.game.CapturePointLocations;
import net.silveros.game.Points;
import net.silveros.game.RivalsCore;
import net.silveros.game.RivalsMap;
import net.silveros.main.RivalsPlugin;
import net.silveros.utility.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandSetupPoints implements CommandExecutor, Color {
    private RivalsPlugin plugin;

    public CommandSetupPoints(RivalsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("setuppoints")) {
            if (args.length == 1) {
                String s = args[0];

                if (s.equals("reset")) {
                    if (RivalsCore.gameInProgress) {
                        RivalsPlugin.core.endDominateGame();

                        sender.sendMessage("Reset and removed all current capture points.");
                        return true;
                    } else {
                        err(sender, "There is no game ongoing, there are no points to remove!");
                        return true;
                    }
                } else if (s.equals("start")) {
                    if (!RivalsCore.gameInProgress) {
                        RivalsPlugin.core.startDominateGame(RivalsMap.TERRA);

                        sender.sendMessage("Set up 5 new capture points.");

                        //Map<Points, Marker> pointsMap = RivalsPlugin.core.capturePoints;

                        sender.sendMessage("Point A: " + CapturePointLocations.TERRA_pointLocations.get(Points.POINT_A).toString());
                        sender.sendMessage("Point B: " + CapturePointLocations.TERRA_pointLocations.get(Points.POINT_B).toString());
                        sender.sendMessage("Point C: " + CapturePointLocations.TERRA_pointLocations.get(Points.POINT_C).toString());
                        sender.sendMessage("Point D: " + CapturePointLocations.TERRA_pointLocations.get(Points.POINT_D).toString());
                        sender.sendMessage("Point E: " + CapturePointLocations.TERRA_pointLocations.get(Points.POINT_E).toString());
                        return true;
                    } else {
                        err(sender, "Game is already in progress!");
                        return true;
                    }
                } else {
                    err(sender, "Invalid usage.");
                    return true;
                }
            } else {
                err(sender, "Invalid usage.");
            }
        }

        return true;
    }

    private static void err(CommandSender sender, String s) {
        sender.sendMessage(RED + s);
    }
}
