package org.jacob.spigot.plugins.deftlobby.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jacob.spigot.plugins.deftlobby.LobbyPlugin;
import org.jacob.spigot.plugins.deftlobby.utils.ItemStackBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServersCommand implements CommandExecutor {

    FileConfiguration config = LobbyPlugin.getInstance().getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(label.equalsIgnoreCase("servers")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "You must be a player to execute this command!");
                return true;
            }
            Player player = (Player) sender;

            if(args.length != 0) {
                player.sendMessage(ChatColor.RED + "No additional arguments required.");
                return true;
            }



           openInventory(player);
        }

        return true;
    }

    public static void openOtherInventory(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "Server Selector");

        inv.setItem(10, new ItemStackBuilder(Material.CHEST).name(ChatColor.RED + "" + ChatColor.BOLD + "Survival").lore(Arrays.asList(
                ChatColor.GRAY + "Go to " + ChatColor.GOLD + "Survival" + ChatColor.GRAY + "!"
        )).build());
        inv.setItem(13, new ItemStackBuilder(Material.DIAMOND_BLOCK).name(ChatColor.AQUA + "" + ChatColor.BOLD + "Creative").lore(Arrays.asList(
                ChatColor.GRAY + "Go to " + ChatColor.GOLD + "Creative" + ChatColor.GRAY + "!"
        )).build());
        inv.setItem(16, new ItemStackBuilder(Material.GRASS).name(ChatColor.GREEN + "" + ChatColor.BOLD + "Skyblock").lore(Arrays.asList(
                ChatColor.GRAY + "Go to " + ChatColor.GOLD + "Skyblock" + ChatColor.GRAY + "!"
        )).build());

        player.openInventory(inv);

    }

    public static void openInventory(Player player) {
        FileConfiguration config = LobbyPlugin.getInstance().getConfig();
        Inventory inv = Bukkit.createInventory(player, LobbyPlugin.getInstance().getConfig().getInt("menu-size"), ChatColor.translateAlternateColorCodes('&', LobbyPlugin.getInstance().getConfig().getString("menu-name")));

        for(String spot : config.getConfigurationSection("menu").getKeys(false)) {
            String materialName = config.getString("menu." + spot + ".material");
            String displayName = config.getString("menu." + spot + ".display-name");
            List<String> lore = config.getStringList("menu." + spot + ".lore");

            int place = Integer.parseInt(spot);

            Material material = Material.matchMaterial(materialName);

            List<String> finallore = new ArrayList<>();

            for(int i = 0; i < lore.size(); i++) {
                finallore.add(ChatColor.translateAlternateColorCodes('&', lore.get(i)));
            }

            inv.setItem(place, new ItemStackBuilder(material).name(
                    ChatColor.translateAlternateColorCodes('&', displayName)).lore(finallore).build());
        }

        player.openInventory(inv);
    }

}
