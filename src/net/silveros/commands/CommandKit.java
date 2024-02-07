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
        if (!(sender instanceof Player)) {
            err(sender, "Only players may use this command.");
            return true;
        }

        Player player = (Player)sender;

        if (cmd.getName().equalsIgnoreCase("kit")) {
            if (args.length == 1) {
                for (Kit kit : Kit.KIT_LIST.values()) {
                    if (kit.kitName.equalsIgnoreCase(args[0])) {
                        User user = Util.getUserFromId(player.getUniqueId());
                        if (user != null) {
                            user.setKit(kit);
                        }

                        player.sendMessage("Successfully applied kit to player.");
                        return true;
                    }
                }

                if ("reset".equalsIgnoreCase(args[0])) {
                    User user = Util.getUserFromId(player.getUniqueId());
                    if (user != null) {
                        user.resetKit();
                        player.sendMessage("Reset player's chosen kit.");
                        return true;
                    }
                }

                err(player, "Invalid kit.");
            } else {
                err(player, "Invalid usage.");
            }
        }

        return true;
    }

    private static void err(CommandSender sender, String s) {
        sender.sendMessage(RED + s);
    }
}
