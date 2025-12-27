package fr.lanfix.epicmanhunt.menu;

import fr.lanfix.epicmanhunt.util.ItemStackUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface MenuItem {

    ItemStack getItem();
    default void onClick(InventoryClickEvent event) {}

    static MenuItem fromMaterial(Material material) {
        return fromStack(new ItemStack(material));
    }

    static MenuItem fromStack(ItemStack itemStack) {
        return new MenuItem() {
            @Override
            public ItemStack getItem() {
                return itemStack;
            }
        };
    }

    static MenuItem openMenu(Material material, String name, List<String> lore, Menu menu) {
        return openMenu(ItemStackUtils.itemStack(material, name, lore), menu);
    }

    static MenuItem openMenu(ItemStack itemStack, Menu menu) {
        return new MenuItem() {
            @Override
            public ItemStack getItem() {
                return itemStack;
            }

            @Override
            public void onClick(InventoryClickEvent event) {
                if (event.getWhoClicked() instanceof Player player) {
                    MenuManager.scheduleOpenMenu(player, menu);
                }
            }
        };
    }

    static MenuItem leaveMenu() {
        return new MenuItem() {
            @Override
            public ItemStack getItem() {
                return new ItemStack(Material.BARRIER);
            }

            @Override
            public void onClick(InventoryClickEvent event) {
                if (event.getWhoClicked() instanceof Player player) {
                    MenuManager.scheduleCloseMenu(player);
                }
            }
        };
    }

    static MenuItem[] fillBackground(Material material, int size) {
        MenuItem[] menuItems = new MenuItem[size];
        for (int i = 0; i < size; i++) menuItems[i] = fromStack(ItemStackUtils.itemStack(material, " "));
        return menuItems;
    }

    static ItemStack[] getItems(MenuItem[] menuItems) {
        ItemStack[] itemStacks = new ItemStack[menuItems.length];
        for (int i = 0; i < menuItems.length; i++) itemStacks[i] = menuItems[i].getItem();
        return itemStacks;
    }

}
