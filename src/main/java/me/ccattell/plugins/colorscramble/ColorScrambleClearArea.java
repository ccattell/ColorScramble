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

    public void clear(Location one, Location two) {
        World w = one.getWorld();
        int sx = one.getBlockX() + 1;
        int sy = one.getBlockY();
        int sz = one.getBlockZ() + 1;
        int ex = two.getBlockX();
        int ey = two.getBlockY();
        int ez = two.getBlockZ();

        // clear the area
        for (int x = sx; x < ex; x++) {
            for (int z = sz; z < ez; z++) {
                for (int y = ey; y >= sy; y--) {
                    if (y != 64 && y != 65 && y != 110 && y != 100) {
                        w.getBlockAt(x, y, z).setType(Material.AIR);
                    }
                }
            }
        }
        System.out.println("Finished clearing");
    }
}
