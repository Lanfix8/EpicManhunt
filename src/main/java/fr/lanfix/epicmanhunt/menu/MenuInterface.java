package fr.lanfix.epicmanhunt.menu;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.PlayerInventory;

public interface MenuInterface {

    Inventory getInventory(InventoryHolder holder);

    MenuItem[] getItems();

    boolean canCloseNormally();

    default void onClick(InventoryClickEvent event) {
        if (!(event.getClickedInventory() instanceof PlayerInventory)) {
            int slot = event.getSlot();
            getItems()[slot].onClick(event);
        }
    }

}
