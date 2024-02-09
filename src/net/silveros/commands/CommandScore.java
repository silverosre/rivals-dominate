package net.silveros.commands;

import net.silveros.entity.User;
import net.silveros.main.RivalsPlugin;
import net.silveros.utility.Color;
import net.silveros.utility.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class CommandScore implements CommandExecutor, Color {
    private RivalsPlugin plugin;

    public CommandScore(RivalsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("score")) {
            if (args.length == 2) {
                String s = args[0];

                if (!("red".equals(s) || "blue".equals(s))) {
                    err(sender, "Invalid team!");
                    return true;
                }

                Team team = "red".equals(s) ? RivalsPlugin.core.TEAM_RED : RivalsPlugin.core.TEAM_BLUE;

                double score;
                try {
                    score = Double.parseDouble(args[1]);
                } catch (NumberFormatException e) {
                    err(sender, "Invalid score!  Score must be a value between 0.0 and 1.0");
                    return true;
                }

                if (score < 0 || score > 1) {
                    err(sender, "Invalid score!  Score must be a value between 0.0 and 1.0");
                    return true;
                }

                RivalsPlugin.core.getTeamProgressBar(team).setProgress(score);
                sender.sendMessage("Successfully set " + s + " team's score to " + score);
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
