package org.jacob.spigot.plugins.deftlobby.listeners;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.jacob.spigot.plugins.deftlobby.LobbyPlugin;
import org.jacob.spigot.plugins.deftlobby.utils.Status;

public class PlayerCommandListener implements Listener {

    @EventHandler
    public boolean onCommand(PlayerCommandPreprocessEvent event) {

        Player sender = event.getPlayer();

        if(sender instanceof Player) {
            if(LobbyPlugin.unverified.get(sender.getUniqueId()) == Status.UNVERIFIED) {
                sender.sendMessage(ChatColor.RED + "You must enter your password first!");
                event.setCancelled(true);
                return false;
            }
        }

        return false;
    }

}
