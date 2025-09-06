package dev.knoxy.rynox.ui.clickgui.component;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class Button extends Component {
    private String text;

    public Button(String text, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.text = text;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta, float alpha) {
        boolean hovered = isMouseOver(mouseX, mouseY);
        int baseColor = hovered ? 0x505050 : 0x404040;
        int textColor = 0xFFFFFF;

        int alphaComponent = ((int)(alpha * 255)) << 24;

        context.fill(x, y, x + width, y + height, alphaComponent | baseColor);

        var textRenderer = MinecraftClient.getInstance().textRenderer;
        context.drawCenteredTextWithShadow(textRenderer, this.text, x + width / 2, y + (height - 8) / 2, alphaComponent | textColor);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            // In a real client, this would trigger an action.
            // For now, we can print to console for feedback.
            System.out.println("Clicked button: " + this.text);
        }
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {}

    @Override
    public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {}
}
