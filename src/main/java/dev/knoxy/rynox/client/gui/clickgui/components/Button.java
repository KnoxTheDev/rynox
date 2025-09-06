package dev.knoxy.rynox.client.gui.clickgui.components;

import dev.knoxy.rynox.client.gui.clickgui.api.Component;
import dev.knoxy.rynox.client.gui.clickgui.util.RenderUtil;
import dev.knoxy.rynox.client.module.Module;
import net.minecraft.client.MinecraftClient;
import dev.knoxy.rynox.client.gui.clickgui.theme.Colors;
import net.minecraft.client.gui.DrawContext;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Button extends Component {

    private final Module module;
    private final List<Component> settings = new ArrayList<>();
    private boolean open;
    private float hoverAnimation;
    private float openAnimation;

    public Button(Module module, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.module = module;
        this.open = false;
        this.hoverAnimation = 0.0f;
        this.openAnimation = 0.0f;
    }

    public void addSetting(Component component) {
        settings.add(component);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (isMouseOver(mouseX, mouseY, height)) {
            hoverAnimation = Math.min(1.0f, hoverAnimation + delta * 5.0f);
        } else {
            hoverAnimation = Math.max(0.0f, hoverAnimation - delta * 5.0f);
        }

        if (open) {
            openAnimation = Math.min(1.0f, openAnimation + delta * 5.0f);
        } else {
            openAnimation = Math.max(0.0f, openAnimation - delta * 5.0f);
        }

        Color color = module.isEnabled() ? new Color(Colors.ACCENT) : new Color(Colors.BUTTON);
        Color hoverColor = module.isEnabled() ? new Color(Colors.ACCENT).darker() : new Color(Colors.BUTTON_HOVER);

        int blendedColor = new Color(
                (int) (color.getRed() * (1 - hoverAnimation) + hoverColor.getRed() * hoverAnimation),
                (int) (color.getGreen() * (1 - hoverAnimation) + hoverColor.getGreen() * hoverAnimation),
                (int) (color.getBlue() * (1 - hoverAnimation) + hoverColor.getBlue() * hoverAnimation)
        ).getRGB();

        RenderUtil.drawRoundedRect(context, x, y, width, height, 3, blendedColor);
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, module.getName(), x + 2, y + 2, Colors.TEXT);

        if (openAnimation > 0) {
            int offsetY = height;
            for (Component setting : settings) {
                setting.setX(this.x);
                setting.setY(this.y + offsetY);
                setting.render(context, mouseX, mouseY, delta);
                offsetY += setting.getHeight();
            }
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY, height)) {
            if (button == 0) {
                module.toggle();
            } else if (button == 1) {
                open = !open;
            }
        }
        if (openAnimation > 0) {
            for (Component setting : settings) {
                setting.mouseClicked(mouseX, mouseY, button);
            }
        }
    }

    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        if (openAnimation > 0) {
            for (Component setting : settings) {
                setting.keyPressed(keyCode, scanCode, modifiers);
            }
        }
    }

    @Override
    public int getHeight() {
        if (openAnimation > 0) {
            int totalHeight = height;
            for (Component setting : settings) {
                totalHeight += setting.getHeight();
            }
            return (int) (height + (totalHeight - height) * openAnimation);
        }
        return height;
    }

    private boolean isMouseOver(double mouseX, double mouseY, int height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
