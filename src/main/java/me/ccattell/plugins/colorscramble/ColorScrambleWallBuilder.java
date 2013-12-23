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
public class ColorScrambleWallBuilder {

    public void build(Location one, Location two) {
        World w = one.getWorld();
        int sx = one.getBlockX();
        int sz = one.getBlockZ();
        // build first wall
        for (int x1 = sx; x1 < (sx + 48); x1++) {
            for (int y1 = 0; y1 < 74; y1++) {
                w.getBlockAt(x1, y1, sz).setType(Material.QUARTZ_BLOCK);
            }
        }
        // build second wall
        for (int z2 = (sz + 1); z2 < (sz + 48); z2++) {
            for (int y2 = 0; y2 < 74; y2++) {
                w.getBlockAt(sx, y2, z2).setType(Material.QUARTZ_BLOCK);
            }
        }
        int ex = two.getBlockX();
        int ez = two.getBlockZ();
        // build third wall
        for (int x3 = ex; x3 > (ex - 47); x3--) {
            for (int y3 = 0; y3 < 74; y3++) {
                w.getBlockAt(x3, y3, ez).setType(Material.QUARTZ_BLOCK);
            }
        }
        // build fourth wall
        for (int z4 = (ez - 1); z4 > (ez - 47); z4--) {
            for (int y4 = 0; y4 < 74; y4++) {
                w.getBlockAt(ex, y4, z4).setType(Material.QUARTZ_BLOCK);
            }
        }
        System.out.println("Finished walls");
    }
}
