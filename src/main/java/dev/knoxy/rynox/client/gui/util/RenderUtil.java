package dev.knoxy.rynox.client.gui.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import org.joml.Matrix4f;

public class RenderUtil {

    public static void drawRoundedRect(DrawContext context, double x, double y, double width, double height, double radius, int color) {
        Matrix4f matrix = context.getMatrices().peek().getPositionMatrix();
        float f = (float) (color >> 24 & 255) / 255.0F;
        float g = (float) (color >> 16 & 255) / 255.0F;
        float h = (float) (color >> 8 & 255) / 255.0F;
        float k = (float) (color & 255) / 255.0F;

        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);

        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();

        // Draw the main rectangle
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(x + radius, y, 0).color(g, h, k, f).next();
        bufferBuilder.vertex(x + width - radius, y, 0).color(g, h, k, f).next();
        bufferBuilder.vertex(x + width - radius, y + height, 0).color(g, h, k, f).next();
        bufferBuilder.vertex(x + radius, y + height, 0).color(g, h, k, f).next();

        bufferBuilder.vertex(x, y + radius, 0).color(g, h, k, f).next();
        bufferBuilder.vertex(x + radius, y + radius, 0).color(g, h, k, f).next();
        bufferBuilder.vertex(x + radius, y + height - radius, 0).color(g, h, k, f).next();
        bufferBuilder.vertex(x, y + height - radius, 0).color(g, h, k, f).next();

        bufferBuilder.vertex(x + width - radius, y + radius, 0).color(g, h, k, f).next();
        bufferBuilder.vertex(x + width, y + radius, 0).color(g, h, k, f).next();
        bufferBuilder.vertex(x + width, y + height - radius, 0).color(g, h, k, f).next();
        bufferBuilder.vertex(x + width - radius, y + height - radius, 0).color(g, h, k, f).next();
        Tessellator.getInstance().draw();

        // Draw the corners
        drawCircle(context, x + radius, y + radius, radius, color, 180, 270);
        drawCircle(context, x + width - radius, y + radius, radius, color, 270, 360);
        drawCircle(context, x + width - radius, y + height - radius, radius, color, 0, 90);
        drawCircle(context, x + radius, y + height - radius, radius, color, 90, 180);

        RenderSystem.disableBlend();
    }

    public static void drawCircle(DrawContext context, double x, double y, double radius, int color) {
        drawCircle(context, x, y, radius, color, 0, 360);
    }

    public static void drawCircle(DrawContext context, double x, double y, double radius, int color, int startAngle, int endAngle) {
        Matrix4f matrix = context.getMatrices().peek().getPositionMatrix();
        float f = (float) (color >> 24 & 255) / 255.0F;
        float g = (float) (color >> 16 & 255) / 255.0F;
        float h = (float) (color >> 8 & 255) / 255.0F;
        float k = (float) (color & 255) / 255.0F;

        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);

        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(x, y, 0).color(g, h, k, f).next();

        for (int i = startAngle; i <= endAngle; i++) {
            double angle = Math.toRadians(i);
            bufferBuilder.vertex(x + Math.sin(angle) * radius, y + Math.cos(angle) * radius, 0).color(g, h, k, f).next();
        }

        Tessellator.getInstance().draw();
        RenderSystem.disableBlend();
    }
}
