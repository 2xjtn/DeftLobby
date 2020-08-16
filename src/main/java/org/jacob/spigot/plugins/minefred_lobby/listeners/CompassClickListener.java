package org.jacob.spigot.plugins.minefred_lobby.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jacob.spigot.plugins.minefred_lobby.commands.ServersCommand;

public class CompassClickListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            if(event.getPlayer().getItemInHand().getType().equals(Material.COMPASS)) {
                ServersCommand.openOtherInventory(event.getPlayer());
               event.setCancelled(true);
            }
        }
    }

}
