package net.silveros.game;

import net.silveros.utility.Vec3;

public class SpawnLocations {
    public static final Vec3 TERRA_RED = new Vec3(-344, 10, -406);
    public static final Vec3 TERRA_BLUE = new Vec3(-344, 10, -300);
    public static final Vec3 TERRA_SPECTATOR = new Vec3(-344, 21, -353);

    public static final Vec3 SANDSTORM_RED = new Vec3(-349, 31, -850);
    public static final Vec3 SANDSTORM_BLUE = new Vec3(-454, 31, -733);
    public static final Vec3 SANDSTORM_SPECTATOR = new Vec3(-403, 48, -795);

    public static final Vec3 RETRO_RED = new Vec3(177, 15, -685);
    public static final Vec3 RETRO_BLUE = new Vec3(177, 15, -837);
    public static final Vec3 RETRO_SPECTATOR = new Vec3(177, 20, -761);

    public static final Vec3 GOOD_INTENTIONS_RED = new Vec3(143, 18, -1800);
    public static final Vec3 GOOD_INTENTIONS_BLUE = new Vec3(-69, 18, -1752);
    public static final Vec3 GOOD_INTENTIONS_SPECTATOR = new Vec3(38, 24, -1776);

    public static final Vec3 MELT_RED = new Vec3(1034, 22, -743);
    public static final Vec3 MELT_BLUE = new Vec3(1148, 22, -857);
    public static final Vec3 MELT_SPECTATOR = new Vec3(1091, 25, -799);

    public static Vec3 getBlueSpawn(RivalsMap map) {
        switch (map) {
            case SANDSTORM:
                return SANDSTORM_BLUE;
            case RETRO:
                return RETRO_BLUE;
            case GOOD_INTENTIONS:
                return GOOD_INTENTIONS_BLUE;
            case MELT:
                return MELT_BLUE;
            default:
                return TERRA_BLUE;
        }
    }

    public static Vec3 getRedSpawn(RivalsMap map) {
        switch (map) {
            case SANDSTORM:
                return SANDSTORM_RED;
            case RETRO:
                return RETRO_RED;
            case GOOD_INTENTIONS:
                return GOOD_INTENTIONS_RED;
            case MELT:
                return MELT_RED;
            default:
                return TERRA_RED;
        }
    }

    public static Vec3 getSpectatorSpawn(RivalsMap map) {
        switch (map) {
            case SANDSTORM:
                return SANDSTORM_SPECTATOR;
            case RETRO:
                return RETRO_SPECTATOR;
            case GOOD_INTENTIONS:
                return GOOD_INTENTIONS_SPECTATOR;
            case MELT:
                return MELT_SPECTATOR;
            default:
                return TERRA_SPECTATOR;
        }
    }
}
