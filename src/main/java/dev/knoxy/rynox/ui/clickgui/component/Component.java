package dev.knoxy.rynox.ui.clickgui.component;

import net.minecraft.client.gui.DrawContext;

public abstract class Component {
    protected int x, y, width, height;

    public Component(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void render(DrawContext context, int mouseX, int mouseY, float delta, float alpha);

    public abstract void mouseClicked(double mouseX, double mouseY, int button);

    public abstract void mouseReleased(double mouseX, double mouseY, int button);

    public abstract void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY);

    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    // Getters and setters for position and size
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
}
