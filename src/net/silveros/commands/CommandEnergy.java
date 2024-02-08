package net.silveros.commands;

import net.silveros.entity.User;
import net.silveros.main.RivalsPlugin;
import net.silveros.utility.Color;
import net.silveros.utility.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandEnergy implements CommandExecutor, Color {
    private RivalsPlugin plugin;

    public CommandEnergy(RivalsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("energy")) {
            if (args.length == 2) {
                Player player = this.plugin.getServer().getPlayer(args[0]);

                if (player != null) {
                    User user = Util.getUserFromId(player.getUniqueId());

                    int energy;
                    try {
                        energy = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        err(sender, "Invalid energy count!  Energy must be a whole number.");
                        return true;
                    }

                    user.setTotalEnergy(energy);
                    sender.sendMessage("Successfully set player's energy to " + energy);
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
