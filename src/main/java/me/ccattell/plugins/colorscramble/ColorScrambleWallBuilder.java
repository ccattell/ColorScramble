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
        int sy = one.getBlockY();
        int sz = one.getBlockZ();
        int ex = two.getBlockX();
        int ey = two.getBlockY();
        int ez = two.getBlockZ();
        // build first and third walls
        for (int x1 = sx; x1 < ex; x1++) {
            for (int y1 = sy; y1 < ey; y1++) {
                w.getBlockAt(x1, y1, sz).setType(Material.QUARTZ_BLOCK);
                w.getBlockAt(x1, y1, ez).setType(Material.QUARTZ_BLOCK);
            }
        }
        // build second and fourth walls
        for (int z2 = sz; z2 < ez; z2++) {
            for (int y2 = sy; y2 < ey; y2++) {
                w.getBlockAt(sx, y2, z2).setType(Material.QUARTZ_BLOCK);
                w.getBlockAt(ex, y2, z2).setType(Material.QUARTZ_BLOCK);
            }
        }
        System.out.println("Finished walls");
    }
}
