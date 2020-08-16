package org.jacob.spigot.plugins.deftlobby;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Scoreboards {

    public static void sendScoreboardToAll() {

        new BukkitRunnable() {
            public void run() {
                for(Player p : Bukkit.getOnlinePlayers()) {
                    freshScoreboard(p);
                }
            }
        }.runTaskTimer(LobbyPlugin.getInstance(), 60L, 60L);
    }

    private static String playerCount(int port) {

        int online = 0;
        int max = 0;

        try {
            Socket sock = new Socket("96.48.120.9", port);

            DataOutputStream out = new DataOutputStream(sock.getOutputStream());
            DataInputStream in = new DataInputStream(sock.getInputStream());
            out.write(0xFE);

            StringBuffer str = new StringBuffer();

            int b;
            while ((b = in.read()) != -1) {
                if (b != 0 && b > 16 && b != 255 && b != 23 && b != 24) {
                    str.append((char) b);
                }
            }

            String[] data = str.toString().split("§");
            online = Integer.parseInt(data[1]);
            max = Integer.parseInt(data[2]);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return online + "/" + max;
    }

    private static boolean serverOnline(int port) {
        try {
            Socket sock = new Socket("96.48.120.9", port);

            DataOutputStream out = new DataOutputStream(sock.getOutputStream());
            DataInputStream in = new DataInputStream(sock.getInputStream());
            out.write(0xFE);

            StringBuffer str = new StringBuffer();

            int b;
            while ((b = in.read()) != -1) {
                if (b != 0 && b > 16 && b != 255 && b != 23 && b != 24) {
                    str.append((char) b);
                }
            }

            String[] data = str.toString().split("§");
            int placeholder = Integer.parseInt(data[1]);
            int placeholderdata = Integer.parseInt(data[2]);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static void freshScoreboard(Player p) {

        p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Objective objective = scoreboard.registerNewObjective("servers", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "DeftCraft");
        Score score = objective.getScore(ChatColor.YELLOW + "Servers");
        score.setScore(100);

        Score lobby = objective.getScore((serverOnline(25566) ? ChatColor.GREEN + "":ChatColor.RED + "") + "Lobby "
                + ChatColor.WHITE + playerCount(25566));
        lobby.setScore(99);

        Score survival = objective.getScore((serverOnline(25567) ? ChatColor.GREEN + "":ChatColor.RED + "") + "Survival "
                + ChatColor.WHITE + playerCount(25567));
        survival.setScore(98);

        Score creative = objective.getScore((serverOnline(25568) ? ChatColor.GREEN + "":ChatColor.RED + "") + "Creative "
                + ChatColor.WHITE + playerCount(25568));
        creative.setScore(97);

        Score skyblock = objective.getScore((serverOnline(25569) ? ChatColor.GREEN + "":ChatColor.RED + "") + "Skyblock "
                + ChatColor.WHITE + playerCount(25569
        ));
        skyblock.setScore(96);



    }
}
