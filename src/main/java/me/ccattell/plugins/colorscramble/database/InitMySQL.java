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
public class InitMySQL {

    Database service = Database.getInstance();
    public Connection connection = service.getConnection();
    public Statement statement = null;

    public void CSInitMySQL() {
    }

    public void initMYSQL() {
        try {
            statement = connection.createStatement();
            String queryCS = "CREATE TABLE IF NOT EXISTS CSMain (arena_name text NOT NULL, pvp text NOT NULL) CHARACTER SET utf8 COLLATE utf8_general_ci";
            statement.executeUpdate(queryCS);
        } catch (SQLException e) {
            plugin.console.sendMessage(plugin.pluginName + ChatColor.GOLD + "Could not create MySQL tables: " + e.getMessage() + ChatColor.RESET);
        }
    }
}
