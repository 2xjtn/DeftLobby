package org.jacob.spigot.plugins.deftlobby.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jacob.spigot.plugins.deftlobby.LobbyPlugin;
import org.jacob.spigot.plugins.deftlobby.utils.Status;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (LobbyPlugin.unverified.get(event.getPlayer().getUniqueId()) == Status.UNVERIFIED) {
            event.setCancelled(true);
        }
    }
}
