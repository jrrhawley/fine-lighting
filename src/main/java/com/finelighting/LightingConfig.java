package com.finelighting;

/**
 * Configuration class for Fine Lighting mod.
 * Manages light boost level and full bright mode.
 */
public class LightingConfig {
    // Light boost ranges from 0 (no boost) to 15 (max light everywhere)
    private static int lightBoost = 0;
    private static boolean fullBright = false;

    // Minimum light level (0-15), blocks below this will be boosted
    private static final int MIN_LIGHT = 0;
    private static final int MAX_LIGHT = 15;
    private static final int BOOST_STEP = 1;

    /**
     * Get the current light boost value.
     * @return Light boost value (0-15)
     */
    public static int getLightBoost() {
        return lightBoost;
    }

    /**
     * Set the light boost value.
     * @param boost Light boost value (will be clamped to 0-15)
     */
    public static void setLightBoost(int boost) {
        lightBoost = Math.max(MIN_LIGHT, Math.min(MAX_LIGHT, boost));
    }

    /**
     * Increase the light boost by one step.
     */
    public static void increaseLightBoost() {
        setLightBoost(lightBoost + BOOST_STEP);
    }

    /**
     * Decrease the light boost by one step.
     */
    public static void decreaseLightBoost() {
        setLightBoost(lightBoost - BOOST_STEP);
    }

    /**
     * Check if full bright mode is enabled.
     * @return true if full bright mode is on
     */
    public static boolean isFullBright() {
        return fullBright;
    }

    /**
     * Toggle full bright mode.
     */
    public static void toggleFullBright() {
        fullBright = !fullBright;
    }

    /**
     * Set full bright mode.
     * @param enabled true to enable full bright
     */
    public static void setFullBright(boolean enabled) {
        fullBright = enabled;
    }

    /**
     * Calculate the modified light level based on current settings.
     * @param originalLight The original light level (0-15)
     * @return The modified light level (0-15)
     */
    public static int getModifiedLightLevel(int originalLight) {
        if (fullBright) {
            return MAX_LIGHT;
        }
        return Math.min(MAX_LIGHT, originalLight + lightBoost);
    }
}
