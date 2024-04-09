package net.silveros.game;

import net.silveros.entity.RivalsTags;
import org.bukkit.Material;

public enum RivalsMap {
    TERRA(RivalsTags.MAP_TERRA, "Terra"),
    SANDSTORM(RivalsTags.MAP_SANDSTORM, "Sandstorm"),
    RETRO(RivalsTags.MAP_RETRO, "Retro"),
    GOOD_INTENTIONS(RivalsTags.MAP_GOOD_INTENTIONS, "Good Intentions", 13000),
    MELT(RivalsTags.MAP_MELT, "Melt", 12500, Weather.THUNDER),;

    public String mapTag, displayName;
    public int time = RivalsCore.DEFAULT_TIME;
    public Weather weather = Weather.NONE;
    public BlockColor colorRed = BlockColor.RED, colorBlue = BlockColor.BLUE;

    RivalsMap(String tag, String name) {
        this.mapTag = tag;
        this.displayName = name;
    }

    RivalsMap(String tag, String name, BlockColor red, BlockColor blue) {
        this(tag, name);
        this.colorRed = red;
        this.colorBlue = blue;
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

    enum BlockColor {
        WHITE(Material.WHITE_WOOL, Material.WHITE_STAINED_GLASS),
        RED(Material.RED_WOOL, Material.RED_STAINED_GLASS), MAGENTA(Material.MAGENTA_WOOL, Material.MAGENTA_STAINED_GLASS), PINK(Material.PINK_WOOL, Material.PINK_STAINED_GLASS),
        BLUE(Material.BLUE_WOOL, Material.BLUE_STAINED_GLASS), CYAN(Material.CYAN_WOOL, Material.CYAN_STAINED_GLASS), LIGHT_BLUE(Material.LIGHT_BLUE_WOOL, Material.LIGHT_BLUE_STAINED_GLASS);

        private Material blockWool, blockGlass;

        BlockColor(Material wool, Material glass) {
            this.blockWool = wool;
            this.blockGlass = glass;
        }

        public Material getWoolBlock() {
            return this.blockWool;
        }

        public Material getGlassBlock() {
            return this.blockGlass;
        }
    }
}
