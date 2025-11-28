package com.finelighting.mixin;

import com.finelighting.LightingConfig;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.texture.NativeImage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightmapTextureManager.class)
public class LightmapTextureManagerMixin {

    @Shadow
    private NativeImage image;

    @Inject(method = "update", at = @At("TAIL"))
    private void modifyLightmap(float delta, CallbackInfo ci) {
        if (LightingConfig.getLightBoost() == 0 && !LightingConfig.isFullBright()) {
            return;
        }

        float boostFactor = LightingConfig.getLightBoost() / 15.0f;

        for (int skyLight = 0; skyLight < 16; skyLight++) {
            for (int blockLight = 0; blockLight < 16; blockLight++) {
                int color = this.image.getColor(blockLight, skyLight);

                int alpha = (color >> 24) & 0xFF;
                int blue = (color >> 16) & 0xFF;
                int green = (color >> 8) & 0xFF;
                int red = color & 0xFF;

                if (LightingConfig.isFullBright()) {
                    red = 255;
                    green = 255;
                    blue = 255;
                } else {
                    red = Math.min(255, (int) (red + (255 - red) * boostFactor));
                    green = Math.min(255, (int) (green + (255 - green) * boostFactor));
                    blue = Math.min(255, (int) (blue + (255 - blue) * boostFactor));
                }

                this.image.setColor(blockLight, skyLight, (alpha << 24) | (blue << 16) | (green << 8) | red);
            }
        }
    }
}
