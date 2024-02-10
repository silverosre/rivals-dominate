package net.silveros.game;

import net.silveros.utility.Vec3;

import java.util.HashMap;
import java.util.Map;

public class CapturePointLocations {
    public static Map<Points, Vec3> GRASSLANDS_pointLocations = new HashMap<>();

    public static void init() {
        addLocation(RivalsMap.GRASSLANDS, Points.POINT_A, new Vec3(100, 100, 100));
        addLocation(RivalsMap.GRASSLANDS, Points.POINT_B, new Vec3(50, 100, 50));
        addLocation(RivalsMap.GRASSLANDS, Points.POINT_C, new Vec3(50, 100, 150));
        addLocation(RivalsMap.GRASSLANDS, Points.POINT_D, new Vec3(150, 100, 150));
        addLocation(RivalsMap.GRASSLANDS, Points.POINT_E, new Vec3(150, 100, 50));
    }

    public static void addLocation(RivalsMap map, Points point, Vec3 vec) {
        if (map == RivalsMap.GRASSLANDS) {
            GRASSLANDS_pointLocations.put(point, vec);
        }
    }
}
