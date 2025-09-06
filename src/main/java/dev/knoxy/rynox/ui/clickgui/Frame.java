package dev.knoxy.rynox.ui.clickgui;

import dev.knoxy.rynox.ui.clickgui.component.Component;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;
import java.util.List;

public class Frame {
    public int x, y, width, height;
    private final String title;
    private final List<Component> components;
    private boolean dragging;
    private int dragX, dragY;

    // Layout
    private int yOffset;
    private final int padding = 5;

    public Frame(String title, int x, int y, int width, int height) {
        this.title = title;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.components = new ArrayList<>();
        this.dragging = false;
        this.yOffset = 20 + padding; // Start placing components below the title bar
    }

    public void addComponent(Component component) {
        // Set component's position relative to the frame
        component.setX(this.x + padding);
        component.setY(this.y + this.yOffset);

        // Add to list and update offset for the next component
        this.components.add(component);
        this.yOffset += component.getHeight() + padding;
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Title bar background
        context.fill(x, y, x + width, y + 20, 0xFF404040);

        // Frame background
        context.fill(x, y + 20, x + width, y + height, 0x90202020);

        // Title text
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, title, x + 5, y + 7, 0xFFFFFFFF);

        // Render components
        for (Component component : components) {
            component.render(context, mouseX, mouseY, delta);
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOverHeader(mouseX, mouseY) && button == 0) {
            dragging = true;
            dragX = (int) (this.x - mouseX);
            dragY = (int) (this.y - mouseY);
        }

        for (Component component : components) {
            component.mouseClicked(mouseX, mouseY, button);
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            dragging = false;
        }

        for (Component component : components) {
            component.mouseReleased(mouseX, mouseY, button);
        }
    }

    public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (dragging) {
            int oldX = this.x;
            int oldY = this.y;
            this.x = (int) (mouseX + dragX);
            this.y = (int) (mouseY + dragY);

            int dx = this.x - oldX;
            int dy = this.y - oldY;

            // Move all child components with the frame
            for (Component component : components) {
                component.setX(component.getX() + dx);
                component.setY(component.getY() + dy);
            }
        }

        // Also pass the drag event to components (for sliders)
        for (Component component : components) {
            component.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        }
    }

    private boolean isMouseOverHeader(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + 20;
    }
}
