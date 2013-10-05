package com.censoredsoftware.util;

import com.minegusta.mauswashere.MausWasHere;
import org.bukkit.Location;
import org.bukkit.World;

public class Zones {
    public static boolean inNoTeachJavaZone(Location location) {
        return isNoTeachJavaWorld(location.getWorld());
    }

    public static boolean isNoTeachJavaWorld(World world) {
        return MausWasHere.DISABLED_WORLDS.contains(world.getName());
    }
}
