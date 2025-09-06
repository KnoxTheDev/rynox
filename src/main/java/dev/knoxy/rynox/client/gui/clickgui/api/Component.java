package dev.knoxy.rynox.client.gui.clickgui.api;

import net.minecraft.client.gui.DrawContext;

public abstract class Component implements Element {

    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public Component(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // To be implemented by subclasses
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        // To be implemented by subclasses
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        // To be implemented by subclasses
    }

    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        // To be implemented by subclasses
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
