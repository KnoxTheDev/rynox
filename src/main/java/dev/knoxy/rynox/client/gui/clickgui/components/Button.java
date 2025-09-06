package dev.knoxy.rynox.client.gui.clickgui.components;

import dev.knoxy.rynox.client.gui.clickgui.api.Component;
import dev.knoxy.rynox.client.gui.clickgui.api.Container;
import dev.knoxy.rynox.client.module.Module;
import dev.knoxy.rynox.client.gui.util.AnimationUtil;
import dev.knoxy.rynox.client.gui.util.RenderUtil;
import dev.knoxy.rynox.client.gui.FontManager;
import net.minecraft.client.MinecraftClient;
import dev.knoxy.rynox.client.gui.clickgui.theme.Colors;
import net.minecraft.client.gui.DrawContext;

import java.awt.Color;

public class Button extends Container {

    private final Module module;
    private float hoverProgress = 0;
    private boolean open = false;
    private float openProgress = 0;

    public Button(Module module, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.module = module;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (isMouseOver(mouseX, mouseY)) {
            hoverProgress = Math.min(1, hoverProgress + delta / 5);
        } else {
            hoverProgress = Math.max(0, hoverProgress - delta / 5);
        }

        if (open) {
            openProgress = Math.min(1, openProgress + delta / 3);
        } else {
            openProgress = Math.max(0, openProgress - delta / 3);
        }

        Color baseColor = module.isEnabled() ? Colors.ACCENT : Colors.BUTTON;
        Color hoverColor = module.isEnabled() ? Colors.ACCENT.darker() : Colors.BUTTON_HOVER;
        Color color = AnimationUtil.lerp(baseColor, hoverColor, AnimationUtil.easeInOut(hoverProgress));

        RenderUtil.drawRoundedRect(context, x, y, width, height, 2, color.getRGB());
        FontManager.PRODUCT_SANS.drawWithShadow(context.getMatrices(), module.getName(), x + (width - FontManager.PRODUCT_SANS.getWidth(module.getName())) / 2, y + (height - FontManager.PRODUCT_SANS.fontHeight) / 2, Colors.TEXT.getRGB());

        if (openProgress > 0) {
            int offsetY = height;
            for(Component component : components) {
                component.setX(this.x);
                component.setY(this.y + offsetY);
                component.render(context, mouseX, mouseY, delta);
                offsetY += component.getHeight();
            }
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY)) {
            if (button == 0) {
                module.toggle();
            } else if (button == 1) {
                open = !open;
            }
        }
        if (open) {
            super.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public int getHeight() {
        int componentsHeight = 0;
        if (openProgress > 0) {
            for (Component component : components) {
                componentsHeight += component.getHeight();
            }
        }
        return height + (int)(componentsHeight * AnimationUtil.easeInOut(openProgress));
    }
}
