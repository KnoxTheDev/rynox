package dev.knoxy.rynox.client.gui.clickgui.components;

import dev.knoxy.rynox.client.gui.clickgui.api.Component;
import dev.knoxy.rynox.client.gui.util.RenderUtil;
import dev.knoxy.rynox.client.gui.FontManager;
import dev.knoxy.rynox.client.module.setting.KeybindSetting;
import net.minecraft.client.MinecraftClient;
import dev.knoxy.rynox.client.gui.clickgui.theme.Colors;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Keybind extends Component {

    private final KeybindSetting setting;
    private boolean listening;

    public Keybind(KeybindSetting setting, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setting = setting;
        this.listening = false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        RenderUtil.drawRoundedRect(context, x, y, width, height, 2, isMouseOver(mouseX, mouseY) ? Colors.BUTTON_HOVER.getRGB() : Colors.BUTTON.getRGB());

        String text = listening ? "Press a key..." : (setting.isNone() ? "None" : InputUtil.fromKeyCode(setting.getKey(), -1).getLocalizedText().getString());
        String name = setting.getName() + ": ";

        FontManager.PRODUCT_SANS.drawWithShadow(context.getMatrices(), name, x + 4, y + (height - FontManager.PRODUCT_SANS.fontHeight) / 2, Colors.TEXT.getRGB());
        FontManager.PRODUCT_SANS.drawWithShadow(context.getMatrices(), text, x + width - FontManager.PRODUCT_SANS.getWidth(text) - 4, y + (height - FontManager.PRODUCT_SANS.fontHeight) / 2, Colors.TEXT.getRGB());
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            listening = !listening;
        }
    }

    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        if (listening) {
            if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
                setting.setKey(GLFW.GLFW_KEY_UNKNOWN);
            } else {
                setting.setKey(keyCode);
            }
            listening = false;
        }
    }
}
