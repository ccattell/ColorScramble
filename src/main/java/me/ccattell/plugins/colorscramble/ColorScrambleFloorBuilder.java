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

    public void build(Location one) {
        World w = one.getWorld();
        int sx = one.getBlockX() + 1;
        int sz = one.getBlockZ() + 1;
        // build ice floor
        for (int ix = sx; ix < (sx + 46); ix++) {
            for (int iz = sz; iz < (sz + 46); iz++) {
                w.getBlockAt(ix, 64, iz).setType(Material.ICE);
            }
        }
        System.out.println("Finished floor");
    }
}
