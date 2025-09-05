package dev.knoxy.rynox.gui;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;

public class ClickGui implements RenderInterface {

    private boolean isVisible = false;
    private int currentCategory = 0;
    private final String[] categories = {"Combat"};

    // Dummy module state
    private final ImBoolean autoCrystalEnabled = new ImBoolean(false);

    public ClickGui() {
        setupStyle();
    }

    private void setupStyle() {
        ImGui.getStyle().setWindowRounding(5.0f);
        ImGui.getStyle().setFrameRounding(4.0f);
        ImGui.getStyle().setGrabRounding(4.0f);

        ImGui.getStyle().setColor(ImGuiCol.Text, 1.00f, 1.00f, 1.00f, 1.00f);
        ImGui.getStyle().setColor(ImGuiCol.TextDisabled, 0.50f, 0.50f, 0.50f, 1.00f);
        ImGui.getStyle().setColor(ImGuiCol.WindowBg, 0.10f, 0.10f, 0.10f, 0.95f);
        ImGui.getStyle().setColor(ImGuiCol.ChildBg, 0.12f, 0.12f, 0.12f, 1.00f);
        ImGui.getStyle().setColor(ImGuiCol.PopupBg, 0.08f, 0.08f, 0.08f, 0.94f);
        ImGui.getStyle().setColor(ImGuiCol.Border, 0.20f, 0.20f, 0.20f, 0.50f);
        ImGui.getStyle().setColor(ImGuiCol.BorderShadow, 0.00f, 0.00f, 0.00f, 0.00f);
        ImGui.getStyle().setColor(ImGuiCol.FrameBg, 0.20f, 0.21f, 0.22f, 0.54f);
        ImGui.getStyle().setColor(ImGuiCol.FrameBgHovered, 0.40f, 0.40f, 0.40f, 0.40f);
        ImGui.getStyle().setColor(ImGuiCol.FrameBgActive, 0.18f, 0.18f, 0.18f, 0.67f);
        ImGui.getStyle().setColor(ImGuiCol.TitleBg, 0.04f, 0.04f, 0.04f, 1.00f);
        ImGui.getStyle().setColor(ImGuiCol.TitleBgActive, 0.29f, 0.29f, 0.29f, 1.00f);
        ImGui.getStyle().setColor(ImGuiCol.TitleBgCollapsed, 0.00f, 0.00f, 0.00f, 0.51f);
        ImGui.getStyle().setColor(ImGuiCol.MenuBarBg, 0.14f, 0.14f, 0.14f, 1.00f);
        ImGui.getStyle().setColor(ImGuiCol.ScrollbarBg, 0.02f, 0.02f, 0.02f, 0.53f);
        ImGui.getStyle().setColor(ImGuiCol.ScrollbarGrab, 0.31f, 0.31f, 0.31f, 1.00f);
        ImGui.getStyle().setColor(ImGuiCol.ScrollbarGrabHovered, 0.41f, 0.41f, 0.41f, 1.00f);
        ImGui.getStyle().setColor(ImGuiCol.ScrollbarGrabActive, 0.51f, 0.51f, 0.51f, 1.00f);
        ImGui.getStyle().setColor(ImGuiCol.CheckMark, 0.94f, 0.94f, 0.94f, 1.00f);
        ImGui.getStyle().setColor(ImGuiCol.SliderGrab, 0.51f, 0.51f, 0.51f, 1.00f);
        ImGui.getStyle().setColor(ImGuiCol.SliderGrabActive, 0.86f, 0.86f, 0.86f, 1.00f);
        ImGui.getStyle().setColor(ImGuiCol.Button, 0.44f, 0.44f, 0.44f, 0.40f);
        ImGui.getStyle().setColor(ImGuiCol.ButtonHovered, 0.46f, 0.47f, 0.48f, 1.00f);
        ImGui.getStyle().setColor(ImGuiCol.ButtonActive, 0.42f, 0.42f, 0.42f, 1.00f);
        ImGui.getStyle().setColor(ImGuiCol.Header, 0.70f, 0.70f, 0.70f, 0.31f);
        ImGui.getStyle().setColor(ImGuiCol.HeaderHovered, 0.70f, 0.70f, 0.70f, 0.80f);
        ImGui.getStyle().setColor(ImGuiCol.HeaderActive, 0.48f, 0.50f, 0.52f, 1.00f);
        ImGui.getStyle().setColor(ImGuiCol.Separator, 0.43f, 0.43f, 0.50f, 0.50f);
        ImGui.getStyle().setColor(ImGuiCol.SeparatorHovered, 0.72f, 0.72f, 0.72f, 0.78f);
        ImGui.getStyle().setColor(ImGuiCol.SeparatorActive, 0.51f, 0.51f, 0.51f, 1.00f);
        ImGui.getStyle().setColor(ImGuiCol.ResizeGrip, 0.91f, 0.91f, 0.91f, 0.25f);
        ImGui.getStyle().setColor(ImGuiCol.ResizeGripHovered, 0.81f, 0.81f, 0.81f, 0.67f);
        ImGui.getStyle().setColor(ImGuiCol.ResizeGripActive, 0.46f, 0.46f, 0.46f, 0.95f);
        ImGui.getStyle().setColor(ImGuiCol.Tab, 0.58f, 0.58f, 0.58f, 0.86f);
        ImGui.getStyle().setColor(ImGuiCol.TabHovered, 0.98f, 0.98f, 0.98f, 0.80f);
        ImGui.getStyle().setColor(ImGuiCol.TabActive, 0.68f, 0.68f, 0.68f, 1.00f);
        ImGui.getStyle().setColor(ImGuiCol.TabUnfocused, 0.15f, 0.15f, 0.15f, 0.97f);
        ImGui.getStyle().setColor(ImGuiCol.TabUnfocusedActive, 0.42f, 0.42f, 0.42f, 1.00f);
        ImGui.getStyle().setColor(ImGuiCol.DockingPreview, 0.26f, 0.59f, 0.98f, 0.70f);
        ImGui.getStyle().setColor(ImGuiCol.DockingEmptyBg, 0.20f, 0.20f, 0.20f, 1.00f);
        ImGui.getStyle().setColor(ImGuiCol.PlotLines, 0.61f, 0.61f, 0.61f, 1.00f);
        ImGui.getStyle().setColor(ImGuiCol.PlotLinesHovered, 1.00f, 0.43f, 0.35f, 1.00f);
        ImGui.getStyle().setColor(ImGuiCol.PlotHistogram, 0.90f, 0.70f, 0.00f, 1.00f);
        ImGui.getStyle().setColor(ImGuiCol.PlotHistogramHovered, 1.00f, 0.60f, 0.00f, 1.00f);
        ImGui.getStyle().setColor(ImGuiCol.TextSelectedBg, 0.26f, 0.59f, 0.98f, 0.35f);
        ImGui.getStyle().setColor(ImGuiCol.DragDropTarget, 1.00f, 1.00f, 0.00f, 0.90f);
        ImGui.getStyle().setColor(ImGuiCol.NavHighlight, 0.26f, 0.59f, 0.98f, 1.00f);
        ImGui.getStyle().setColor(ImGuiCol.NavWindowingHighlight, 1.00f, 1.00f, 1.00f, 0.70f);
        ImGui.getStyle().setColor(ImGuiCol.NavWindowingDimBg, 0.80f, 0.80f, 0.80f, 0.20f);
        ImGui.getStyle().setColor(ImGuiCol.ModalWindowDimBg, 0.80f, 0.80f, 0.80f, 0.35f);
    }

    public void toggle() {
        this.isVisible = !this.isVisible;
    }

    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public void render(ImGuiIO io) {
        if (!isVisible) {
            return;
        }

        int windowFlags = ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoCollapse;
        ImGui.setNextWindowSize(600, 400);
        ImGui.begin("Rynox", windowFlags);

        // Sidebar
        ImGui.beginChild("Sidebar", 120, 0, true);
        {
            for (int i = 0; i < categories.length; i++) {
                if (ImGui.selectable(categories[i], currentCategory == i)) {
                    currentCategory = i;
                }
            }
        }
        ImGui.endChild();

        ImGui.sameLine();

        // Main content area
        ImGui.beginChild("Content", 0, 0, true);
        {
            if (currentCategory == 0) { // Combat
                ImGui.text("Combat");
                ImGui.separator();
                ImGui.checkbox("Auto Crystal", autoCrystalEnabled);
            }
        }
        ImGui.endChild();

        ImGui.end();
    }
}
