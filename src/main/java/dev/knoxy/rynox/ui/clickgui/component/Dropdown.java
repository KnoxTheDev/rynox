package dev.knoxy.rynox.ui.clickgui.component;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import java.util.List;

public class Dropdown extends Component {
    private final String label;
    private final List<String> options;
    private String selected;
    private boolean expanded;

    public Dropdown(String label, List<String> options, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.label = label;
        this.options = options;
        if (options != null && !options.isEmpty()) {
            this.selected = options.get(0);
        }
        this.expanded = false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        var textRenderer = MinecraftClient.getInstance().textRenderer;

        // Draw label
        context.drawTextWithShadow(textRenderer, label, x, y - 12, 0xFFFFFFFF);

        // Draw the main box that shows the selected item
        context.fill(x, y, x + width, y + height, 0xFF303030);
        context.drawTextWithShadow(textRenderer, selected, x + 5, y + (height - 8) / 2, 0xFFFFFFFF);

        // Draw arrow indicator
        String arrow = expanded ? "^" : "v";
        context.drawTextWithShadow(textRenderer, arrow, x + width - 10, y + (height - 8) / 2, 0xFFFFFFFF);

        // If expanded, draw the list of options
        if (expanded) {
            int yOffset = y + height;
            for (String option : options) {
                boolean hovered = mouseX >= x && mouseX <= x + width && mouseY >= yOffset && mouseY <= yOffset + height;
                int color = hovered ? 0xFF505050 : 0xFF303030; // Highlight on hover
                context.fill(x, yOffset, x + width, yOffset + height, color);
                context.drawTextWithShadow(textRenderer, option, x + 5, yOffset + (height - 8) / 2, 0xFFFFFFFF);
                yOffset += height;
            }
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            // Clicking the main box toggles the dropdown
            if (isMouseOver(mouseX, mouseY)) {
                this.expanded = !this.expanded;
                return;
            }

            // If expanded, check for clicks on the options
            if (this.expanded) {
                int yOffset = y + height;
                for (String option : options) {
                    if (mouseX >= x && mouseX <= x + width && mouseY >= yOffset && mouseY <= yOffset + height) {
                        this.selected = option;
                        this.expanded = false; // Collapse after selection
                        System.out.println("Selected '" + option + "' from dropdown '" + this.label + "'");
                        return;
                    }
                    yOffset += height;
                }
            }
        }
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {}

    @Override
    public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {}
}
