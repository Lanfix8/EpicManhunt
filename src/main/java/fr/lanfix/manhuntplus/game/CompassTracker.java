package fr.lanfix.manhuntplus.game;

import fr.lanfix.manhuntplus.util.ItemStackUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class CompassTracker implements Listener {

    @EventHandler
    public static void onInteract(PlayerInteractEvent event) {
        if (ManhuntGame.instance.isRunning()) {
            Player player = event.getPlayer();
            if (ManhuntGame.instance.getHunters().contains(player)) {
                ItemStack item = event.getItem();
                if (item != null && item.getType().equals(Material.COMPASS)) {
                    switch (event.getAction()) {
                        case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> {
                            double nearestDistance = Double.POSITIVE_INFINITY;
                            Player nearestSpeedrunner = null;
                            for (Player speedrunner : ManhuntGame.instance.getSpeedrunners()) {
                                if (player.getWorld().equals(speedrunner.getWorld())) {
                                    double distance = player.getLocation().distance(speedrunner.getLocation());
                                    if (distance < nearestDistance) {
                                        nearestDistance = distance;
                                        nearestSpeedrunner = speedrunner;
                                    }
                                }
                            }
                            if (nearestSpeedrunner == null) {
                                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "No players to track"));
                            } else {
                                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "Compass tracking " + nearestSpeedrunner.getDisplayName()));
                                CompassMeta compassMeta = (CompassMeta) item.getItemMeta();
                                assert compassMeta != null;
                                compassMeta.setLodestone(nearestSpeedrunner.getLocation());
                                compassMeta.setLodestoneTracked(false);
                                item.setItemMeta(compassMeta);
                            }
                        }
                    }
                }
            }
        }
    }

    public static ItemStack getCompass() {
        return ItemStackUtils.itemStack(Material.COMPASS, ChatColor.RESET + "Speedrunner Tracker");
    }

}
