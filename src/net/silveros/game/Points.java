package net.silveros.game;

import net.silveros.entity.RivalsTags;

public enum Points {
    POINT_A(RivalsTags.POINT_A, "Point A"),
    POINT_B(RivalsTags.POINT_B, "Point B"),
    POINT_C(RivalsTags.POINT_C, "Point C"),
    POINT_D(RivalsTags.POINT_D, "Point D"),
    POINT_E(RivalsTags.POINT_E, "Point E");

    public String tag, displayName;

    Points(String s, String name) {
        this.tag = s;
        this.displayName = name;
    }
}
