package me.ccattell.plugins.colorscramble.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import static me.ccattell.plugins.colorscramble.ColorScramble.plugin;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

/**
 *
 * @author Charlie
 */
public class MainResultSet {

    public ConsoleCommandSender console;
    private Database service = Database.getInstance();
    private Connection connection = service.getConnection();
    private HashMap<String, Object> where;
    private String arena_name;

    public MainResultSet(HashMap<String, Object> where) {
        this.where = where;
    }

    public boolean resultSet() {
        PreparedStatement statement = null;
        ResultSet rs = null;
        String wheres = "";
        if (where != null) {
            StringBuilder sbw = new StringBuilder();
            for (Map.Entry<String, Object> entry : where.entrySet()) {
                sbw.append(entry.getKey()).append(" = ? AND ");
            }
            wheres = " WHERE " + sbw.toString().substring(0, sbw.length() - 5);
        }
        String query = "SELECT * FROM CSMain" + wheres;
        try {
            statement = connection.prepareStatement(query);
            if (where != null) {
                int i = 1;
                for (Map.Entry<String, Object> entry : where.entrySet()) {
                    if (entry.getValue().getClass().equals(String.class)) {
                        statement.setString(i, entry.getValue().toString());
                    } else {
                        if (entry.getValue().getClass().getName().contains("Float")) {
                            statement.setDouble(i, Float.parseFloat(entry.getValue().toString()));
                        } else if (entry.getValue().getClass().getName().contains("Long")) {
                            statement.setLong(i, Long.parseLong(entry.getValue().toString()));
                        } else {
                            statement.setInt(i, Integer.parseInt(entry.getValue().toString()));
                        }
                    }
                    i++;
                }
                where.clear();
            }
            rs = statement.executeQuery();
            if (rs.isBeforeFirst()) {
                this.arena_name = rs.getString("arena_name");
            } else {
                return false;
            }
        } catch (SQLException e) {
            console.sendMessage(plugin.pluginName + ChatColor.GOLD + "ResultSet error for CSMain table! " + e.getMessage() + ChatColor.RESET);
            return false;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (Exception e) {
                console.sendMessage(plugin.pluginName + ChatColor.GOLD + "Error closing CSMain table! " + e.getMessage() + ChatColor.RESET);
            }
        }
        return true;
    }

    public String getArena_name() {
        return arena_name;
    }
}
