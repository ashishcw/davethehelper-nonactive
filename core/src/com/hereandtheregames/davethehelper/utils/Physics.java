package com.hereandtheregames.davethehelper.utils;

import com.badlogic.gdx.math.Vector2;

public final class Physics {

    public static final float PIXELS_PER_UNIT = 16f;

    private Physics() {}

    public static float toUnits(float pixels) {
        return pixels / PIXELS_PER_UNIT;
    }

    public static Vector2 toUnits(Vector2 pixels) {
        return new Vector2(toUnits(pixels.x), toUnits(pixels.y));
    }

    public static float toPixels(float units) {
        return units * PIXELS_PER_UNIT;
    }

    public static Vector2 toPixels(Vector2 units) {
        return new Vector2(toPixels(units.x), toPixels(units.y));
    }
}