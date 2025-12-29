package fr.lanfix.epicmanhunt;

import fr.lanfix.epicmanhunt.game.CompassTracker;
import fr.lanfix.epicmanhunt.game.ManhuntEventListener;
import fr.lanfix.epicmanhunt.game.ManhuntGame;
import fr.lanfix.epicmanhunt.menu.MenuEventListener;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class EpicManhunt extends JavaPlugin {

    private static EpicManhunt instance;

    public static EpicManhunt getInstance() {
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
        getServer().getPluginManager().registerEvents(new CompassTracker(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        ManhuntGame.instance.stop();
    }

    // TODO Editable config for default game settings
    
}
