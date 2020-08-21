package org.jacob.spigot.plugins.deftlobby.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jacob.spigot.plugins.deftlobby.LobbyPlugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Database {

    public static boolean correctPassword(Player player, String password) {

        UUID uuid = player.getUniqueId();

        try {
            PreparedStatement statement = MysqlConnection.getConnection().prepareStatement("SELECT uuid, password FROM player_data");

            ResultSet results = statement.executeQuery();

            if(results.next()) {
                if(results.getString("uuid").equals(uuid.toString())) {

                    if(results.getString("password").equals(password)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return true;
    }

    public static boolean playerExists(UUID uuid) {
        try {
            PreparedStatement statement = MysqlConnection.getConnection().prepareStatement("SELECT * FROM player_data WHERE uuid=?");
            statement.setString(1, uuid.toString());

            ResultSet results = statement.executeQuery();

            if (results.next()) {
                return true;
            }
            return false;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static void setPassword(final UUID uuid, String password) {
        try{

            PreparedStatement statement = MysqlConnection.getConnection().prepareStatement("SELECT * FROM "
                    + MysqlConnection.table + " WHERE uuid=?");

            statement.setString(1, uuid.toString());

            ResultSet results = statement.executeQuery();

            results.next();

            if(!(playerExists(uuid))) {
                PreparedStatement insert = MysqlConnection.getConnection().prepareStatement("INSERT INTO "
                        + MysqlConnection.table + " (uuid,password) VALUES (?,?)");
                insert.setString(1, uuid.toString());
                insert.setString(2, password);
                insert.executeUpdate();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
