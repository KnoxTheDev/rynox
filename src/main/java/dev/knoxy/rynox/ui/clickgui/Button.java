package dev.knoxy.rynox.ui.clickgui;

import dev.knoxy.rynox.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.awt.Color;

public class Button {

    private final Module module;
    private double x, y, width, height;

    public Button(Module module, double x, double y, double width, double height) {
        this.module = module;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        int bgColor = module.isEnabled() ? new Color(0, 170, 0, 180).getRGB() : new Color(40, 40, 40, 180).getRGB();
        if (isMouseOver(mouseX, mouseY)) {
            bgColor = module.isEnabled() ? new Color(0, 170, 0, 220).getRGB() : new Color(0, 0, 0, 180).getRGB();
        }
        context.fill((int) x, (int) y, (int) (x + width), (int) (y + height), bgColor);
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, module.getName(), (int) (x + 2), (int) (y + 2), Color.WHITE.getRGB());
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            module.toggle();
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
    }

    public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        // TODO: Implement mouse drag handling
    }

    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }
}
