package fr.lanfix.manhuntplus.menu;

import fr.lanfix.manhuntplus.game.ManhuntGame;
import fr.lanfix.manhuntplus.util.ItemStackUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public enum Menu implements MenuInterface {

    MAIN_MENU {
        final MenuItem[] menuItems = MenuItem.fillBackground(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 27);

        @Override
        public Inventory getInventory(InventoryHolder holder) {
            // Keep Inventory Button
            menuItems[10] = new MenuItem() {
                @Override
                public ItemStack getItem() {
                    return ItemStackUtils.itemStack(Material.CHEST, ChatColor.RESET + "Hunters Keep Inventory : " +
                            (ManhuntGame.instance.isKeepInventoryForHunters() ?
                                    ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled"));
                }

                @Override
                public void onClick(InventoryClickEvent event) {
                    boolean isKeepInv = !ManhuntGame.instance.isKeepInventoryForHunters();
                    ManhuntGame.instance.setKeepInventoryForHunters(isKeepInv);
                    ItemStack itemStack = event.getCurrentItem();
                    assert itemStack != null;
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    assert itemMeta != null;
                    itemMeta.setDisplayName(ChatColor.RESET + "Hunters Keep Inventory : " +
                            (isKeepInv ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled"));
                    itemStack.setItemMeta(itemMeta);
                }
            };
            // Run On New World Button
            menuItems[11] = new MenuItem() {
                @Override
                public ItemStack getItem() {
                    return ItemStackUtils.itemStack(Material.GRASS_BLOCK, ChatColor.RESET + "Run on new world : " +
                            (ManhuntGame.instance.isRunOnNewWorld() ?
                                    ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled"));
                }

                @Override
                public void onClick(InventoryClickEvent event) {
                    boolean isKeepInv = !ManhuntGame.instance.isRunOnNewWorld();
                    ManhuntGame.instance.setRunOnNewWorld(isKeepInv);
                    ItemStack itemStack = event.getCurrentItem();
                    assert itemStack != null;
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    assert itemMeta != null;
                    itemMeta.setDisplayName(ChatColor.RESET + "Run on new world : " +
                            (isKeepInv ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled"));
                    itemStack.setItemMeta(itemMeta);
                }
            };
            // Glowing Speedrunner Button
            menuItems[12] = new MenuItem() {
                @Override
                public ItemStack getItem() {
                    return ItemStackUtils.itemStack(Material.GLASS, ChatColor.RESET + "Glowing speedrunner : " +
                            (ManhuntGame.instance.isGlowingSpeedrunner() ?
                                    ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled"));
                }

                @Override
                public void onClick(InventoryClickEvent event) {
                    boolean isKeepInv = !ManhuntGame.instance.isGlowingSpeedrunner();
                    ManhuntGame.instance.setGlowingSpeedrunner(isKeepInv);
                    ItemStack itemStack = event.getCurrentItem();
                    assert itemStack != null;
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    assert itemMeta != null;
                    itemMeta.setDisplayName(ChatColor.RESET + "Glowing speedrunner : " +
                            (isKeepInv ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled"));
                    itemStack.setItemMeta(itemMeta);
                }
            };
            // TODO Speedrunners Button
            // menuItems[15]
            // TODO Hunters Button
            // menuItems[16]
            // Start or Stop Button
            if (!ManhuntGame.instance.isRunning()) {
                menuItems[22] = new MenuItem() {
                    @Override
                    public ItemStack getItem() {
                        return ItemStackUtils.itemStack(Material.GREEN_CONCRETE, ChatColor.GREEN + "Start");
                    }

                    @Override
                    public void onClick(InventoryClickEvent event) {
                        ManhuntGame.instance.start();
                        if (event.getWhoClicked() instanceof Player player) {
                            MenuManager.scheduleCloseMenu(player);
                        }

                    }
                };
            } else {
                menuItems[22] = new MenuItem() {
                    @Override
                    public ItemStack getItem() {
                        return ItemStackUtils.itemStack(Material.RED_CONCRETE, ChatColor.RED + "Stop");
                    }

                    @Override
                    public void onClick(InventoryClickEvent event) {
                        ManhuntGame.instance.stop();
                        if (event.getWhoClicked() instanceof Player player) {
                            MenuManager.scheduleCloseMenu(player);
                        }
                    }
                };
            }
            // Leave Menu
            // menuItems[26] = MenuItem.leaveMenu();
            Inventory inventory = Bukkit.createInventory(holder, menuItems.length, ChatColor.GREEN + "Manhunt Plus");
            inventory.setStorageContents(MenuItem.getItems(menuItems));
            return inventory;
        }

        @Override
        public MenuItem[] getItems() {
            return menuItems;
        }

        @Override
        public boolean canCloseNormally() {
            return true;
        }
    }

}
