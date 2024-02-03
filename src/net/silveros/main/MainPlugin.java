package net.silveros.main;

import net.silveros.commands.RDCommands;
import net.silveros.events.AbilityEvents;
import net.silveros.kits.ItemRegistry;
import net.silveros.kits.Kit;
import net.silveros.utility.Color;
import net.silveros.utility.Util;
import org.bukkit.plugin.java.JavaPlugin;

public class MainPlugin extends JavaPlugin implements Color {
    @Override
    public void onEnable() {
        Util.initialize(this);
        Util.print(GREEN + "Enabling test plugin...");

        //item and kit initialization
        ItemRegistry.init();
        Kit.init();

        //register events & commands
        Util.registerEvent(new AbilityEvents());

        Util.registerCommand("kit", new RDCommands(this));
        //this.getCommand("kit").setExecutor(new RDCommands(this));
    }

    @Override
    public void onDisable() {
        Util.print(RED + "Disabling test plugin...");
    }
}
