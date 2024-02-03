package net.silveros.commands;

import net.silveros.kits.Kit;
import net.silveros.main.MainPlugin;
import net.silveros.utility.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RDCommands implements CommandExecutor, Color {
    public MainPlugin plugin;

    public RDCommands(MainPlugin plugin) {
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
                for (Kit kit : Kit.KIT_LIST) {
                    if (kit.kitName.equalsIgnoreCase(args[0])) {
                        kit.activateKit(player.getInventory());
                        player.sendMessage("Successfully applied kit to player.");
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
