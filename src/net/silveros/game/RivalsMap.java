package net.silveros.game;

import net.silveros.entity.RivalsTags;

public enum RivalsMap {
    TERRA(RivalsTags.MAP_TERRA, "Terra"),
    SANDSTORM(RivalsTags.MAP_SANDSTORM, "Sandstorm"),
    RETRO(RivalsTags.MAP_RETRO, "Retro"),
    GOOD_INTENTIONS(RivalsTags.MAP_GOOD_INTENTIONS, "Good Intentions", 13000),;

    public String mapTag, displayName;
    public int time = 0;

    RivalsMap(String tag, String name) {
        this.mapTag = tag;
        this.displayName = name;
    }

    RivalsMap(String tag, String name, int t) {
        this(tag, name);
        this.time = t;
    }
}
