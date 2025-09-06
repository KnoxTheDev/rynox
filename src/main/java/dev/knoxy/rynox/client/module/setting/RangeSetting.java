package dev.knoxy.rynox.client.module.setting;

public class RangeSetting extends Setting<Range> {
    private final double min;
    private final double max;

    public RangeSetting(String name, Range value, double min, double max) {
        super(name, value);
        this.min = min;
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }
}
