package dev.knoxy.rynox.client.gui.clickgui.util;

import net.minecraft.client.gui.DrawContext;
import java.awt.Color;

public class RenderUtil {

    public static void drawRoundedRect(DrawContext context, double x, double y, double width, double height, double radius, int color) {
        context.fill((int) x, (int) y, (int) (x + width), (int) (y + height), color);
    }

    public static void drawShadow(DrawContext context, double x, double y, double width, double height, double radius, int color) {
        context.fill((int) (x + 2), (int) (y + 2), (int) (x + width + 2), (int) (y + height + 2), new Color(0, 0, 0, 100).getRGB());
    }
}
