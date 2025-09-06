package dev.knoxy.rynox.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class FontManager {

    public static TextRenderer PRODUCT_SANS;

    public static void init() {
        MinecraftClient client = MinecraftClient.getInstance();
        PRODUCT_SANS = new TextRenderer(client.getFontManager().getFontStorage(new Identifier("rynox:product_sans"))::getFont, false);
    }
}
