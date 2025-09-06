package dev.knoxy.rynox.client.gui.clickgui.api;

import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;
import java.util.List;

public abstract class Container extends Component {

    protected final List<Component> components = new ArrayList<>();

    public Container(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void addComponent(Component component) {
        components.add(component);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        for (Component component : components) {
            component.render(context, mouseX, mouseY, delta);
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        for (Component component : components) {
            component.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        for (Component component : components) {
            component.mouseReleased(mouseX, mouseY, button);
        }
    }

    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        for (Component component : components) {
            component.keyPressed(keyCode, scanCode, modifiers);
        }
    }
}
