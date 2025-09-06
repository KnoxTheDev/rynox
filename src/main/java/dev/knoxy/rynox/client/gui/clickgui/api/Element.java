package dev.knoxy.rynox.client.gui.clickgui.api;

import net.minecraft.client.gui.DrawContext;

public interface Element {

    void render(DrawContext context, int mouseX, int mouseY, float delta);

    void mouseClicked(double mouseX, double mouseY, int button);

    void mouseReleased(double mouseX, double mouseY, int button);

    void keyPressed(int keyCode, int scanCode, int modifiers);

    void setX(int x);

    void setY(int y);

    int getWidth();

    int getHeight();
}
