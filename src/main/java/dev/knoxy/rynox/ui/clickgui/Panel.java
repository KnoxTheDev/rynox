package dev.knoxy.rynox.ui.clickgui;

import dev.knoxy.rynox.module.Module;
import dev.knoxy.rynox.module.ModuleManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Panel {

    private final Module.Category category;
    private final List<Button> buttons = new ArrayList<>();
    private double x, y, width, height;
    private boolean dragging;
    private double dragX, dragY;

    public Panel(Module.Category category, double x, double y, double width, double height) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        double yOffset = y + 14;
        for (Module module : ModuleManager.getInstance().getModulesInCategory(category)) {
            buttons.add(new Button(module, x, yOffset, width, 14));
            yOffset += 14;
        }
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill((int) x, (int) y, (int) (x + width), (int) (y + height), new Color(0, 0, 0, 180).getRGB());
        context.fill((int) x, (int) y, (int) (x + width), (int) (y + 14), new Color(40, 40, 40, 180).getRGB());
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, category.name(), (int) (x + 2), (int) (y + 2), Color.WHITE.getRGB());

        for (Button button : buttons) {
            button.render(context, mouseX, mouseY, delta);
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            dragging = true;
            dragX = mouseX - x;
            dragY = mouseY - y;
        }
        for (Button b : buttons) {
            b.mouseClicked(mouseX, mouseY, button);
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            dragging = false;
        }
        for (Button b : buttons) {
            b.mouseReleased(mouseX, mouseY, button);
        }
    }

    public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (dragging) {
            x = mouseX - dragX;
            y = mouseY - dragY;
        }
        for (Button b : buttons) {
            b.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        }
    }

    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }
}
