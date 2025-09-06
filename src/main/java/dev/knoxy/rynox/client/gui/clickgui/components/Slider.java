package dev.knoxy.rynox.client.gui.clickgui.components;

import dev.knoxy.rynox.client.gui.clickgui.api.Component;
import net.minecraft.client.MinecraftClient;
import dev.knoxy.rynox.client.gui.clickgui.theme.Colors;
import net.minecraft.client.gui.DrawContext;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Slider extends Component {

    private final String name;
    private final Supplier<Double> getter;
    private final Consumer<Double> setter;
    private final double min;
    private final double max;
    private boolean dragging;

    public Slider(String name, Supplier<Double> getter, Consumer<Double> setter, double min, double max, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.name = name;
        this.getter = getter;
        this.setter = setter;
        this.min = min;
        this.max = max;
        this.dragging = false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (dragging) {
            double diff = max - min;
            double value = min + (Math.max(0, Math.min(width, mouseX - x)) / width) * diff;
            setter.accept(value);
        }

        double value = getter.get();
        double diff = max - min;
        int sliderWidth = (int) ((value - min) / diff * width);

        context.fill(x, y, x + width, y + height, Colors.SLIDER.getRGB());
        context.fill(x, y, x + sliderWidth, y + height, Colors.ACCENT.getRGB());
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, name + ": " + String.format("%.2f", value), x + 2, y + 2, Colors.TEXT.getRGB());
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
