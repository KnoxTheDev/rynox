package dev.knoxy.rynox.client.gui.clickgui.frame;

import dev.knoxy.rynox.client.gui.clickgui.api.Component;
import dev.knoxy.rynox.client.gui.clickgui.api.Container;
import dev.knoxy.rynox.client.module.Category;
import net.minecraft.client.MinecraftClient;
import dev.knoxy.rynox.client.gui.util.AnimationUtil;
import dev.knoxy.rynox.client.gui.util.RenderUtil;
import dev.knoxy.rynox.client.gui.clickgui.theme.Colors;
import dev.knoxy.rynox.client.gui.FontManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderPhase;

public class Frame extends Container {

    private final Category category;
    private boolean dragging;
    private int dragX;
    private int dragY;
    private boolean open = true;
    private float openProgress = 1;

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

        if (open) {
            openProgress = Math.min(1, openProgress + delta / 3);
        } else {
            openProgress = Math.max(0, openProgress - delta / 3);
        }

        int componentsHeight = 0;
        for(Component component : components) {
            componentsHeight += component.getHeight();
        }

        int currentHeight = 14 + (int)(componentsHeight * AnimationUtil.easeInOut(openProgress));

        // Render frame background and title bar
        RenderUtil.drawRoundedRect(context, x, y, width, currentHeight, 3, Colors.BACKGROUND.getRGB());
        RenderUtil.drawRoundedRect(context, x, y, width, 14, 3, Colors.TITLE_BAR.getRGB());

        // Render title
        FontManager.PRODUCT_SANS.drawWithShadow(context.getMatrices(), category.getName(), x + (width - FontManager.PRODUCT_SANS.getWidth(category.getName())) / 2, y + 3, Colors.TEXT.getRGB());

        if (openProgress > 0) {
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
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + 14) {
            if (button == 0) {
                open = !open;
            } else if (button == 1) {
                dragging = true;
                dragX = (int) (mouseX - x);
                dragY = (int) (mouseY - y);
            }
        }
        if(open) {
            super.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            dragging = false;
        }
        super.mouseReleased(mouseX, mouseY, button);
    }
}
