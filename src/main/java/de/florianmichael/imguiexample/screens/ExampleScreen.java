package de.florianmichael.imguiexample.screens;

import de.florianmichael.imguiexample.imgui.ImGuiImpl;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public final class ExampleScreen extends Screen {

    private static final ImBoolean showDemoWindow = new ImBoolean(false);

    public ExampleScreen() {
        super(Text.literal("Example Screen"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        ImGuiImpl.draw(this::renderImGui);
    }

    private void renderImGui(final ImGuiIO io) {
        if (ImGui.begin("Hello, World!", ImGuiWindowFlags.None)) {
            ImGui.setWindowSize(800, 600);

            ImGui.checkbox("Show Demo Window", showDemoWindow);
        }
        ImGui.end();

        ImGui.showDemoWindow(showDemoWindow);
    }

    @Override
    public boolean shouldPause() {
        return false; // Only relevant in singleplayer
    }

}
