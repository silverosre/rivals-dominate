package net.silveros.commands;

import net.silveros.entity.User;
import net.silveros.kits.Kit;
import net.silveros.main.RivalsPlugin;
import net.silveros.utility.Color;
import net.silveros.utility.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRTeam implements CommandExecutor, Color {
    private RivalsPlugin plugin;

    public CommandRTeam(RivalsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("rteam")) {
            if (args.length == 2) {
                Player player = this.plugin.getServer().getPlayer(args[0]);

                if (player != null) {
                    User user = Util.getUserFromId(player.getUniqueId());

                    if (user != null) {
                        String team = args[1].toLowerCase();

                        switch (team) {
                            case "red":
                                user.setTeam(RivalsPlugin.core.TEAM_RED);
                                sender.sendMessage("Set player's team to " + RivalsPlugin.core.TEAM_RED.getDisplayName());
                                return true;
                            case "blue":
                                user.setTeam(RivalsPlugin.core.TEAM_BLUE);
                                sender.sendMessage("Set player's team to " + RivalsPlugin.core.TEAM_BLUE.getDisplayName());
                                return true;
                            case "spectator":
                                user.setTeam(RivalsPlugin.core.TEAM_SPECTATOR);
                                sender.sendMessage("Set player's team to " + RivalsPlugin.core.TEAM_SPECTATOR.getDisplayName());
                                return true;
                            case "reset":
                                user.resetTeam();
                                sender.sendMessage("Reset player's chosen team.");
                                return true;
                        }
                    }

                    err(sender, "Invalid team.");
                } else {
                    err(sender, "Invalid player!  Make sure you typed the correct username.");
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
