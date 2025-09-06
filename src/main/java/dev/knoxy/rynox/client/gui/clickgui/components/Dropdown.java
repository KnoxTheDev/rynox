package dev.knoxy.rynox.client.gui.clickgui.components;

import dev.knoxy.rynox.client.gui.clickgui.api.Component;
import net.minecraft.client.MinecraftClient;
import dev.knoxy.rynox.client.gui.clickgui.theme.Colors;
import net.minecraft.client.gui.DrawContext;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Dropdown extends Component {

    private final String name;
    private final Supplier<String> getter;
    private final Consumer<String> setter;
    private final List<String> values;
    private boolean open;

    public Dropdown(String name, Supplier<String> getter, Consumer<String> setter, List<String> values, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.name = name;
        this.getter = getter;
        this.setter = setter;
        this.values = values;
        this.open = false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(x, y, x + width, y + height, Colors.DROPDOWN.getRGB());
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, name + ": " + getter.get(), x + 2, y + 2, Colors.TEXT.getRGB());

        if (open) {
            int offsetY = height;
            for (String value : values) {
                context.fill(x, y + offsetY, x + width, y + offsetY + height, Colors.BUTTON.getRGB());
                context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, value, x + 2, y + offsetY + 2, Colors.TEXT.getRGB());
                offsetY += height;
            }
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            open = !open;
        } else if (open) {
            int offsetY = height;
            for (String value : values) {
                if (mouseX >= x && mouseX <= x + width && mouseY >= y + offsetY && mouseY <= y + offsetY + height) {
                    setter.accept(value);
                    open = false;
                    break;
                }
                offsetY += height;
            }
        }
    }

    @Override
    public int getHeight() {
        if (open) {
            return height * (values.size() + 1);
        }
        return height;
    }
}
