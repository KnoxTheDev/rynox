package dev.knoxy.rynox.client.module;

import dev.knoxy.rynox.client.module.setting.Setting;
import java.util.ArrayList;
import java.util.List;

public class Module {

    private final String name;
    private final String description;
    private final Category category;
    private boolean enabled;
    private int key = -1;
    private final List<Setting<?>> settings = new ArrayList<>();

    public Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.enabled = false;
    }

    protected void addSetting(Setting<?> setting) {
        settings.add(setting);
    }

    public List<Setting<?>> getSettings() {
        return settings;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (this.enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public void toggle() {
        setEnabled(!this.enabled);
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void onEnable() {
        // Can be overridden by subclasses
    }

    public void onDisable() {
        // Can be overridden by subclasses
    }
}
