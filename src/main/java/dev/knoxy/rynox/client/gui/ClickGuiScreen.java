package dev.knoxy.rynox.client.gui;

import dev.knoxy.rynox.client.gui.clickgui.components.*;
import dev.knoxy.rynox.client.gui.clickgui.frame.Frame;
import dev.knoxy.rynox.client.module.Category;
import dev.knoxy.rynox.client.module.Module;
import dev.knoxy.rynox.client.module.ModuleManager;
import dev.knoxy.rynox.client.module.setting.*;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ClickGuiScreen extends Screen {

    private static ClickGuiScreen INSTANCE;

    private final List<Frame> frames = new ArrayList<>();

    private ClickGuiScreen() {
        super(Text.of("ClickGUI"));
        setupFrames();
    }

    public static ClickGuiScreen getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClickGuiScreen();
        }
        return INSTANCE;
    }

    private void setupFrames() {
        ModuleManager moduleManager = ModuleManager.getInstance();
        int x = 10;
        for (Category category : Category.values()) {
            List<Module> modules = moduleManager.getModulesInCategory(category);
            if (modules.isEmpty()) {
                continue;
            }

            Frame frame = new Frame(category, x, 10, 100, 14);
            for (Module module : modules) {
                Button button = new Button(module, 0, 0, 100, 12);
                frame.addComponent(button);
                for (Setting<?> setting : module.getSettings()) {
                    if (setting instanceof BooleanSetting) {
                        BooleanSetting booleanSetting = (BooleanSetting) setting;
                        button.addComponent(new Checkbox(booleanSetting.getName(), booleanSetting::getValue, booleanSetting::setValue, 0, 0, 100, 12));
                    } else if (setting instanceof DoubleSetting) {
                        DoubleSetting doubleSetting = (DoubleSetting) setting;
                        button.addComponent(new Slider(doubleSetting.getName(), doubleSetting::getValue, doubleSetting::setValue, doubleSetting.getMin(), doubleSetting.getMax(), 0, 0, 100, 12));
                    } else if (setting instanceof RangeSetting) {
                        RangeSetting rangeSetting = (RangeSetting) setting;
                        button.addComponent(new RangeSlider(rangeSetting.getName(), () -> rangeSetting.getValue().getMin(), (val) -> rangeSetting.getValue().setMin(val), () -> rangeSetting.getValue().getMax(), (val) -> rangeSetting.getValue().setMax(val), rangeSetting.getMin(), rangeSetting.getMax(), 0, 0, 100, 12));
                    } else if (setting instanceof EnumSetting) {
                        EnumSetting enumSetting = (EnumSetting) setting;
                        button.addComponent(new Dropdown(enumSetting.getName(), enumSetting::getValue, enumSetting::setValue, enumSetting.getValues(), 0, 0, 100, 12));
                    } else if (setting instanceof KeybindSetting) {
                        KeybindSetting keybindSetting = (KeybindSetting) setting;
                        button.addComponent(new Keybind(keybindSetting, 0, 0, 100, 12));
                    }
                }
            }
            frames.add(frame);
            x += 110;
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        for (Frame frame : frames) {
            frame.render(context, mouseX, mouseY, delta);
        }
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (Frame frame : frames) {
            frame.mouseClicked(mouseX, mouseY, button);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for (Frame frame : frames) {
            frame.mouseReleased(mouseX, mouseY, button);
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        for (Frame frame : frames) {
            frame.keyPressed(keyCode, scanCode, modifiers);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
