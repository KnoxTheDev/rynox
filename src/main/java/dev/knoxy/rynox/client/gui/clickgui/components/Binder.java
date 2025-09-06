package dev.knoxy.rynox.client.gui.clickgui.components;

import dev.knoxy.rynox.client.gui.clickgui.api.Component;
import dev.knoxy.rynox.client.gui.clickgui.util.RenderUtil;
import dev.knoxy.rynox.client.module.Module;
import net.minecraft.client.MinecraftClient;
import dev.knoxy.rynox.client.gui.clickgui.theme.Colors;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.glfw.GLFW;

import java.awt.Color;

public class Binder extends Component {

    private final Module module;
    private boolean binding;
    private float hoverAnimation;

    public Binder(Module module, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.module = module;
        this.binding = false;
        this.hoverAnimation = 0.0f;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (isMouseOver(mouseX, mouseY)) {
            hoverAnimation = Math.min(1.0f, hoverAnimation + delta * 5.0f);
        } else {
            hoverAnimation = Math.max(0.0f, hoverAnimation - delta * 5.0f);
        }

        Color color = new Color(Colors.BUTTON);
        Color hoverColor = new Color(Colors.BUTTON_HOVER);

        int blendedColor = new Color(
                (int) (color.getRed() * (1 - hoverAnimation) + hoverColor.getRed() * hoverAnimation),
                (int) (color.getGreen() * (1 - hoverAnimation) + hoverColor.getGreen() * hoverAnimation),
                (int) (color.getBlue() * (1 - hoverAnimation) + hoverColor.getBlue() * hoverAnimation)
        ).getRGB();

        RenderUtil.drawRoundedRect(context, x, y, width, height, 3, blendedColor);
        String text = binding ? "Press a key..." : "Bind: " + (module.getKey() == -1 ? "None" : GLFW.glfwGetKeyName(module.getKey(), 0));
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, text, x + 2, y + 2, Colors.TEXT);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            binding = !binding;
        }
    }

    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        if (binding) {
            if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
                module.setKey(-1);
            } else {
                module.setKey(keyCode);
            }
            binding = false;
        }
    }
}
