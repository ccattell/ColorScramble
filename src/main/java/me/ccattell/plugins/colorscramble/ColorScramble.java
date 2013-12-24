package me.ccattell.plugins.colorscramble;

import java.io.File;
import java.util.HashMap;
import me.ccattell.plugins.colorscramble.commands.CSAdmin;
import me.ccattell.plugins.colorscramble.database.Database;
import me.ccattell.plugins.colorscramble.database.InitMySQL;
import me.ccattell.plugins.colorscramble.database.InitSQLite;
import me.ccattell.plugins.colorscramble.utilities.VersionCheck;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ColorScramble extends JavaPlugin implements Listener {

    public static ColorScramble plugin;
    Database service = Database.getInstance();
    public PluginDescriptionFile pdfFile;
    public HashMap<String, String> trackPlayers = new HashMap<String, String>();
    public ConsoleCommandSender console;
    public String dbtype;
    public PluginManager pm = Bukkit.getServer().getPluginManager();
    protected VersionCheck versionCheck;
    public String pluginName = ChatColor.DARK_PURPLE + "[Color Shuffle]" + ChatColor.RESET + " ";

    @Override
    public void onDisable() {
        // TODO: Place any custom disable code here.
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        pdfFile = getDescription();

        plugin = this;
        console = getServer().getConsoleSender();
        String UpdateChannel = plugin.getConfig().getString("System.UpdateChannel");
        if (!UpdateChannel.equalsIgnoreCase("none")) {
            console.sendMessage(plugin.pluginName + "Update Channel: " + UpdateChannel);
            this.versionCheck = new VersionCheck(this, "http://dev.bukkit.org/server-mods/color-scramble/files.rss");
            if (this.versionCheck.updateNeeded()) {
                String update = this.versionCheck.getUpdate();
                if (update.equalsIgnoreCase("none")) {
                    console.sendMessage(pluginName + "There are no files to test in your channel");
                } else if (update.equalsIgnoreCase("no")) {
                    console.sendMessage(pluginName + "Congratulations, you are running the latest version of ColorScramble!");
                } else if (update.equalsIgnoreCase("yes")) {
                    console.sendMessage(pluginName + ChatColor.GOLD + "A new version is available: " + ChatColor.DARK_GREEN + this.versionCheck.getVersion() + ChatColor.RESET);
                    console.sendMessage(pluginName + ChatColor.GOLD + "Get it from: " + ChatColor.DARK_GREEN + this.versionCheck.getLink() + ChatColor.RESET);
                } else if (update.equalsIgnoreCase("dev")) {
                    console.sendMessage(pluginName + "You are using an unreleased version of the plugin!");
                }
            }
        }
        try {
            if (getConfig().getString("System.Database.Type").equals("sqlite")) {
                console.sendMessage(pluginName + "Loading SQLite Database");
                String path = getDataFolder() + File.separator + "ColorScramble.db";
                service.setConnection(path);
                new InitSQLite().initSQLite();
            } else {
                // mysql
                console.sendMessage(pluginName + "Loading MySQL Database");
                service.setConnection();
                new InitMySQL().initMYSQL();
            }
        } catch (Exception e) {
            console.sendMessage(pluginName + ChatColor.GOLD + "Connection and Tables Error: " + e + ChatColor.RESET);
        }
        getCommand("csadmin").setExecutor(new CSAdmin(this));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
    }
}
