package net.silveros.game;

import net.silveros.entity.RivalsTags;

public enum RivalsMap {
    TERRA(RivalsTags.MAP_TERRA, "Terra"),
    ARID(RivalsTags.MAP_ARID, "Arid"),;

    public String mapTag, displayName;

    RivalsMap(String tag, String name) {
        this.mapTag = tag;
        this.displayName = name;
    }
}
