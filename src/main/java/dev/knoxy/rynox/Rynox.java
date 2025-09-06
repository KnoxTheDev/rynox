package dev.knoxy.rynox;

import dev.knoxy.rynox.client.gui.ClickGuiScreen;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import dev.knoxy.rynox.client.gui.FontManager;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Rynox implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("rynox");

    public static final KeyBinding TOGGLE_GUI_KEYBINDING = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.rynox.toggle_gui",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            "key.categories.rynox"
    ));

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Rynox...");

        FontManager.init();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (TOGGLE_GUI_KEYBINDING.wasPressed()) {
                client.setScreen(ClickGuiScreen.getInstance());
            }
        });
    }
}
