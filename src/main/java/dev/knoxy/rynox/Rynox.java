package dev.knoxy.rynox;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;
import dev.knoxy.rynox.gui.ClickGui;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Rynox implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("rynox");
    public static ClickGui CLICK_GUI;

    public static final KeyBinding TOGGLE_GUI_KEYBINDING = new KeyBinding(
            "key.rynox.toggle_gui",
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            "key.categories.rynox"
    );

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Rynox...");

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (TOGGLE_GUI_KEYBINDING.wasPressed()) {
                if (CLICK_GUI == null) {
                    CLICK_GUI = new ClickGui();
                }
                CLICK_GUI.toggle();
            }
        });
    }
}
