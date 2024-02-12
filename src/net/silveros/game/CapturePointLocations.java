package net.silveros.game;

import net.silveros.utility.Vec3;

import java.util.HashMap;
import java.util.Map;

public class CapturePointLocations {
    public static Map<Points, Vec3> TERRA_pointLocations = new HashMap<>();

    public static void init() {
        addLocation(RivalsMap.TERRA, Points.POINT_A, new Vec3(-19, 1, -251));
        addLocation(RivalsMap.TERRA, Points.POINT_B, new Vec3(-60, -1, -207));
        addLocation(RivalsMap.TERRA, Points.POINT_C, new Vec3(-61, 4, -293));
        addLocation(RivalsMap.TERRA, Points.POINT_D, new Vec3(29, 3, -298));
        addLocation(RivalsMap.TERRA, Points.POINT_E, new Vec3(28, 3, -206));
    }

    public static void addLocation(RivalsMap map, Points point, Vec3 vec) {
        if (map == RivalsMap.TERRA) {
            TERRA_pointLocations.put(point, vec);
        }
    }
}
