package dev.knoxy.rynox.client.gui.util;

import java.awt.Color;

public class AnimationUtil {

    public static double lerp(double start, double end, double progress) {
        return start + (end - start) * progress;
    }

    public static Color lerp(Color start, Color end, float progress) {
        float r = (float) lerp(start.getRed(), end.getRed(), progress);
        float g = (float) lerp(start.getGreen(), end.getGreen(), progress);
        float b = (float) lerp(start.getBlue(), end.getBlue(), progress);
        float a = (float) lerp(start.getAlpha(), end.getAlpha(), progress);
        return new Color(r / 255f, g / 255f, b / 255f, a / 255f);
    }

    public static float easeInOut(float t) {
        return t < 0.5f ? 2 * t * t : -1 + (4 - 2 * t) * t;
    }
}
