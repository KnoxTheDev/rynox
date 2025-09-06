package dev.knoxy.rynox.client.gui.clickgui.components;

import dev.knoxy.rynox.client.gui.clickgui.api.Component;
import dev.knoxy.rynox.client.gui.util.AnimationUtil;
import dev.knoxy.rynox.client.gui.util.RenderUtil;
import dev.knoxy.rynox.client.gui.FontManager;
import net.minecraft.client.MinecraftClient;
import dev.knoxy.rynox.client.gui.clickgui.theme.Colors;
import net.minecraft.client.gui.DrawContext;

import java.awt.Color;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Slider extends Component {

    private final String name;
    private final Supplier<Double> getter;
    private final Consumer<Double> setter;
    private final double min;
    private final double max;
    private boolean dragging;
    private double animatedValue;

    public Slider(String name, Supplier<Double> getter, Consumer<Double> setter, double min, double max, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.name = name;
        this.getter = getter;
        this.setter = setter;
        this.min = min;
        this.max = max;
        this.dragging = false;
        this.animatedValue = getter.get();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (dragging) {
            double diff = max - min;
            double value = min + (Math.max(0, Math.min(width, mouseX - x)) / (double)width) * diff;
            setter.accept(value);
        }

        double value = getter.get();
        animatedValue = AnimationUtil.lerp(animatedValue, value, delta * 10);

        double diff = max - min;
        int sliderWidth = (int) (((animatedValue - min) / diff) * width);

        RenderUtil.drawRoundedRect(context, x, y, width, height, 2, isMouseOver(mouseX, mouseY) ? Colors.BUTTON_HOVER.getRGB() : Colors.BUTTON.getRGB());
        RenderUtil.drawRoundedRect(context, x, y, sliderWidth, height, 2, Colors.ACCENT.getRGB());

        String valueStr = String.format("%.2f", value);
        FontManager.PRODUCT_SANS.drawWithShadow(context.getMatrices(), name, x + 4, y + (height - FontManager.PRODUCT_SANS.fontHeight) / 2, Colors.TEXT.getRGB());
        FontManager.PRODUCT_SANS.drawWithShadow(context.getMatrices(), valueStr, x + width - FontManager.PRODUCT_SANS.getWidth(valueStr) - 4, y + (height - FontManager.PRODUCT_SANS.fontHeight) / 2, Colors.TEXT.getRGB());

        RenderUtil.drawCircle(context, x + sliderWidth, y + height / 2.0, height / 2.5, Colors.ACCENT.darker().getRGB());
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            dragging = true;
        }
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            dragging = false;
        }
    }
}
