package org.jacob.spigot.plugins.deftlobby;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jacob.spigot.plugins.deftlobby.commands.PasswordCommand;
import org.jacob.spigot.plugins.deftlobby.commands.ServersCommand;
import org.jacob.spigot.plugins.deftlobby.commands.StaffGuiCommand;
import org.jacob.spigot.plugins.deftlobby.listeners.*;
import org.jacob.spigot.plugins.deftlobby.utils.MysqlConnection;
import org.jacob.spigot.plugins.deftlobby.utils.Status;

import java.util.*;

public class LobbyPlugin extends JavaPlugin {

    private static LobbyPlugin instance;

    public static LobbyPlugin getInstance() {
        return instance;
    }

    public static Map<UUID, Status> unverified = new HashMap<>();

    FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        instance = this;

        getConfig().options().copyDefaults(true);
        saveConfig();

        if (!getConfig().contains("staff-inventory")) {
            Inventory inv = Bukkit.createInventory(null, 54, "Staff");

            saveStaffInventory(inv);
        }

        getCommand("servers").setExecutor(new ServersCommand());
        getCommand("staffgui").setExecutor(new StaffGuiCommand());
        getCommand("register").setExecutor(new PasswordCommand());

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new InventoryClickListener(), this);
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new CompassClickListener(), this);
        pm.registerEvents(new PlayerCommandListener(), this);
        pm.registerEvents(new PlayerMoveListener(), this);
        pm.registerEvents(new PlayerChatListener(), this);

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        MysqlConnection connection = new MysqlConnection();
        connection.init();

        new BukkitRunnable() {
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (!p.getInventory().contains(Material.COMPASS)) {
                        ItemStack compass = new ItemStack(Material.COMPASS);
                        ItemMeta meta = compass.getItemMeta();
                        meta.setDisplayName(ChatColor.GREEN + "Server Selector");
                        compass.setItemMeta(meta);

                        p.getInventory().setItem(1, compass);
                    }
                }
            }
        }.runTaskTimer(this, 20L, 20L);

    }

    public static void saveStaffInventory(Inventory inv) {

        getInstance().getConfig().set("staff-inventory", inv.getContents());
        getInstance().saveConfig();
    }

    public static Inventory getStaffInventory() {
        Inventory inventory = Bukkit.createInventory(null, 54, "Staff");
        Object inv = getInstance().getConfig().get("staff-inventory");
        if (inv == null) {
            return null;
        }
        ItemStack[] invstack = null;

        if (inv instanceof ItemStack[]) {
            invstack = (ItemStack[]) inv;
        } else if (inv instanceof List) {
            List invlist = (List) inv;
            invstack = (ItemStack[]) invlist.toArray(new ItemStack[0]);
        }

        for (ItemStack stack : invstack) {

            if (stack != null && stack.hasItemMeta()) {

                ItemMeta meta = stack.getItemMeta();
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', meta.getDisplayName()));
                stack.setItemMeta(meta);

            }
        }

        inventory.setContents(invstack);

        return inventory;
    }
}
