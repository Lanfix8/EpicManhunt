package fr.lanfix.manhuntplus;

import fr.lanfix.manhuntplus.menu.Menu;
import fr.lanfix.manhuntplus.menu.MenuManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ManhuntCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if (sender instanceof Player player) {
            MenuManager.openMenu(player, Menu.MAIN_MENU);
        } else {
            sender.sendMessage(ChatColor.RED + "This command opens a menu, please parse it as a player !");
        }
        return true;
    }

}
