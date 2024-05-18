package com.hereandtheregames.davethehelper.utils;

public final class GameConfig {
    public static final int VIEWPORT_WIDTH = 1360;//450;
    public static final int VIEWPORT_HEIGHT = 900;//800

//    public static final int VIEWPORT_WIDTH = 1024;
//    public static final int VIEWPORT_HEIGHT = 768;
    public static final float UNIT_SCALE_PPM = 1/16f;//Pixel Per Meter
    public static final float UNIT_SCALE = 16f;//800
    public static final float UNIT_SPRITE_WIDTH = 1.0f;//1f;
    public static final float UNIT_SPRITE_HEIGHT = 1.2f;//1.5f;

    public static final float NPC_SPRITE_WIDTH = 1.1f;//1f;
    public static final float NPC_SPRITE_HEIGHT = 1.5f;//1.5f;

    public static final float WORLD_WIDTH_IN_TILES = 20f;
    public static final float WORLD_HEIGHT_IN_TILES = 20f;

    public static final short GROUND_CATEGORY = 0;
    public static final short WALL_CATEGORY = 1;
    public static final short NONE_CATEGORY = 1 << 1;
    public static final short PLAYER_CATEGORY = 1 << 2;
    public static final short NPC_CATEGORY = 1 << 3;
    public static final short INTERACTIBLE_CATEGORY = 1 << 4;


}
