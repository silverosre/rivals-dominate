package net.silveros.game;

import net.silveros.entity.RivalsTags;

public enum RivalsMap {
    TERRA(RivalsTags.MAP_TERRA, "Terra", Weather.RAIN),
    SANDSTORM(RivalsTags.MAP_SANDSTORM, "Sandstorm", Weather.THUNDER),
    RETRO(RivalsTags.MAP_RETRO, "Retro"),
    GOOD_INTENTIONS(RivalsTags.MAP_GOOD_INTENTIONS, "Good Intentions", 13000),
    MELT(RivalsTags.MAP_MELT, "Melt", 12500, Weather.THUNDER),;

    public String mapTag, displayName;
    public int time = RivalsCore.DEFAULT_TIME;
    public Weather weather = Weather.NONE;

    RivalsMap(String tag, String name) {
        this.mapTag = tag;
        this.displayName = name;
    }

    RivalsMap(String tag, String name, int t) {
        this(tag, name);
        this.time = t;
    }

    RivalsMap(String tag, String name, Weather w) {
        this(tag, name);
        this.weather = w;
    }

    RivalsMap(String tag, String name, int t, Weather w) {
        this(tag, name, t);
        this.weather = w;
    }

    enum Weather {
        NONE, RAIN, THUNDER;
    }
}
