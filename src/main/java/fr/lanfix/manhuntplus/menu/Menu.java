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

import java.util.List;

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
            // Players Button
            menuItems[16] = MenuItem.openMenu(Material.PLAYER_HEAD, ChatColor.BLUE + "Configure players", List.of(
                    ChatColor.RESET + "Choose the " + ChatColor.DARK_PURPLE + "speedrunners" + ChatColor.RESET +
                            " and the " + ChatColor.RED + "hunters"
            ), Menu.PLAYERS_MENU);
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
    },

    PLAYERS_MENU {
        final MenuItem[] menuItems = MenuItem.fillBackground(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 54);

        @Override
        public Inventory getInventory(InventoryHolder holder) {
            // Speedrunners
            int i = 0;
            for (Player speedrunner : ManhuntGame.instance.getSpeedrunners()) {
                ItemStack itemStack = ItemStackUtils.playerHead(ChatColor.DARK_PURPLE + speedrunner.getName(),
                        speedrunner.getPlayerProfile(), List.of(
                                ChatColor.RESET + "Left Click to remove this player from the speedrunners",
                                ChatColor.RESET + "Right Click to move this player to the hunters"));
                menuItems[i] = new MenuItem() {
                    @Override
                    public ItemStack getItem() {
                        return itemStack;
                    }

                    @Override
                    public void onClick(InventoryClickEvent event) {
                        switch (event.getClick()) {
                            case LEFT, SHIFT_LEFT -> {
                                ManhuntGame.instance.getSpeedrunners().remove(speedrunner);
                                if (event.getWhoClicked() instanceof Player player) {
                                    MenuManager.scheduleOpenMenu(player, Menu.PLAYERS_MENU);
                                }
                            }
                            case RIGHT, SHIFT_RIGHT -> {
                                ManhuntGame.instance.getSpeedrunners().remove(speedrunner);
                                ManhuntGame.instance.getHunters().add(speedrunner);
                                if (event.getWhoClicked() instanceof Player player) {
                                    MenuManager.scheduleOpenMenu(player, Menu.PLAYERS_MENU);
                                }
                            }
                        }
                    }
                };
                i++;
            }
            do {
                menuItems[i] = MenuItem.fromMaterial(Material.PURPLE_STAINED_GLASS_PANE);
                i++;
            } while (i % 9 != 0);
            // Hunters
            for (Player hunter : ManhuntGame.instance.getHunters()) {
                ItemStack itemStack = ItemStackUtils.playerHead(ChatColor.RED + hunter.getName(),
                        hunter.getPlayerProfile(), List.of(
                                ChatColor.RESET + "Left Click to remove this player from the hunters",
                                ChatColor.RESET + "Right Click to move this player to the speedrunners"));
                menuItems[i] = new MenuItem() {
                    @Override
                    public ItemStack getItem() {
                        return itemStack;
                    }

                    @Override
                    public void onClick(InventoryClickEvent event) {
                        switch (event.getClick()) {
                            case LEFT, SHIFT_LEFT -> {
                                ManhuntGame.instance.getHunters().remove(hunter);
                                if (event.getWhoClicked() instanceof Player player) {
                                    MenuManager.scheduleOpenMenu(player, Menu.PLAYERS_MENU);
                                }
                            }
                            case RIGHT, SHIFT_RIGHT -> {
                                ManhuntGame.instance.getHunters().remove(hunter);
                                ManhuntGame.instance.getSpeedrunners().add(hunter);
                                if (event.getWhoClicked() instanceof Player player) {
                                    MenuManager.scheduleOpenMenu(player, Menu.PLAYERS_MENU);
                                }
                            }
                        }
                    }
                };
                i++;
            }
            do {
                menuItems[i] = MenuItem.fromMaterial(Material.RED_STAINED_GLASS_PANE);
                i++;
            } while (i % 9 != 0);
            // Other Players
            for (Player player :Bukkit.getOnlinePlayers()) {
                if (!ManhuntGame.instance.getHunters().contains(player) && !ManhuntGame.instance.getSpeedrunners().contains(player)) {
                    ItemStack itemStack = ItemStackUtils.playerHead(ChatColor.BLUE + player.getName(),
                            player.getPlayerProfile(), List.of(
                                    ChatColor.RESET + "Left Click to add this player to the speedrunners",
                                    ChatColor.RESET + "Right Click to add this player to the hunters"));
                    menuItems[i] = new MenuItem() {
                        @Override
                        public ItemStack getItem() {
                            return itemStack;
                        }

                        @Override
                        public void onClick(InventoryClickEvent event) {
                            switch (event.getClick()) {
                                case LEFT, SHIFT_LEFT -> {
                                    ManhuntGame.instance.getSpeedrunners().add(player);
                                    if (event.getWhoClicked() instanceof Player whoClicked) {
                                        MenuManager.scheduleOpenMenu(whoClicked, Menu.PLAYERS_MENU);
                                    }
                                }
                                case RIGHT, SHIFT_RIGHT -> {
                                    ManhuntGame.instance.getHunters().add(player);
                                    if (event.getWhoClicked() instanceof Player whoClicked) {
                                        MenuManager.scheduleOpenMenu(whoClicked, Menu.PLAYERS_MENU);
                                    }
                                }
                            }
                        }
                    };
                    i++;
                }
            }
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
