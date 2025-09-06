package dev.knoxy.rynox.client.module;

import dev.knoxy.rynox.client.module.setting.*;

import java.util.Arrays;

public class TestModule extends Module {

    public final BooleanSetting booleanSetting = new BooleanSetting("Boolean", false);
    public final DoubleSetting doubleSetting = new DoubleSetting("Double", 5.0, 0.0, 10.0);
    public final RangeSetting rangeSetting = new RangeSetting("Range", new Range(2.0, 8.0), 0.0, 10.0);
    public final EnumSetting enumSetting = new EnumSetting("Enum", "Value 1", Arrays.asList("Value 1", "Value 2", "Value 3"));

    public TestModule() {
        super("TestModule", "A module for testing widgets.", Category.MISC);
        addSetting(booleanSetting);
        addSetting(doubleSetting);
        addSetting(rangeSetting);
        addSetting(enumSetting);
    }
}
