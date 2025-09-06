package dev.knoxy.rynox.client.gui.clickgui.components;

import dev.knoxy.rynox.client.gui.clickgui.api.Component;
import dev.knoxy.rynox.client.gui.clickgui.util.RenderUtil;
import net.minecraft.client.MinecraftClient;
import dev.knoxy.rynox.client.gui.clickgui.theme.Colors;
import net.minecraft.client.gui.DrawContext;

import java.awt.Color;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Dropdown<T extends Enum<T>> extends Component {

    private final String name;
    private final Supplier<T> getter;
    private final Consumer<T> setter;
    private final List<T> values;
    private boolean open;
    private float hoverAnimation;
    private float openAnimation;

    public Dropdown(String name, Supplier<T> getter, Consumer<T> setter, List<T> values, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.name = name;
        this.getter = getter;
        this.setter = setter;
        this.values = values;
        this.open = false;
        this.hoverAnimation = 0.0f;
        this.openAnimation = 0.0f;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (isMouseOver(mouseX, mouseY) || open) {
            hoverAnimation = Math.min(1.0f, hoverAnimation + delta * 5.0f);
        } else {
            hoverAnimation = Math.max(0.0f, hoverAnimation - delta * 5.0f);
        }

        if (open) {
            openAnimation = Math.min(1.0f, openAnimation + delta * 5.0f);
        } else {
            openAnimation = Math.max(0.0f, openAnimation - delta * 5.0f);
        }

        Color color = new Color(Colors.DROPDOWN);
        Color hoverColor = new Color(Colors.BUTTON_HOVER);

        int blendedColor = new Color(
                (int) (color.getRed() * (1 - hoverAnimation) + hoverColor.getRed() * hoverAnimation),
                (int) (color.getGreen() * (1 - hoverAnimation) + hoverColor.getGreen() * hoverAnimation),
                (int) (color.getBlue() * (1 - hoverAnimation) + hoverColor.getBlue() * hoverAnimation)
        ).getRGB();

        RenderUtil.drawRoundedRect(context, x, y, width, height, 3, blendedColor);
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, name, x + 2, y + 2, Colors.TEXT);
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, getter.get().name(), x + width - MinecraftClient.getInstance().textRenderer.getWidth(getter.get().name()) - 2, y + 2, Colors.TEXT);

        if (openAnimation > 0) {
            int dropdownHeight = height * values.size();
            int currentHeight = (int) (dropdownHeight * openAnimation);

            int offsetY = height;
            for (T value : values) {
                if (offsetY < currentHeight + height) {
                    RenderUtil.drawRoundedRect(context, x, y + offsetY, width, height, 3, blendedColor);
                    context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, value.name(), x + 2, y + offsetY + 2, Colors.TEXT);
                }
                offsetY += height;
            }
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            open = !open;
        } else if (open) {
            int offsetY = height;
            for (T value : values) {
                if (mouseX >= x && mouseX <= x + width && mouseY >= y + offsetY && mouseY <= y + offsetY + height) {
                    setter.accept(value);
                    open = false;
                    break;
                }
                offsetY += height;
            }
        }
    }

    @Override
    public int getHeight() {
        if (openAnimation > 0) {
            return height + (int) (height * values.size() * openAnimation);
        }
        return height;
    }
}
