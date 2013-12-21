package me.ccattell.plugins.colorscramble;

import me.ccattell.plugins.colorscramble.commands.CSAdmin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ColorScramble extends JavaPlugin implements Listener {
    public void onDisable() {
        // TODO: Place any custom disable code here.
    }

    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("csadmin").setExecutor(new CSAdmin());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
    }
}

