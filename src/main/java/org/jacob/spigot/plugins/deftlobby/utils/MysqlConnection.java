package org.jacob.spigot.plugins.deftlobby.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.jacob.spigot.plugins.deftlobby.LobbyPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnection {

    static FileConfiguration config = LobbyPlugin.getInstance().getConfig();

    private static Connection connection;
    public static String host = config.getString("mysql.host");
    public static String database = config.getString("mysql.database");
    public static String username = config.getString("mysql.username");
    public static String password = config.getString("mysql.password");
    public static String table = config.getString("mysql.table");
    public static int port = config.getInt("mysql.port");

    public void init() {
        try {
            synchronized (this){
                if(getConnection() != null && !getConnection().isClosed()) {
                    return;
                }
                Class.forName("com.mysql.jdbc.Driver");
                setConnection(DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/"
                        + database + "?characterEncoding=utf8", username, password));
                System.out.println(ChatColor.GREEN + "Successfully connected to the database.");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection1) {
        connection = connection1;
    }

}
