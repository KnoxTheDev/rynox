package dev.knoxy.rynox.client.gui.clickgui.frame;

import dev.knoxy.rynox.client.gui.clickgui.api.Component;
import dev.knoxy.rynox.client.gui.clickgui.api.Container;
import dev.knoxy.rynox.client.module.Category;
import net.minecraft.client.MinecraftClient;
import dev.knoxy.rynox.client.gui.clickgui.theme.Colors;
import net.minecraft.client.gui.DrawContext;

public class Frame extends Container {

    private final Category category;
    private boolean dragging;
    private int dragX;
    private int dragY;

    public Frame(Category category, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.category = category;
        this.dragging = false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (dragging) {
            setX(mouseX - dragX);
            setY(mouseY - dragY);
        }

        int currentHeight = 14;
        for(Component component : components) {
            currentHeight += component.getHeight();
        }

        // Render frame background
        context.fill(x, y, x + width, y + currentHeight, Colors.BACKGROUND.getRGB());
        // Render title bar
        context.fill(x, y, x + width, y + 14, Colors.TITLE_BAR.getRGB());
        // Render title
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, category.getName(), x + 2, y + 3, Colors.TEXT.getRGB());

        int offsetY = 14;
        for (Component component : components) {
            component.setX(this.x);
            component.setY(this.y + offsetY);
            component.render(context, mouseX, mouseY, delta);
            offsetY += component.getHeight();
        }
        this.height = currentHeight;
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + 14 && button == 0) {
            dragging = true;
            dragX = (int) (mouseX - x);
            dragY = (int) (mouseY - y);
        }
        super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            dragging = false;
        }
        super.mouseReleased(mouseX, mouseY, button);
    }
}
