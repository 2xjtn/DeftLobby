package org.jacob.spigot.plugins.deftlobby.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
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
    }
}
