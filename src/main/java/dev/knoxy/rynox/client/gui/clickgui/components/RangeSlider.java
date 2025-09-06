package dev.knoxy.rynox.client.gui.clickgui.components;

import dev.knoxy.rynox.client.gui.clickgui.api.Component;
import dev.knoxy.rynox.client.gui.util.AnimationUtil;
import dev.knoxy.rynox.client.gui.util.RenderUtil;
import dev.knoxy.rynox.client.gui.FontManager;
import dev.knoxy.rynox.client.gui.clickgui.theme.Colors;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class RangeSlider extends Component {

    private final String name;
    private final Supplier<Double> getterMin;
    private final Consumer<Double> setterMin;
    private final Supplier<Double> getterMax;
    private final Consumer<Double> setterMax;
    private final double min;
    private final double max;
    private boolean draggingMin;
    private boolean draggingMax;
    private double animatedValueMin;
    private double animatedValueMax;

    public RangeSlider(String name, Supplier<Double> getterMin, Consumer<Double> setterMin, Supplier<Double> getterMax, Consumer<Double> setterMax, double min, double max, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.name = name;
        this.getterMin = getterMin;
        this.setterMin = setterMin;
        this.getterMax = getterMax;
        this.setterMax = setterMax;
        this.min = min;
        this.max = max;
        this.draggingMin = false;
        this.draggingMax = false;
        this.animatedValueMin = getterMin.get();
        this.animatedValueMax = getterMax.get();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        double valueMin = getterMin.get();
        double valueMax = getterMax.get();
        double diff = max - min;

        if (draggingMin) {
            double newValue = min + (Math.max(0, Math.min(width, mouseX - x)) / (double)width) * diff;
            if (newValue < valueMax) {
                setterMin.accept(newValue);
            }
        }
        if (draggingMax) {
            double newValue = min + (Math.max(0, Math.min(width, mouseX - x)) / (double)width) * diff;
            if (newValue > valueMin) {
                setterMax.accept(newValue);
            }
        }

        valueMin = getterMin.get(); // update after dragging
        valueMax = getterMax.get(); // update after dragging

        animatedValueMin = AnimationUtil.lerp(animatedValueMin, valueMin, delta * 10);
        animatedValueMax = AnimationUtil.lerp(animatedValueMax, valueMax, delta * 10);

        int sliderMinX = (int) (((animatedValueMin - min) / diff) * width);
        int sliderMaxX = (int) (((animatedValueMax - min) / diff) * width);

        RenderUtil.drawRoundedRect(context, x, y, width, height, 2, isMouseOver(mouseX, mouseY) ? Colors.BUTTON_HOVER.getRGB() : Colors.BUTTON.getRGB());
        RenderUtil.drawRoundedRect(context, x + sliderMinX, y, sliderMaxX - sliderMinX, height, 2, Colors.ACCENT.getRGB());

        String valueStr = String.format("%.2f-%.2f", valueMin, valueMax);
        FontManager.PRODUCT_SANS.drawWithShadow(context.getMatrices(), name, x + 4, y + (height - FontManager.PRODUCT_SANS.fontHeight) / 2, Colors.TEXT.getRGB());
        FontManager.PRODUCT_SANS.drawWithShadow(context.getMatrices(), valueStr, x + width - FontManager.PRODUCT_SANS.getWidth(valueStr) - 4, y + (height - FontManager.PRODUCT_SANS.fontHeight) / 2, Colors.TEXT.getRGB());

        RenderUtil.drawCircle(context, x + sliderMinX, y + height / 2.0, height / 2.5, Colors.ACCENT.darker().getRGB());
        RenderUtil.drawCircle(context, x + sliderMaxX, y + height / 2.0, height / 2.5, Colors.ACCENT.darker().getRGB());
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            double valueMin = getterMin.get();
            double valueMax = getterMax.get();
            double diff = max - min;

            double clickedValue = min + (Math.max(0, Math.min(width, mouseX - x)) / width) * diff;

            if (Math.abs(clickedValue - valueMin) < Math.abs(clickedValue - valueMax)) {
                draggingMin = true;
            } else {
                draggingMax = true;
            }
        }
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            draggingMin = false;
            draggingMax = false;
        }
    }
}
