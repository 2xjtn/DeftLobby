package org.jacob.spigot.plugins.deftlobby.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jacob.spigot.plugins.deftlobby.LobbyPlugin;
import org.jacob.spigot.plugins.deftlobby.utils.Database;
import org.jacob.spigot.plugins.deftlobby.utils.Status;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        if(LobbyPlugin.unverified.get(event.getPlayer().getUniqueId()) == Status.SETTING) {

           /* Database.setPassword(event.getPlayer().getUniqueId(), event.getMessage());

            event.getPlayer().sendMessage(ChatColor.GREEN + "Password set!");

            LobbyPlugin.unverified.put(event.getPlayer().getUniqueId(), Status.VERIFIED);
            event.setCancelled(true);

            return;
            */
        }

        if(LobbyPlugin.unverified.get(event.getPlayer().getUniqueId()) == Status.UNVERIFIED) {

            if(Database.correctPassword(event.getPlayer(), event.getMessage())) {
                event.getPlayer().sendMessage(ChatColor.GREEN + "Success");
                LobbyPlugin.unverified.put(event.getPlayer().getUniqueId(), Status.VERIFIED);
                event.setCancelled(true);
            } else {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.RED + "Incorrect password");
            }
        }
    }
}
