package dev.knoxy.rynox.ui.clickgui;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.knoxy.rynox.ui.clickgui.component.*;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ClickGUI extends Screen {

    private enum AnimationState {
        OPENING,
        CLOSING,
        IDLE
    }

    private AnimationState animState = AnimationState.IDLE;
    private long startTime;
    private static final long ANIMATION_DURATION_MS = 200;

    private List<Frame> frames;

    public ClickGUI() {
        super(Text.of("Rynox ClickGUI"));
    }

    @Override
    protected void init() {
        super.init();
        this.startTime = System.currentTimeMillis();
        this.animState = AnimationState.OPENING;

        this.frames = new ArrayList<>();
        Frame testFrame = new Frame("Test Frame", 50, 50, 120, 300); // Increased height

        testFrame.addComponent(new Button("Click Me!", 0, 0, 100, 20));
        testFrame.addComponent(new Checkbox("A Checkbox", 0, 0, 12));
        testFrame.addComponent(new Slider("A Slider", 0, 0, 100, 10));

        // Add the new components
        List<String> dropdownOptions = List.of("Vanilla", "Grim", "Verus");
        testFrame.addComponent(new Dropdown("Mode", dropdownOptions, 0, 0, 100, 15));
        testFrame.addComponent(new RangeSlider("Reach", 0, 0, 100, 10));

        this.frames.add(testFrame);
    }

    @Override
    public void close() {
        this.startTime = System.currentTimeMillis();
        this.animState = AnimationState.CLOSING;
    }

    private float getAnimationProgress() {
        long timePassed = System.currentTimeMillis() - this.startTime;
        if (timePassed > ANIMATION_DURATION_MS) {
            return 1.0f;
        }
        return (float) timePassed / ANIMATION_DURATION_MS;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        float progress = getAnimationProgress();
        float alpha = 1.0f;

        if (animState == AnimationState.OPENING) {
            alpha = progress;
            if (progress >= 1.0f) {
                animState = AnimationState.IDLE;
                alpha = 1.0f;
            }
        } else if (animState == AnimationState.CLOSING) {
            alpha = 1.0f - progress;
            if (progress >= 1.0f) {
                if (this.client != null) {
                    this.client.setScreen(null);
                }
                return;
            }
        }

        if (alpha <= 0.05f) return;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, alpha);

        this.renderBackground(context, mouseX, mouseY, delta);

        for (Frame frame : frames) {
            frame.render(context, mouseX, mouseY, delta);
        }

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableBlend();
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        for (Frame frame : frames) {
            frame.mouseClicked(mouseX, mouseY, button);
        }
        super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        for (Frame frame : frames) {
            frame.mouseReleased(mouseX, mouseY, button);
        }
        super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        for (Frame frame : frames) {
            frame.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        }
        super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
