package dev.knoxy.rynox.client.gui.clickgui.frame;

import dev.knoxy.rynox.client.gui.clickgui.api.Component;
import dev.knoxy.rynox.client.gui.clickgui.api.Container;
import dev.knoxy.rynox.client.gui.clickgui.util.RenderUtil;
import dev.knoxy.rynox.client.module.Category;
import net.minecraft.client.MinecraftClient;
import dev.knoxy.rynox.client.gui.clickgui.theme.Colors;
import net.minecraft.client.gui.DrawContext;

public class Frame extends Container {

    private final Category category;
    private boolean dragging;
    private int dragX;
    private int dragY;
    private boolean open = true;
    private float openAnimation;

    public Frame(Category category, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.category = category;
        this.dragging = false;
        this.openAnimation = 1.0f;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (dragging) {
            setX(mouseX - dragX);
            setY(mouseY - dragY);
        }

        if (open) {
            openAnimation = Math.min(1.0f, openAnimation + delta * 5.0f);
        } else {
            openAnimation = Math.max(0.0f, openAnimation - delta * 5.0f);
        }

        int totalHeight = 14;
        for (Component component : components) {
            totalHeight += component.getHeight();
        }
        int currentHeight = 14 + (int) ((totalHeight - 14) * openAnimation);


        // Render frame shadow
        RenderUtil.drawShadow(context, x, y, width, currentHeight, 5, Colors.BACKGROUND);
        // Render frame background
        RenderUtil.drawRoundedRect(context, x, y, width, currentHeight, 5, Colors.BACKGROUND);
        // Render title bar
        RenderUtil.drawRoundedRect(context, x, y, width, 14, 5, Colors.TITLE_BAR);
        // Render title
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, category.getName(), x + width / 2 - MinecraftClient.getInstance().textRenderer.getWidth(category.getName()) / 2, y + 3, Colors.TEXT);

        if (openAnimation > 0) {
            int offsetY = 14;
            for (Component component : components) {
                component.setX(this.x);
                component.setY(this.y + offsetY);
                component.render(context, mouseX, mouseY, delta);
                offsetY += component.getHeight();
            }
        }
        this.height = currentHeight;
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY, 14)) {
            if (button == 0) {
                dragging = true;
                dragX = (int) (mouseX - x);
                dragY = (int) (mouseY - y);
            } else if (button == 1) {
                open = !open;
            }
        }
        if (openAnimation > 0) {
            super.mouseClicked(mouseX, mouseY, button);
        }
    }

    private boolean isMouseOver(double mouseX, double mouseY, int height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }


    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            dragging = false;
        }
        super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        if (openAnimation > 0) {
            super.keyPressed(keyCode, scanCode, modifiers);
        }
    }
}
