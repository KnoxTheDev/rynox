package dev.knoxy.rynox.client.gui.clickgui.components;

import dev.knoxy.rynox.client.gui.clickgui.api.Component;
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
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        double valueMin = getterMin.get();
        double valueMax = getterMax.get();
        double diff = max - min;

        if (draggingMin) {
            double newValue = min + (Math.max(0, Math.min(width, mouseX - x)) / width) * diff;
            if (newValue < valueMax) {
                setterMin.accept(newValue);
            }
        }
        if (draggingMax) {
            double newValue = min + (Math.max(0, Math.min(width, mouseX - x)) / width) * diff;
            if (newValue > valueMin) {
                setterMax.accept(newValue);
            }
        }

        valueMin = getterMin.get(); // update after dragging
        valueMax = getterMax.get(); // update after dragging

        int sliderMinX = (int) ((valueMin - min) / diff * width);
        int sliderMaxX = (int) ((valueMax - min) / diff * width);

        context.fill(x, y, x + width, y + height, Colors.SLIDER.getRGB());
        context.fill(x + sliderMinX, y, x + sliderMaxX, y + height, Colors.ACCENT.getRGB());
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, name + ": " + String.format("%.2f", valueMin) + " - " + String.format("%.2f", valueMax), x + 2, y + 2, Colors.TEXT.getRGB());
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
