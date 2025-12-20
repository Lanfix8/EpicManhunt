package fr.lanfix.manhuntplus.menu;

import fr.lanfix.manhuntplus.ManhuntPlus;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class MenuManager {

    private static final HashMap<UUID, Menu> currentMenus = new HashMap<>();

    public static void scheduleOpenMenu(Player player, Menu menu) {
        new BukkitRunnable() {
            @Override
            public void run() {
                openMenu(player, menu);
            }
        }.runTask(ManhuntPlus.getInstance());
    }

    public static void openMenu(Player player, Menu menu) {
        Inventory inventory = menu.getInventory(player);
        player.openInventory(inventory);
        currentMenus.put(player.getUniqueId(), menu);
    }

    public static boolean hasOpenMenu(Player player) {
        return currentMenus.containsKey(player.getUniqueId());
    }

    public static Menu getCurrentMenu(Player player) {
        return currentMenus.getOrDefault(player.getUniqueId(), null);
    }

    public static void scheduleCloseMenu(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                closeMenu(player);
            }
        }.runTask(ManhuntPlus.getInstance());
    }

    public static void closeMenu(Player player) {
        player.closeInventory();
        currentMenus.remove(player.getUniqueId());
    }

    public static void remove(Player player) {
        currentMenus.remove(player.getUniqueId());
    }

}
