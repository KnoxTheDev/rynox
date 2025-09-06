package dev.knoxy.rynox;

import dev.knoxy.rynox.ui.clickgui.ClickGUI;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Rynox implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("rynox");

    private static KeyBinding openGuiKeybind;
    private static ClickGUI clickGuiInstance;

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing Rynox...");

        // 1. Create and Register Keybinding
        openGuiKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.rynox.toggle_gui", // Use existing translation key
                InputUtil.Type.KEYSYM, // The type of the key, KEYSYM for keyboard
                GLFW.GLFW_KEY_RIGHT_SHIFT, // The default key
                "key.categories.rynox" // Use existing translation key
        ));

        // Create a single instance of our GUI to reuse
        clickGuiInstance = new ClickGUI();

        // 2. Register a Client Tick Event to listen for the key press
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // 3. Check if the key was pressed
            if (openGuiKeybind.wasPressed()) {
                // 4. Open the GUI
                // We check if the current screen is null to avoid opening the GUI over itself
                // or other screens. This can be adjusted based on desired behavior.
                if (client.currentScreen == null) {
                    client.setScreen(clickGuiInstance);
                }
            }
        });
    }
}
