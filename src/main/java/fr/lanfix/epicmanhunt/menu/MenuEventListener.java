package fr.lanfix.epicmanhunt.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class MenuEventListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            if (MenuManager.hasOpenMenu(player)) {
                event.setCancelled(true);
                Menu menu = MenuManager.getCurrentMenu(player);
                menu.onClick(event);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player player) {
            if (MenuManager.hasOpenMenu(player)) {
                Menu menu = MenuManager.getCurrentMenu(player);
                if (menu.canCloseNormally()) {
                    MenuManager.remove(player);
                } else {
                    MenuManager.openMenu(player, menu);
                }
            }
        }
    }

}
