package fr.lanfix.epicmanhunt.game;

import fr.lanfix.epicmanhunt.EpicManhunt;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EnderDragonChangePhaseEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ManhuntEventListener implements Listener {

    @EventHandler
    public void onEnderDragonChangePhase(EnderDragonChangePhaseEvent event) {
        if (event.getNewPhase().equals(EnderDragon.Phase.DYING)) {
            Bukkit.broadcastMessage("%s%sSpeedrunners have won, they defeated the ender dragon !".formatted(ChatColor.BOLD, ChatColor.GREEN));
            ManhuntGame.instance.stop();
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (ManhuntGame.instance.isRunning()) {
            Player player = event.getEntity();
            if (ManhuntGame.instance.getSpeedrunners().contains(player)) {
                player.setGameMode(GameMode.SPECTATOR);
                ManhuntGame.instance.getSpeedrunners().remove(player);
                ManhuntGame.instance.checkHuntersWin();
            } else if (ManhuntGame.instance.getHunters().contains(player)) {
                if (ManhuntGame.instance.isKeepInventoryForHunters()) {
                    event.setKeepInventory(true);
                    event.setKeepLevel(true);
                } else {
                    event.getDrops().removeIf(itemStack -> itemStack.getType().equals(Material.COMPASS));
                }
            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        if (ManhuntGame.instance.isRunning()) {
            Player player = event.getPlayer();
            if (ManhuntGame.instance.getHunters().contains(player) && !ManhuntGame.instance.isKeepInventoryForHunters()) {
                player.getInventory().addItem(CompassTracker.getCompass());
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if (ManhuntGame.instance.isRunning()) {
            Player player = event.getPlayer();
            boolean wasSpeedrunner = ManhuntGame.instance.getSpeedrunners().contains(player);
            if (wasSpeedrunner || ManhuntGame.instance.getHunters().contains(player)) {
                if (wasSpeedrunner) {
                    ManhuntGame.instance.getSpeedrunners().remove(player);
                } else {
                    ManhuntGame.instance.getHunters().remove(player);
                }
                Bukkit.broadcastMessage(player.getDisplayName() + " logged off, he has 5 minutes to get back on");
                new BukkitRunnable() {
                    int seconds = 0;
                    @Override
                    public void run() {
                        seconds++;
                        if (player.isOnline()) {
                            if (wasSpeedrunner) {
                                ManhuntGame.instance.getSpeedrunners().add(player);
                            } else {
                                ManhuntGame.instance.getHunters().add(player);
                            }
                            cancel();
                        } else if (seconds >= 300) {
                            Bukkit.broadcastMessage(player.getDisplayName() + " is out because he left for more than 5 minutes !");
                            ManhuntGame.instance.checkHuntersWin();
                            cancel();
                        }
                    }
                }.runTaskTimer(EpicManhunt.getInstance(), 20, 20);
            }
        }
    }

}
