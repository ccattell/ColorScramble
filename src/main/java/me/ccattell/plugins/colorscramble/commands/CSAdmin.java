package me.ccattell.plugins.colorscramble.commands;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.databases.ProtectionDatabaseException;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Charlie
 */
public class CSAdmin implements CommandExecutor {

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
            Chunk chunkstart = world.getChunkAt(chunkx - 1, chunkz - 1);
            Chunk chunkend = world.getChunkAt(chunkx + 1, chunkz + 1);
            int sx = chunkstart.getBlock(0, 0, 0).getX();
            int sz = chunkstart.getBlock(0, 0, 0).getZ();
            int ex = chunkend.getBlock(15, 127, 15).getX();
            int ez = chunkend.getBlock(15, 127, 15).getZ();
            Location one = new Location(world, sx, 0, sz);
            Location two = new Location(world, ex, 128, ez);
            addWGProtection(player, one, two);
            // hollow out 3x3 chunk area and set quartz block walls at arena boundaries from y0 to y128, then place white carpet covered ice at y64 and create a world guard region
        }
        return true;
    }

    /**
     * Adds a WorldGuard protected region.
     *
     * @param p the player to assign as the owner of the region
     * @param one a start location of a cuboid region
     * @param two an end location of a cuboid region
     */
    public void addWGProtection(Player p, Location one, Location two) {
        WorldGuardPlugin wg = (WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        RegionManager rm = wg.getRegionManager(one.getWorld());
        BlockVector b1 = makeBlockVector(one);
        BlockVector b2 = makeBlockVector(two);
        ProtectedCuboidRegion region = new ProtectedCuboidRegion("", b1, b2);
        DefaultDomain dd = new DefaultDomain();
        dd.addPlayer(p.getName());
        region.setOwners(dd);
        HashMap<Flag<?>, Object> flags = new HashMap<Flag<?>, Object>();
        flags.put(DefaultFlag.TNT, State.DENY);
        flags.put(DefaultFlag.CREEPER_EXPLOSION, State.DENY);
        flags.put(DefaultFlag.FIRE_SPREAD, State.DENY);
        flags.put(DefaultFlag.LAVA_FIRE, State.DENY);
        flags.put(DefaultFlag.LAVA_FLOW, State.DENY);
        flags.put(DefaultFlag.LIGHTER, State.DENY);
        flags.put(DefaultFlag.MOB_SPAWNING, State.DENY);
        flags.put(DefaultFlag.CONSTRUCT, RegionGroup.OWNERS);
        region.setFlags(flags);
        rm.addRegion(region);
        try {
            rm.save();
        } catch (ProtectionDatabaseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Turns a location object into a BlockVector.
     *
     * @param location the Location to convert to BlockVector
     * @return a BlockVector
     */
    public BlockVector makeBlockVector(Location location) {
        return new BlockVector(location.getX(), location.getY(), location.getZ());
    }
}
