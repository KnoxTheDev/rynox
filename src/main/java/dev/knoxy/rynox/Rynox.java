package dev.knoxy.rynox;

import dev.knoxy.rynox.ui.clickgui.ClickGui;
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
    private static KeyBinding clickGuiKeybind;

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing Rynox...");

        clickGuiKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.rynox.clickgui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.rynox.main"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (clickGuiKeybind.wasPressed()) {
                client.setScreen(ClickGui.getINSTANCE());
            }
        });
    }
}
