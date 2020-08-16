package org.jacob.spigot.plugins.deftlobby.listeners;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jacob.spigot.plugins.deftlobby.LobbyPlugin;

public class InventoryClickListener implements Listener {

    FileConfiguration config = LobbyPlugin.getInstance().getConfig();

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        int slot = event.getSlot();

       /* String menuName;

        try {
            menuName = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("menu-name")));
        } catch (NullPointerException ex) {
            return;
        }

        if (event.getView().getTitle().equalsIgnoreCase(menuName)) {
            for (String slotString : config.getConfigurationSection("menu").getKeys(false)) {
                if ((slot == Integer.parseInt(slotString))) {
                    String displayName = config.getString("menu." + slotString + ".display-name");
                    String serverName = config.getString("menu." + slotString + ".server-name");

                    Player pl = (Player) event.getWhoClicked();

                    pl.sendMessage(ChatColor.translateAlternateColorCodes('&', LobbyPlugin.getInstance().getConfig().getString("message")
                            .replaceAll("<name>", displayName)));

                    ByteArrayDataOutput out = ByteStreams.newDataOutput();
                    out.writeUTF("Connect");
                    out.writeUTF(serverName);
                    pl.sendPluginMessage(LobbyPlugin.getInstance(), "BungeeCord", out.toByteArray());

                }
            }
            event.setCancelled(true);
        }

        */


        if(event.getView().getTitle().equalsIgnoreCase("Server Selector")) {
            switch(event.getSlot()) {
                case 10:
                    sendToServer((Player)event.getWhoClicked(), "survival");
                    break;
                case 13:
                    sendToServer((Player)event.getWhoClicked(), "creative");
                    break;
                case 16:
                    sendToServer((Player)event.getWhoClicked(), "skyblock");
                    break;
            }
            event.setCancelled(true);
        }

        if(event.getView().getTitle().equalsIgnoreCase("Staff")) {
            event.setCancelled(true);
        }
    }
    private static void sendToServer(Player player, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        player.sendPluginMessage(LobbyPlugin.getInstance(), "BungeeCord", out.toByteArray());
        player.sendMessage(ChatColor.GRAY + "Sending you to " + ChatColor.YELLOW + server);
    }

}
