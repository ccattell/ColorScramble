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
import me.ccattell.plugins.colorscramble.ColorScramble;
import me.ccattell.plugins.colorscramble.ColorScrambleCarpetLayer;
import me.ccattell.plugins.colorscramble.ColorScrambleClearArea;
import me.ccattell.plugins.colorscramble.ColorScrambleFloorBuilder;
import me.ccattell.plugins.colorscramble.ColorScrambleWallBuilder;
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

    private ColorScramble plugin;
    public String moduleName = ChatColor.GREEN + "[Color Scramble]" + ChatColor.RESET + " ";

    public CSAdmin(ColorScramble plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("csadmin")) {
            final Player player;
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
            Location l = player.getLocation();
            World world = l.getWorld();
            Chunk chunk = l.getChunk();
            int chunkx = chunk.getX();
            int chunkz = chunk.getZ();
            Chunk chunkstart = world.getChunkAt(chunkx - 1, chunkz - 1);
            Chunk chunkend = world.getChunkAt(chunkx + 1, chunkz + 1);
            int sx = chunkstart.getBlock(0, 0, 0).getX();
            int sz = chunkstart.getBlock(0, 0, 0).getZ();
            int ex = chunkend.getBlock(15, 0, 15).getX();
            int ez = chunkend.getBlock(15, 0, 15).getZ();
            final Location one = new Location(world, sx, 0, sz);
            final Location two = new Location(world, ex, 74, ez);
            addWGProtection(player, one, two);
            // set the player flying
            player.setAllowFlight(true);
            player.setFlying(true);
            l.setY(65D);
            player.teleport(l);
            // hollow out 3x3 chunk area and set quartz block walls at arena boundaries from y0 to y74, then place white carpet covered ice at y64 and create a world guard region
            // ~ 1 second
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    sender.sendMessage(moduleName + "Clearing the area...");
                    System.out.println("Starting clearing...");
                    new ColorScrambleClearArea().clear(one);
                }
            }, 10L);
            // floor building takes about 10-12 seconds here still slow
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    sender.sendMessage(moduleName + "Building the floor...");
                    System.out.println("Starting floor...");
                    // build the floor
                    new ColorScrambleFloorBuilder().build(one);
                }
            }, 110L);
            // ~ 1 second, longer if floor building comes first
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    sender.sendMessage(moduleName + "Building the walls...");
                    System.out.println("Starting walls...");
                    // build the walls
                    new ColorScrambleWallBuilder().build(one, two);
                }
            }, 210L);
            // if floor building is here it takes 27 seconds and the client times out! Why?
//            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
//                public void run() {
//                    sender.sendMessage(moduleName + "Building the floor...");
//                    System.out.println("Starting floor...");
//                    // build the floor
//                    new ColorScrambleFloorBuilder().build(one);
//                }
//            }, 130L);
            // less than a second
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    sender.sendMessage(moduleName + "Laying the carpet...");
                    System.out.println("Starting carpet...");
                    // build the floor
                    new ColorScrambleCarpetLayer().lay(one);
                }
            }, 310L);
        }
        return true;
    }

    /**
     * Adds a WorldGuard protected region.
     *
     * @param p the player to assign as the owner of the region
     * @param one a location
     * @param two another location
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
