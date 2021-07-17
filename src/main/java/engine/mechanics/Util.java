package engine.mechanics;

import java.util.HashMap;

/**
 * Contains useful methods to save some time writing code.
 */
public class Util {

    private Util() {

    }

    /**
     * Clamps a value between a max and a min value.
     *
     * @param val The value that should be clamped.
     * @param max The max value that the var shouldn't overshoot.
     * @param min The min value that the var shouldn't overshoot.
     * @return Returns the clamped/corrected value.
     */
    public static int clamp(int val, int max, int min) {
        val = Math.max(val, min);
        val = Math.min(val, max);
        return val;
    }

    /**
     * Clamps a value between a max and a min value.
     *
     * @param val The value that should be clamped.
     * @param max The max value that the var shouldn't overshoot.
     * @param min The min value that the var shouldn't overshoot.
     * @return Returns the clamped/corrected value.
     */
    public static double clamp(double val, double max, double min) {
        val = Math.max(val, min);
        val = Math.min(val, max);
        return val;
    }

    /**
     * Clamps a value between a max and a min value.
     *
     * @param val The value that should be clamped.
     * @param max The max value that the var shouldn't overshoot.
     * @param min The min value that the var shouldn't overshoot.
     * @return Returns the clamped/corrected value.
     */
    public static float clamp(float val, float max, float min) {
        val = Math.max(val, min);
        val = Math.min(val, max);
        return val;
    }

    /**
     * Clamps a value between a max and a min value.
     *
     * @param val The value that should be clamped.
     * @param max The max value that the var shouldn't overshoot.
     * @param min The min value that the var shouldn't overshoot.
     * @return Returns the clamped/corrected value.
     */
    public static long clamp(long val, long max, long min) {
        val = Math.max(val, min);
        val = Math.min(val, max);
        return val;
    }

    /**
     * Generates a random value in the set range.
     *
     * @param min The minimum result.
     * @param max The maximum result.
     * @return Returns the generated random value.
     */
    public static int getRandomInt(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    /**
     * Generates a random value in the set range.
     *
     * @param min The minimum result.
     * @param max The maximum result.
     * @return Returns the generated random value.
     */
    public static double getRandomDouble(double min, double max) {
        return (Math.random() * (max - min)) + min;
    }

    /**
     * Generates a random value in the set range.
     *
     * @param min The minimum result.
     * @param max The maximum result.
     * @return Returns the generated random value.
     */
    public static float getRandomFloat(float min, float max) {
        return (float) ((Math.random() * (max - min)) + min);
    }

    /**
     * Generates a random value in the set range.
     *
     * @param min The minimum result.
     * @param max The maximum result.
     * @return Returns the generated random value.
     */
    public static long getRandomFloat(long min, long max) {
        return (long) ((Math.random() * (max - min)) + min);
    }
}
