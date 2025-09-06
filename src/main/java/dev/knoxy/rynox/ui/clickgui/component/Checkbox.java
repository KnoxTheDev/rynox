package dev.knoxy.rynox.ui.clickgui.component;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class Checkbox extends Component {
    private boolean checked;
    private final String label;

    public Checkbox(String label, int x, int y, int size) {
        super(x, y, size, size); // Checkboxes are typically square
        this.label = label;
        this.checked = false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Draw the main box
        context.fill(x, y, x + width, y + height, 0xFF303030);

        // Draw the checkmark if checked
        if (this.checked) {
            context.fill(x + 2, y + 2, x + width - 2, y + height - 2, 0xFFFFFFFF);
        }

        // Draw the label text
        var textRenderer = MinecraftClient.getInstance().textRenderer;
        context.drawTextWithShadow(textRenderer, this.label, x + width + 5, y + (height - 8) / 2, 0xFFFFFFFF);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        // Make the clickable area slightly larger than just the box for better usability
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            this.checked = !this.checked;
            System.out.println("Toggled checkbox '" + this.label + "' to: " + this.checked);
        }
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {}

    @Override
    public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {}
}
