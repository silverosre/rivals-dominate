package net.silveros.game;

import net.silveros.entity.RivalsTags;

public enum RivalsMap {
    GRASSLANDS(RivalsTags.MAP_GRASSLANDS, "Grasslands"),
    ARID(RivalsTags.MAP_ARID, "Arid"),;

    public String mapTag, displayName;

    RivalsMap(String tag, String name) {
        this.mapTag = tag;
        this.displayName = name;
    }
}
