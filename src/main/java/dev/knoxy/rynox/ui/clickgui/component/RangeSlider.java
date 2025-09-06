package dev.knoxy.rynox.ui.clickgui.component;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class RangeSlider extends Component {
    private double minValue;
    private double maxValue;
    private final String label;
    private boolean draggingMin = false;
    private boolean draggingMax = false;

    public RangeSlider(String label, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.label = label;
        this.minValue = 0.2;
        this.maxValue = 0.8;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta, float alpha) {
        var textRenderer = MinecraftClient.getInstance().textRenderer;
        int alphaComponent = ((int)(alpha * 255)) << 24;

        // Draw label and values
        String displayText = String.format("%s: [%.2f, %.2f]", label, minValue, maxValue);
        context.drawTextWithShadow(textRenderer, displayText, x, y - 12, alphaComponent | 0xFFFFFF);

        // Draw the background track
        context.fill(x, y, x + width, y + height, alphaComponent | 0x202020);

        // Draw the selected range
        int rangeStartX = (int) (x + width * minValue);
        int rangeEndX = (int) (x + width * maxValue);
        context.fill(rangeStartX, y, rangeEndX, y + height, alphaComponent | 0x5E87B0);

        // Draw handles
        int handleWidth = 2;
        context.fill(rangeStartX - 1, y, rangeStartX + handleWidth - 1, y + height, alphaComponent | 0x707070);
        context.fill(rangeEndX - 1, y, rangeEndX + handleWidth - 1, y + height, alphaComponent | 0x707070);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            double mouseValue = (mouseX - x) / width;
            double distToMin = Math.abs(mouseValue - minValue);
            double distToMax = Math.abs(mouseValue - maxValue);

            if (distToMin < distToMax) {
                draggingMin = true;
                updateMinValue(mouseX);
            } else {
                draggingMax = true;
                updateMaxValue(mouseX);
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

    @Override
    public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (draggingMin) {
            updateMinValue(mouseX);
        } else if (draggingMax) {
            updateMaxValue(mouseX);
        }
    }

    private void updateMinValue(double mouseX) {
        double rawValue = (mouseX - x) / width;
        this.minValue = Math.max(0, Math.min(this.maxValue, rawValue));
    }

    private void updateMaxValue(double mouseX) {
        double rawValue = (mouseX - x) / width;
        this.maxValue = Math.max(this.minValue, Math.min(1, rawValue));
    }
}
