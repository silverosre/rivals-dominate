package net.silveros.commands;

import net.silveros.entity.User;
import net.silveros.kits.Kit;
import net.silveros.main.RivalsPlugin;
import net.silveros.utility.Color;
import net.silveros.utility.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class CommandKitP implements CommandExecutor, Color {
    private RivalsPlugin plugin;

    public CommandKitP(RivalsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("kitp")) {
            if (args.length == 2) {
                for (Entity e : Bukkit.selectEntities(sender, args[0])) {
                    if (e instanceof Player) {
                        Player player = (Player)e;

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
                        err(sender, "No entities found using selector: " + args[0]);
                    }
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
