/*
 *  Copyright 2013 eccentric_nz.
 */
package me.ccattell.plugins.colorscramble;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

/**
 *
 * @author eccentric_nz
 */
public class ColorScrambleFloorBuilder {

    public void build(Location one, Location two) {
        World w = one.getWorld();
        int sx = one.getBlockX() + 1;
        int sz = one.getBlockZ() + 1;
        int ex = two.getBlockX();
        int ez = two.getBlockZ();

        // build ice floor
        for (int ix = sx; ix < ex; ix++) {
            for (int iz = sz; iz < ez; iz++) {
                w.getBlockAt(ix, 64, iz).setType(Material.ICE);
                w.getBlockAt(ix, 65, iz).setType(Material.CARPET);
            }
        }
        System.out.println("Finished floor");
    }
}
