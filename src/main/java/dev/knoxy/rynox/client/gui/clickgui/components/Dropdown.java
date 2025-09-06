package dev.knoxy.rynox.client.gui.clickgui.components;

import dev.knoxy.rynox.client.gui.clickgui.api.Component;
import dev.knoxy.rynox.client.gui.util.AnimationUtil;
import dev.knoxy.rynox.client.gui.util.RenderUtil;
import dev.knoxy.rynox.client.gui.FontManager;
import net.minecraft.client.MinecraftClient;
import dev.knoxy.rynox.client.gui.clickgui.theme.Colors;
import net.minecraft.client.gui.DrawContext;

import java.awt.Color;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Dropdown extends Component {

    private final String name;
    private final Supplier<String> getter;
    private final Consumer<String> setter;
    private final List<String> values;
    private boolean open;
    private float openProgress = 0;

    public Dropdown(String name, Supplier<String> getter, Consumer<String> setter, List<String> values, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.name = name;
        this.getter = getter;
        this.setter = setter;
        this.values = values;
        this.open = false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (open) {
            openProgress = Math.min(1, openProgress + delta / 4);
        } else {
            openProgress = Math.max(0, openProgress - delta / 4);
        }

        RenderUtil.drawRoundedRect(context, x, y, width, height, 2, isMouseOver(mouseX, mouseY) ? Colors.BUTTON_HOVER.getRGB() : Colors.BUTTON.getRGB());
        FontManager.PRODUCT_SANS.drawWithShadow(context.getMatrices(), name, x + 4, y + (height - FontManager.PRODUCT_SANS.fontHeight) / 2, Colors.TEXT.getRGB());

        String valueStr = getter.get();
        FontManager.PRODUCT_SANS.drawWithShadow(context.getMatrices(), valueStr, x + width - FontManager.PRODUCT_SANS.getWidth(valueStr) - 12, y + (height - FontManager.PRODUCT_SANS.fontHeight) / 2, Colors.TEXT.getRGB());

        // Draw arrow
        String arrow = open ? "v" : ">";
        FontManager.PRODUCT_SANS.drawWithShadow(context.getMatrices(), arrow, x + width - 8, y + (height - FontManager.PRODUCT_SANS.fontHeight) / 2, Colors.TEXT.getRGB());

        if (openProgress > 0) {
            float easedProgress = AnimationUtil.easeInOut(openProgress);
            int animatedHeight = (int) (height * values.size() * easedProgress);

            int offsetY = height;
            for (String value : values) {
                Color textColor = new Color(Colors.TEXT.getRed(), Colors.TEXT.getGreen(), Colors.TEXT.getBlue(), (int) (255 * easedProgress));
                RenderUtil.drawRoundedRect(context, x, y + offsetY, width, height, 2, isMouseOver(mouseX, mouseY) ? Colors.BUTTON_HOVER.getRGB() : Colors.BUTTON.getRGB());
                FontManager.PRODUCT_SANS.drawWithShadow(context.getMatrices(), value, x + 4, y + offsetY + (height - FontManager.PRODUCT_SANS.fontHeight) / 2, textColor.getRGB());
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
            for (String value : values) {
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
        return height + (int) (height * values.size() * openProgress);
    }
}
