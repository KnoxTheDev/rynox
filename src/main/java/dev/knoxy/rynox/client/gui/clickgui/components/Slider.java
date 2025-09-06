package dev.knoxy.rynox.client.gui.clickgui.components;

import dev.knoxy.rynox.client.gui.clickgui.api.Component;
import dev.knoxy.rynox.client.gui.clickgui.util.RenderUtil;
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
    private float hoverAnimation;

    public Slider(String name, Supplier<Double> getter, Consumer<Double> setter, double min, double max, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.name = name;
        this.getter = getter;
        this.setter = setter;
        this.min = min;
        this.max = max;
        this.dragging = false;
        this.hoverAnimation = 0.0f;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (dragging) {
            double diff = max - min;
            double val = min + (Math.max(0, Math.min(width, mouseX - x)) / width) * diff;
            setter.accept(val);
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

        double value = getter.get();
        double diff = max - min;
        double sliderWidth = ((value - min) / diff) * width;

        RenderUtil.drawRoundedRect(context, x, y, width, height, 3, blendedColor);
        RenderUtil.drawRoundedRect(context, x, y, sliderWidth, height, 3, Colors.ACCENT);

        RenderUtil.drawRoundedRect(context, x + sliderWidth - 2, y - 1, 4, height + 2, 2, Color.WHITE.getRGB());

        String valueString = String.format("%.2f", value);
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, name, x + 2, y + 2, Colors.TEXT);
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, valueString, x + width - MinecraftClient.getInstance().textRenderer.getWidth(valueString) - 2, y + 2, Colors.TEXT);
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
