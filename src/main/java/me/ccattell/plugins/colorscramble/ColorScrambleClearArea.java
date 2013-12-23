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
        // clear the area
        for (int x = sx; x < (sx + 46); x++) {
            for (int z = sz; z < (sz + 46); z++) {
                for (int y = 0; y < 74; y++) {
                    w.getBlockAt(x, y, z).setType(Material.AIR);
                }
            }
        }
        System.out.println("Finished clearing");
    }
}
