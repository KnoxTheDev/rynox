package dev.knoxy.rynox.client.gui.clickgui.components;

import dev.knoxy.rynox.client.gui.clickgui.api.Component;
import net.minecraft.client.MinecraftClient;
import dev.knoxy.rynox.client.gui.clickgui.theme.Colors;
import net.minecraft.client.gui.DrawContext;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Checkbox extends Component {

    private final String name;
    private final Supplier<Boolean> getter;
    private final Consumer<Boolean> setter;

    public Checkbox(String name, Supplier<Boolean> getter, Consumer<Boolean> setter, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.name = name;
        this.getter = getter;
        this.setter = setter;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(x, y, x + width, y + height, Colors.CHECKBOX.getRGB());
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, name, x + 2, y + 2, Colors.TEXT.getRGB());
        if (getter.get()) {
            context.fill(x + width - 12, y + 2, x + width - 2, y + height - 2, Colors.ACCENT.getRGB());
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            setter.accept(!getter.get());
        }
    }
}
