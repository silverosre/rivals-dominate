package net.silveros.game;

import net.silveros.utility.Vec3;

public class SpawnLocations {
    public static final Vec3 TERRA_RED = new Vec3(-19, 1, -304);
    public static final Vec3 TERRA_BLUE = new Vec3(-19, 1, -198);
    public static final Vec3 TERRA_SPECTATOR = new Vec3(-19, 5, -251);

    public static final Vec3 SANDSTORM_RED = new Vec3(261, 2, -493);
    public static final Vec3 SANDSTORM_BLUE = new Vec3(156, 2, -376);
    public static final Vec3 SANDSTORM_SPECTATOR = new Vec3(207, 18, -438);

    public static final Vec3 RETRO_RED = new Vec3(546, 11, -4);
    public static final Vec3 RETRO_BLUE = new Vec3(546, 11, -156);
    public static final Vec3 RETRO_SPECTATOR = new Vec3(546, 18, -80);

    public static Vec3 getBlueSpawn(RivalsMap map) {
        if (map == RivalsMap.SANDSTORM) {
            return SANDSTORM_BLUE;
        } else if (map == RivalsMap.RETRO) {
            return RETRO_BLUE;
        }

        return TERRA_BLUE;
    }

    public static Vec3 getRedSpawn(RivalsMap map) {
        if (map == RivalsMap.SANDSTORM) {
            return SANDSTORM_RED;
        } else if (map == RivalsMap.RETRO) {
            return RETRO_RED;
        }

        return TERRA_RED;
    }

    public static Vec3 getSpectatorSpawn(RivalsMap map) {
        if (map == RivalsMap.SANDSTORM) {
            return SANDSTORM_SPECTATOR;
        } else if (map == RivalsMap.RETRO) {
            return RETRO_SPECTATOR;
        }

        return TERRA_SPECTATOR;
    }
}
