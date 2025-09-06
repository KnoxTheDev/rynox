package dev.knoxy.rynox.client.gui.clickgui.components;

import dev.knoxy.rynox.client.gui.clickgui.api.Component;
import dev.knoxy.rynox.client.gui.util.AnimationUtil;
import dev.knoxy.rynox.client.gui.util.RenderUtil;
import dev.knoxy.rynox.client.gui.FontManager;
import net.minecraft.client.MinecraftClient;
import dev.knoxy.rynox.client.gui.clickgui.theme.Colors;
import net.minecraft.client.gui.DrawContext;

import java.awt.Color;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Checkbox extends Component {

    private final String name;
    private final Supplier<Boolean> getter;
    private final Consumer<Boolean> setter;
    private float checkProgress = 0;

    public Checkbox(String name, Supplier<Boolean> getter, Consumer<Boolean> setter, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.name = name;
        this.getter = getter;
        this.setter = setter;
        this.checkProgress = getter.get() ? 1 : 0;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (getter.get()) {
            checkProgress = Math.min(1, checkProgress + delta / 4);
        } else {
            checkProgress = Math.max(0, checkProgress - delta / 4);
        }

        RenderUtil.drawRoundedRect(context, x, y, width, height, 2, isMouseOver(mouseX, mouseY) ? Colors.BUTTON_HOVER.getRGB() : Colors.BUTTON.getRGB());
        FontManager.PRODUCT_SANS.drawWithShadow(context.getMatrices(), name, x + 4, y + (height - FontManager.PRODUCT_SANS.fontHeight) / 2, Colors.TEXT.getRGB());

        int boxX = x + width - 14;
        int boxY = y + 2;
        int boxSize = height - 4;

        RenderUtil.drawRoundedRect(context, boxX, boxY, boxSize, boxSize, 2, Colors.CHECKBOX.getRGB());
        if (checkProgress > 0) {
            float easedProgress = AnimationUtil.easeInOut(checkProgress);
            float animatedSize = (boxSize - 2) * easedProgress;
            float offset = (boxSize - 2 - animatedSize) / 2;
            RenderUtil.drawRoundedRect(context, boxX + 1 + offset, boxY + 1 + offset, animatedSize, animatedSize, 2, Colors.ACCENT.getRGB());
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            setter.accept(!getter.get());
        }
    }
}
