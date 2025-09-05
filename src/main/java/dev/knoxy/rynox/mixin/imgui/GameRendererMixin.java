package dev.knoxy.rynox.mixin.imgui;

import dev.knoxy.rynox.Rynox;
import dev.knoxy.rynox.gui.ImGuiImpl;
import dev.knoxy.rynox.gui.RenderInterface;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "render", at = @At("RETURN"))
    private void render(RenderTickCounter tickCounter, boolean tick, CallbackInfo ci) {
        if (Rynox.CLICK_GUI != null && Rynox.CLICK_GUI.isVisible()) {
            ImGuiImpl.draw(Rynox.CLICK_GUI);
        }

        if (client.currentScreen instanceof final RenderInterface renderInterface) {
            ImGuiImpl.draw(renderInterface);
        }
    }

}
