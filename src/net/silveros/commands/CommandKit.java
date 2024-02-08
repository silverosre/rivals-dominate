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

public class CommandKit implements CommandExecutor, Color {
    private RivalsPlugin plugin;

    public CommandKit(RivalsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("kit")) {
            if (args.length == 2) {
                Player player = this.plugin.getServer().getPlayer(args[0]);

                if (player != null) {
                    for (Kit kit : Kit.KIT_LIST.values()) {
                        if (kit.kitName.equalsIgnoreCase(args[1])) {
                            User user = Util.getUserFromId(player.getUniqueId());
                            if (user != null) {
                                user.setKit(kit);
                            }

                            sender.sendMessage("Successfully applied kit to player.");
                            return true;
                        }
                    }

                    if ("reset".equalsIgnoreCase(args[1])) {
                        User user = Util.getUserFromId(player.getUniqueId());
                        if (user != null) {
                            user.resetKit();
                            sender.sendMessage("Reset player's chosen kit.");
                            return true;
                        }
                    }

                    err(sender, "Invalid kit.");
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
