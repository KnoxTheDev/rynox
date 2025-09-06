package dev.knoxy.rynox.client.gui.clickgui.components;

import dev.knoxy.rynox.client.gui.clickgui.api.Component;
import dev.knoxy.rynox.client.module.Module;
import net.minecraft.client.MinecraftClient;
import dev.knoxy.rynox.client.gui.clickgui.theme.Colors;
import net.minecraft.client.gui.DrawContext;

import java.awt.Color;

public class Button extends Component {

    private final Module module;

    public Button(Module module, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.module = module;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        Color color = module.isEnabled() ? Colors.ACCENT : Colors.BUTTON;
        if (isMouseOver(mouseX, mouseY)) {
            color = module.isEnabled() ? Colors.ACCENT.darker() : Colors.BUTTON_HOVER;
        }
        context.fill(x, y, x + width, y + height, color.getRGB());
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, module.getName(), x + 2, y + 2, Colors.TEXT.getRGB());
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            module.toggle();
        }
    }
}
