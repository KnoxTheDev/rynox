package dev.knoxy.rynox.client.module.setting;

import java.util.List;

public class EnumSetting<T extends Enum<T>> extends Setting<T> {
    private final List<T> values;

    public EnumSetting(String name, T value, List<T> values) {
        super(name, value);
        this.values = values;
    }

    public List<T> getValues() {
        return values;
    }
}
