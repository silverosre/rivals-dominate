package net.silveros.game;

import net.silveros.utility.Vec3;

import java.util.HashMap;
import java.util.Map;

public class CapturePointLocations {
    public static Map<Points, Vec3> TERRA_pointLocations = new HashMap<>();
    public static Map<Points, Vec3> SANDSTORM_pointLocations = new HashMap<>();
    public static Map<Points, Vec3> RETRO_pointLocations = new HashMap<>();
    public static Map<Points, Vec3> GOOD_INTENTIONS_pointLocations = new HashMap<>();
    public static Map<Points, Vec3> MELT_pointLocations = new HashMap<>();

    public static void init() {
        addLocation(RivalsMap.TERRA, Points.POINT_A, new Vec3(-344, 10, -353));
        addLocation(RivalsMap.TERRA, Points.POINT_B, new Vec3(-385, 8, -309));
        addLocation(RivalsMap.TERRA, Points.POINT_C, new Vec3(-386, 13, -395));
        addLocation(RivalsMap.TERRA, Points.POINT_D, new Vec3(-296, 12, -400));
        addLocation(RivalsMap.TERRA, Points.POINT_E, new Vec3(-297, 12, -308));

        addLocation(RivalsMap.SANDSTORM, Points.POINT_A, new Vec3(-403, 32, -795));
        addLocation(RivalsMap.SANDSTORM, Points.POINT_B, new Vec3(-417, 31, -745));
        addLocation(RivalsMap.SANDSTORM, Points.POINT_C, new Vec3(-356, 30, -746));
        addLocation(RivalsMap.SANDSTORM, Points.POINT_D, new Vec3(-396, 34, -845));
        addLocation(RivalsMap.SANDSTORM, Points.POINT_E, new Vec3(-448, 40, -818));

        addLocation(RivalsMap.RETRO, Points.POINT_A, new Vec3(177, 6, -761));
        addLocation(RivalsMap.RETRO, Points.POINT_B, new Vec3(155, 20, -788));
        addLocation(RivalsMap.RETRO, Points.POINT_C, new Vec3(207, 20, -726));
        addLocation(RivalsMap.RETRO, Points.POINT_D, new Vec3(230, 6, -814));
        addLocation(RivalsMap.RETRO, Points.POINT_E, new Vec3(154, 14, -714));

        addLocation(RivalsMap.GOOD_INTENTIONS, Points.POINT_A, new Vec3(38, 16, -1776));
        addLocation(RivalsMap.GOOD_INTENTIONS, Points.POINT_B, new Vec3(2, 27, -1818));
        addLocation(RivalsMap.GOOD_INTENTIONS, Points.POINT_C, new Vec3(72, 23, -1818));
        addLocation(RivalsMap.GOOD_INTENTIONS, Points.POINT_D, new Vec3(83, 9, -1726));
        addLocation(RivalsMap.GOOD_INTENTIONS, Points.POINT_E, new Vec3(-3, 14, -1735));

        addLocation(RivalsMap.MELT, Points.POINT_A, new Vec3(38, 16, -1776));
        addLocation(RivalsMap.MELT, Points.POINT_B, new Vec3(2, 27, -1818));
        addLocation(RivalsMap.MELT, Points.POINT_C, new Vec3(72, 23, -1818));
        addLocation(RivalsMap.MELT, Points.POINT_D, new Vec3(83, 9, -1726));
        addLocation(RivalsMap.MELT, Points.POINT_E, new Vec3(-3, 14, -1735));
    }

    public static void addLocation(RivalsMap map, Points point, Vec3 vec) {
        switch (map) {
            case SANDSTORM:
                SANDSTORM_pointLocations.put(point, vec);
                break;
            case RETRO:
                RETRO_pointLocations.put(point, vec);
                break;
            case GOOD_INTENTIONS:
                GOOD_INTENTIONS_pointLocations.put(point, vec);
                break;
            case MELT:
                MELT_pointLocations.put(point, vec);
                break;
            default:
                TERRA_pointLocations.put(point, vec);
                break;
        }
    }

    public static Map<Points, Vec3> getPointLocations(RivalsMap map) {
        switch (map) {
            case SANDSTORM:
                return SANDSTORM_pointLocations;
            case RETRO:
                return RETRO_pointLocations;
            case GOOD_INTENTIONS:
                return GOOD_INTENTIONS_pointLocations;
            case MELT:
                return MELT_pointLocations;
            default:
                return TERRA_pointLocations;
        }
    }
}
