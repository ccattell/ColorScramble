package me.ccattell.plugins.colorscramble.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import static me.ccattell.plugins.colorscramble.ColorScramble.plugin;
import org.bukkit.ChatColor;

/**
 *
 * @author Charlie
 */
public class InitSQLite {

    Database service = Database.getInstance();
    public Connection connection = service.getConnection();
    public Statement statement = null;

    public void CSInitSQLite() {
    }

    public void initSQLite() {
        try {
            statement = connection.createStatement();
            String queryCS = "CREATE TABLE IF NOT EXISTS CSMain (arena_name TEXT PRIMARY KEY COLLATE NOCASE, pvp text NOT NULL COLLATE NOCASE)";
            statement.executeUpdate(queryCS);
        } catch (SQLException e) {
            plugin.console.sendMessage(plugin.pluginName + ChatColor.GOLD + "Could not create SQLite tables: " + e.getMessage() + ChatColor.RESET);
        }
    }
}
