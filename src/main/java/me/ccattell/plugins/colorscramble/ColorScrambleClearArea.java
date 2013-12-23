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
public class ColorScrambleClearArea {

    public void clear(Location one) {
        World w = one.getWorld();
        int sx = one.getBlockX() + 1;
        int sz = one.getBlockZ() + 1;
        int ex = sx + 46;
        int ez = sz + 46;

        // clear the area
        for (int x = sx; x < ex; x++) {
            for (int z = sz; z < ez; z++) {
                for (int y = 128; y >= 0; y--) {
                    if (y != 64 && y != 65) {
                        w.getBlockAt(x, y, z).setType(Material.AIR);
                    }
                }
            }
        }
        System.out.println("Finished clearing");
    }
}
