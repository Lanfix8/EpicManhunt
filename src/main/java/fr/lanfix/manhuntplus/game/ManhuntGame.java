package fr.lanfix.manhuntplus.game;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ManhuntGame {

    public static ManhuntGame instance = new ManhuntGame();

    private boolean running = false;

    private boolean keepInventoryForHunters = false;
    private boolean runOnNewWorld = true;
    private boolean glowingSpeedrunner = false;

    private List<Player> speedrunners = new ArrayList<>();
    private List<Player> hunters = new ArrayList<>();

    public void start() {
        World world;
        if (runOnNewWorld) {
            if (Bukkit.getWorld("Manhunt_World") != null) {

            }
        }
        running = true;
        // TODO Start Logic (Heal, Clear, Teleport...)
    }

    public void stop() {
        running = false;
    }

    public void checkHuntersWin() {
        if (this.getSpeedrunners().isEmpty()) {
            Bukkit.broadcastMessage("%s%sHunters have won, they killed all speedrunners !".formatted(ChatColor.BOLD, ChatColor.GREEN));
            this.stop();
        }
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isKeepInventoryForHunters() {
        return keepInventoryForHunters;
    }

    public void setKeepInventoryForHunters(boolean keepInventoryForHunters) {
        this.keepInventoryForHunters = keepInventoryForHunters;
    }

    public boolean isRunOnNewWorld() {
        return runOnNewWorld;
    }

    public void setRunOnNewWorld(boolean runOnNewWorld) {
        this.runOnNewWorld = runOnNewWorld;
    }

    public boolean isGlowingSpeedrunner() {
        return glowingSpeedrunner;
    }

    public void setGlowingSpeedrunner(boolean glowingSpeedrunner) {
        this.glowingSpeedrunner = glowingSpeedrunner;
    }

    public List<Player> getSpeedrunners() {
        return speedrunners;
    }

    public List<Player> getHunters() {
        return hunters;
    }
}
