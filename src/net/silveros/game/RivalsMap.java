package net.silveros.game;

import net.silveros.entity.RivalsTags;

public enum RivalsMap {
    TERRA(RivalsTags.MAP_TERRA, "Terra"),
    SANDSTORM(RivalsTags.MAP_SANDSTORM, "Sandstorm"),
    RETRO(RivalsTags.MAP_RETRO, "Retro"),;

    public String mapTag, displayName;

    RivalsMap(String tag, String name) {
        this.mapTag = tag;
        this.displayName = name;
    }
}
