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
public class ColorScrambleCarpetLayer {

    public void lay(Location one) {
        World w = one.getWorld();
        int sx = one.getBlockX() + 1;
        int sz = one.getBlockZ() + 1;
        // lay the carpet
        for (int cx = sx; cx < (sx + 46); cx++) {
            for (int cz = sz; cz < (sz + 46); cz++) {
                w.getBlockAt(cx, 65, cz).setType(Material.CARPET);
            }
        }
        System.out.println("Finished carpet");
    }
}
