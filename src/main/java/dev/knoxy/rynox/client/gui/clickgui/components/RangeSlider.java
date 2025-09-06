package dev.knoxy.rynox.client.gui.clickgui.components;

import dev.knoxy.rynox.client.gui.clickgui.api.Component;
import dev.knoxy.rynox.client.gui.clickgui.util.RenderUtil;
import net.minecraft.client.MinecraftClient;
import dev.knoxy.rynox.client.gui.clickgui.theme.Colors;
import net.minecraft.client.gui.DrawContext;

import java.awt.Color;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class RangeSlider extends Component {

    private final String name;
    private final Supplier<Double> minGetter;
    private final Consumer<Double> minSetter;
    private final Supplier<Double> maxGetter;
    private final Consumer<Double> maxSetter;
    private final double min;
    private final double max;
    private boolean draggingMin;
    private boolean draggingMax;
    private float hoverAnimation;

    public RangeSlider(String name, Supplier<Double> minGetter, Consumer<Double> minSetter, Supplier<Double> maxGetter, Consumer<Double> maxSetter, double min, double max, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.name = name;
        this.minGetter = minGetter;
        this.minSetter = minSetter;
        this.maxGetter = maxGetter;
        this.maxSetter = maxSetter;
        this.min = min;
        this.max = max;
        this.draggingMin = false;
        this.draggingMax = false;
        this.hoverAnimation = 0.0f;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (draggingMin) {
            double diff = max - min;
            double val = min + (Math.max(0, Math.min(width, mouseX - x)) / width) * diff;
            if (val < maxGetter.get()) {
                minSetter.accept(val);
            }
        }
        if (draggingMax) {
            double diff = max - min;
            double val = min + (Math.max(0, Math.min(width, mouseX - x)) / width) * diff;
            if (val > minGetter.get()) {
                maxSetter.accept(val);
            }
        }

        if (isMouseOver(mouseX, mouseY)) {
            hoverAnimation = Math.min(1.0f, hoverAnimation + delta * 5.0f);
        } else {
            hoverAnimation = Math.max(0.0f, hoverAnimation - delta * 5.0f);
        }

        Color color = new Color(Colors.SLIDER);
        Color hoverColor = new Color(Colors.BUTTON_HOVER);

        int blendedColor = new Color(
                (int) (color.getRed() * (1 - hoverAnimation) + hoverColor.getRed() * hoverAnimation),
                (int) (color.getGreen() * (1 - hoverAnimation) + hoverColor.getGreen() * hoverAnimation),
                (int) (color.getBlue() * (1 - hoverAnimation) + hoverColor.getBlue() * hoverAnimation)
        ).getRGB();

        double minValue = minGetter.get();
        double maxValue = maxGetter.get();
        double diff = max - min;
        double minSliderWidth = ((minValue - min) / diff) * width;
        double maxSliderWidth = ((maxValue - min) / diff) * width;

        RenderUtil.drawRoundedRect(context, x, y, width, height, 3, blendedColor);
        RenderUtil.drawRoundedRect(context, x + minSliderWidth, y, maxSliderWidth - minSliderWidth, height, 3, Colors.ACCENT);

        RenderUtil.drawRoundedRect(context, x + minSliderWidth - 2, y - 1, 4, height + 2, 2, Color.WHITE.getRGB());
        RenderUtil.drawRoundedRect(context, x + maxSliderWidth - 2, y - 1, 4, height + 2, 2, Color.WHITE.getRGB());

        String valueString = String.format("%.2f - %.2f", minValue, maxValue);
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, name, x + 2, y + 2, Colors.TEXT);
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, valueString, x + width - MinecraftClient.getInstance().textRenderer.getWidth(valueString) - 2, y + 2, Colors.TEXT);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            double minValue = minGetter.get();
            double maxValue = maxGetter.get();
            double diff = max - min;
            double minSliderWidth = ((minValue - min) / diff) * width;
            double maxSliderWidth = ((maxValue - min) / diff) * width;

            if (Math.abs(mouseX - (x + minSliderWidth)) < Math.abs(mouseX - (x + maxSliderWidth))) {
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
