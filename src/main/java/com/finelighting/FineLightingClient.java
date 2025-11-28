package com.finelighting;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FineLightingClient implements ClientModInitializer {
    public static final String MOD_ID = "finelighting";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static KeyBinding increaseLightKey;
    private static KeyBinding decreaseLightKey;
    private static KeyBinding toggleFullBrightKey;

    @Override
    public void onInitializeClient() {
        LOGGER.info("Fine Lighting mod initialized!");

        // Register keybindings
        increaseLightKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.finelighting.increase_light",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_BRACKET,
                "category.finelighting.main"
        ));

        decreaseLightKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.finelighting.decrease_light",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_BRACKET,
                "category.finelighting.main"
        ));

        toggleFullBrightKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.finelighting.toggle_fullbright",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_B,
                "category.finelighting.main"
        ));

        // Register tick event to handle key presses
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (increaseLightKey.wasPressed()) {
                LightingConfig.increaseLightBoost();
                if (client.player != null) {
                    client.player.sendMessage(
                            net.minecraft.text.Text.literal("Light boost: " + LightingConfig.getLightBoost()),
                            true
                    );
                }
                // Force lighting update
                if (client.worldRenderer != null) {
                    client.worldRenderer.reload();
                }
            }

            while (decreaseLightKey.wasPressed()) {
                LightingConfig.decreaseLightBoost();
                if (client.player != null) {
                    client.player.sendMessage(
                            net.minecraft.text.Text.literal("Light boost: " + LightingConfig.getLightBoost()),
                            true
                    );
                }
                // Force lighting update
                if (client.worldRenderer != null) {
                    client.worldRenderer.reload();
                }
            }

            while (toggleFullBrightKey.wasPressed()) {
                LightingConfig.toggleFullBright();
                if (client.player != null) {
                    client.player.sendMessage(
                            net.minecraft.text.Text.literal("Full bright: " + (LightingConfig.isFullBright() ? "ON" : "OFF")),
                            true
                    );
                }
                // Force lighting update
                if (client.worldRenderer != null) {
                    client.worldRenderer.reload();
                }
            }
        });
    }
}
