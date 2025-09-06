package dev.knoxy.rynox.client.module.setting;

import org.lwjgl.glfw.GLFW;

public class KeybindSetting extends Setting<Integer> {

    public KeybindSetting(String name, Integer value) {
        super(name, value);
    }

    public int getKey() {
        return getValue();
    }

    public void setKey(int key) {
        setValue(key);
    }

    public boolean isNone() {
        return getKey() == GLFW.GLFW_KEY_UNKNOWN;
    }
}
