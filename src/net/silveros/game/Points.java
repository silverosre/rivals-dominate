package net.silveros.game;

import net.silveros.entity.RivalsTags;

public enum Points {
    POINT_A(RivalsTags.POINT_A),
    POINT_B(RivalsTags.POINT_B),
    POINT_C(RivalsTags.POINT_C),
    POINT_D(RivalsTags.POINT_D),
    POINT_E(RivalsTags.POINT_E);

    public String tag;

    Points(String s) {
        this.tag = s;
    }
}
