package fr.lanfix.epicmanhunt.game;

import fr.lanfix.epicmanhunt.util.FileUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ManhuntGame {

    public static ManhuntGame instance = new ManhuntGame();

    private boolean running = false;

    private boolean keepInventoryForHunters = false;
    private boolean runOnNewWorld = true;
    private boolean glowingSpeedrunner = false;

    private final List<Player> speedrunners = new ArrayList<>();
    private final List<Player> hunters = new ArrayList<>();

    public void start() {
        Bukkit.getLogger().info("Starting Manhunt");
        World world;
        if (runOnNewWorld) {
            World oldWorld = Bukkit.getWorld("Manhunt_World");
            if (oldWorld != null) {
                oldWorld.getPlayers().forEach(player -> player.teleport(Objects.requireNonNull(Bukkit.getWorld("world")).getSpawnLocation()));
                Bukkit.unloadWorld(oldWorld, false);
                FileUtils.deleteDirectory(oldWorld.getWorldFolder());
            }
            world = Bukkit.createWorld(WorldCreator.name("Manhunt_World"));
        } else {
            world = Bukkit.getWorld("world");
        }
        assert world != null;
        world.setTime(0);
        Location spawnLocation = world.getSpawnLocation();
        for (Player speedrunner : speedrunners) {
            speedrunner.setHealth(20);
            speedrunner.setSaturation(20);
            speedrunner.setFoodLevel(20);
            speedrunner.setTotalExperience(0);
            speedrunner.setExp(0);
            speedrunner.sendExperienceChange(0, 0);
            speedrunner.setGameMode(GameMode.SURVIVAL);
            speedrunner.teleport(spawnLocation);
            speedrunner.getInventory().clear();
            speedrunner.getActivePotionEffects().forEach(effect -> speedrunner.removePotionEffect(effect.getType()));
            if (glowingSpeedrunner) {
                speedrunner.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, -1, 0, false, false, false));
            }
        }
        for (Player hunter : hunters) {
            hunter.setHealth(20);
            hunter.setSaturation(20);
            hunter.setFoodLevel(20);
            hunter.setTotalExperience(0);
            hunter.setExp(0);
            hunter.sendExperienceChange(0, 0);
            hunter.setGameMode(GameMode.SURVIVAL);
            hunter.teleport(spawnLocation);
            hunter.getActivePotionEffects().forEach(effect -> hunter.removePotionEffect(effect.getType()));
            hunter.getInventory().clear();
            hunter.getInventory().setItem(8, CompassTracker.getCompass());
        }
        running = true;
    }

    public void stop() {
        running = false;
    }

    public void checkHuntersWin() {
        if (this.running && this.getSpeedrunners().isEmpty()) {
            Bukkit.broadcastMessage("%s%sHunters have won, they killed all speedrunners !".formatted(ChatColor.BOLD, ChatColor.GREEN));
            this.stop();
        }
    }

    public boolean isRunning() {
        checkHuntersWin();
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
