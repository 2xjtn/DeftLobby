package org.jacob.spigot.plugins.minefred_lobby.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jacob.spigot.plugins.minefred_lobby.LobbyPlugin;

public class StaffGuiCommand implements CommandExecutor {

    FileConfiguration config = LobbyPlugin.getInstance().getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (label.equalsIgnoreCase("staffgui")) {

            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "You must be a player!");
                return true;
            }
            Player p = (Player) sender;

            if(args.length == 0) {
                p.openInventory(LobbyPlugin.getStaffInventory());
                return true;
            }

            if (!p.hasPermission("staffgui.edit")) {
                p.sendMessage(ChatColor.RED + "No permission");
                return true;
            }

            if (args.length < 4) {
                p.sendMessage(ChatColor.RED + "Usage: /staffgui <player:item> <item-name:player-name> <slot> <display-name>");
                return true;
            }

            if (args[0].equalsIgnoreCase("player")) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);

                if (target == null) {
                    p.sendMessage(ChatColor.RED + "Invalid player!");
                    return true;
                }

                int slot = 0;

                try {
                    slot = Integer.parseInt(args[2]);
                } catch (Exception e) {
                    p.sendMessage(ChatColor.RED + "Invalid slot!");
                    return true;
                }

                if(slot > 53) {
                    p.sendMessage(ChatColor.RED + "That number is too high! Max: 53");
                    return true;
                }

                StringBuilder name = new StringBuilder();

                for(int i = 3; i < args.length; i++) {
                    name.append(args[i] + " ");
                }

                ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());

                SkullMeta meta = (SkullMeta) head.getItemMeta();

                meta.setDisplayName(name.toString());

                String owner = target.getName();

                meta.setOwner(owner);
                head.setItemMeta(meta);

                Inventory inventory = LobbyPlugin.getStaffInventory();

                inventory.setItem(slot, head);

                LobbyPlugin.saveStaffInventory(inventory);

                p.sendMessage(ChatColor.GREEN + "Success!");

            } else if (args[0].equalsIgnoreCase("item")) {
                ItemStack stack = new ItemStack(Material.matchMaterial(args[1]));
                int slot = 0;
                try {
                    slot = Integer.parseInt(args[2]);

                } catch (Exception e) {
                    p.sendMessage(ChatColor.RED + "Invalid slot!");
                    return true;
                }

                StringBuilder name = new StringBuilder();

                for(int i = 3; i < args.length; i++) {
                    name.append(args[i]+ " ");
                }

                Inventory inventory = LobbyPlugin.getStaffInventory();

                ItemMeta meta = stack.getItemMeta();

                meta.setDisplayName(name.toString());

                stack.setItemMeta(meta);

                inventory.setItem(slot, stack);

                LobbyPlugin.saveStaffInventory(inventory);

                p.sendMessage(ChatColor.GREEN + "Success!");

            } else {
                p.sendMessage(ChatColor.RED + "Invalid argument!");
                return true;
            }
        }

        return true;
    }
}
