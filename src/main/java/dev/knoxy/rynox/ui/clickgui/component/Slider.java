package dev.knoxy.rynox.ui.clickgui.component;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class Slider extends Component {
    private double value;
    private final String label;
    private boolean dragging = false;

    public Slider(String label, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.label = label;
        this.value = 0.5; // Default to middle
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Draw the slider track
        context.fill(x, y + height / 2 - 1, x + width, y + height / 2 + 1, 0xFF202020);

        // Draw the handle
        int handleWidth = 4;
        int handleX = (int) (x + (width - handleWidth) * this.value);
        context.fill(handleX, y, handleX + handleWidth, y + height, 0xFF707070);

        // Draw the label and value
        var textRenderer = MinecraftClient.getInstance().textRenderer;
        String displayText = String.format("%s: %.2f", label, this.value);
        context.drawTextWithShadow(textRenderer, displayText, x, y - 12, 0xFFFFFFFF);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            this.dragging = true;
            updateValue(mouseX);
        }
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            this.dragging = false;
        }
    }

    @Override
    public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.dragging) {
            updateValue(mouseX);
        }
    }

    private void updateValue(double mouseX) {
        double rawValue = (mouseX - x) / width;
        this.value = Math.max(0, Math.min(1, rawValue)); // Clamp between 0 and 1
    }
}
