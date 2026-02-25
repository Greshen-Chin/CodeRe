package util;

public class ClampUtil {

    public static int clampZero(int value) {
        return Math.max(0, value);
    }

    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}