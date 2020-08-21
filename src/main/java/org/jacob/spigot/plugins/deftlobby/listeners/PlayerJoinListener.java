package org.jacob.spigot.plugins.deftlobby.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jacob.spigot.plugins.deftlobby.LobbyPlugin;
import org.jacob.spigot.plugins.deftlobby.utils.Database;
import org.jacob.spigot.plugins.deftlobby.utils.Status;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) throws InterruptedException {
        Player p = event.getPlayer();
        p.teleport(p.getWorld().getSpawnLocation());

        if(p.getInventory().contains(Material.COMPASS)) {
            p.getInventory().remove(Material.COMPASS);
        }

        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta meta = compass.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Server Selector");
        compass.setItemMeta(meta);

        p.getInventory().setItem(1, compass);

        if(p.hasPermission("deftcraft.requirepassword")) {

            System.out.println(Database.playerExists(p.getUniqueId()));

            if(!Database.playerExists(p.getUniqueId())) {
                p.sendMessage(ChatColor.YELLOW + "You must create a password! to do this, type /register <password>");
                LobbyPlugin.unverified.put(p.getUniqueId(), Status.SETTING);
            } else {
                p.sendMessage(ChatColor.YELLOW + "Enter your password");
                LobbyPlugin.unverified.put(p.getUniqueId(), Status.UNVERIFIED);

            }
        }
    }
}
