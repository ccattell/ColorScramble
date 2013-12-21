package me.ccattell.plugins.colorscramble.commands;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Charlie
 */
public class CSAdmin implements CommandExecutor{

    public String moduleName = ChatColor.GREEN + "[Color Scramble]" + ChatColor.RESET + " ";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("csadmin")) {
            Player player;
            if (sender instanceof Player) {
                player = (Player) sender;
            } else {
                sender.sendMessage(moduleName + "That command cannot be used from the console!");
                return true;
            }
            if (!sender.hasPermission("cs.admin")) {
                sender.sendMessage(moduleName + "You don't have permission to use that command!");
                return true;
            }
            World world = player.getLocation().getWorld();
            Chunk chunk = player.getLocation().getChunk();
            int chunkx = chunk.getX();
            int chunkz = chunk.getZ();
            Chunk chunkstart = world.getChunkAt(chunkx+1, chunkz+1);
            Chunk chunkend = world.getChunkAt(chunkx-1, chunkz-1);
            int xx = chunk.getBlock(0,0,0);
        // hollow out 3x3 chunk area and set quartz block walls at arena boundaries from y0 to y128, then place white carpet covered ice at y64 and create a world guard region
        }
        return true;
    }
}
