package dev.knoxy.rynox.client.module.setting;

import java.util.List;

public class EnumSetting extends Setting<String> {
    private final List<String> values;

    public EnumSetting(String name, String value, List<String> values) {
        super(name, value);
        this.values = values;
    }

    public List<String> getValues() {
        return values;
    }
}
