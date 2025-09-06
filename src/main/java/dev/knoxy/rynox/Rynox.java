package dev.knoxy.rynox;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Rynox implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("rynox");

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing Rynox...");
    }
}
