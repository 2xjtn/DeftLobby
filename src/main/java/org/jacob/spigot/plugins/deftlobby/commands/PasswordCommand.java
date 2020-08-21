package org.jacob.spigot.plugins.deftlobby.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jacob.spigot.plugins.deftlobby.utils.Database;
import org.jacob.spigot.plugins.deftlobby.utils.MysqlConnection;

public class PasswordCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(label.equalsIgnoreCase("register")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "You must be a player!");
                return true;
            }
            Player p = (Player) sender;

            if(!p.hasPermission("deftcraft.requirepassword")) {
                p.sendMessage(ChatColor.RED + "No permission!");
                return true;
            }

            if(args.length != 1) {
                p.sendMessage(ChatColor.RED + "Usage: /register <password>");
                return true;
            }

            String password = args[0];

            if(Database.playerExists(p.getUniqueId())) {
                p.sendMessage(ChatColor.RED + "You've already registered. to change your password, please contact 2xjtn.");
                return true;
            }

            Database.setPassword(p.getUniqueId(), password);
            p.kickPlayer(ChatColor.GREEN + "Password set!");

        }

        return true;
    }
}
