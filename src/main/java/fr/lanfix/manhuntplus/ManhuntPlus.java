package fr.lanfix.manhuntplus;

import fr.lanfix.manhuntplus.game.ManhuntEventListener;
import fr.lanfix.manhuntplus.menu.MenuEventListener;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class ManhuntPlus extends JavaPlugin {

    private static ManhuntPlus instance;

    public static ManhuntPlus getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        // Manhunt Command
        PluginCommand manhuntCommand = getCommand("manhunt");
        assert manhuntCommand != null;
        manhuntCommand.setExecutor(new ManhuntCommand());
        // Event Listeners
        getServer().getPluginManager().registerEvents(new MenuEventListener(), this);
        getServer().getPluginManager().registerEvents(new ManhuntEventListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    
}
