package engine.mechanics;

import java.util.HashMap;

public class Storage {
    private static HashMap<String, Object> varSaver = new HashMap<>();
    private Storage() {
    }

    /**
     * Saves a value to a keyword. You can use this to store values globally so you can share them easier between classes.
     *
     * @param key   The key value that the variable should be stored in
     * @param value The value you want to assign. Every datatype should work.
     */
    public static void saveValue(String key, Object value) {
        varSaver.put(key, value);
    }

    /**
     * Fetches the value of a previously saved value. Note that you have to cast the value to your datatype.
     *
     * @param key The key of the value.
     * @return Returns the value as an object.
     */
    public static Object getValue(String key) {
        return varSaver.get(key);
    }

    /**
     * Fetches the value of a previously saved value. Note that if the value is not compatible with the integer datatype
     * an error will be thrown.
     *
     * @param key The key of the value.
     * @return Returns the value as an integer.
     */
    public static int getValueAsInt(String key) {
        return (int) varSaver.get(key);
    }

    /**
     * Fetches the value of a previously saved value. Note that if the value is not compatible with the double datatype
     * an error will be thrown.
     *
     * @param key The key of the value.
     * @return Returns the value as an double.
     */
    public static double getValueAsDouble(String key) {
        return (double) varSaver.get(key);
    }

    /**
     * Fetches the value of a previously saved value. Note that if the value is not compatible with the float datatype
     * an error will be thrown.
     *
     * @param key The key of the value.
     * @return Returns the value as an float.
     */
    public static float getValueAsFloat(String key) {
        return (float) varSaver.get(key);
    }

    /**
     * Fetches the value of a previously saved value. Note that if the value is not compatible with the long datatype
     * an error will be thrown.
     *
     * @param key The key of the value.
     * @return Returns the value as an long.
     */
    public static long getValueAsLong(String key) {
        return (long) varSaver.get(key);
    }

    /**
     * Fetches the value of a previously saved value. Note that if the value is not compatible with the boolean datatype
     * an error will be thrown.
     *
     * @param key The key of the value.
     * @return Returns the value as an boolean.
     */
    public static boolean getValueAsBool(String key) {
        return (boolean) varSaver.get(key);
    }

    /**
     * Fetches the value of a previously saved value. Note that if the value is not compatible with the float datatype
     * an error will be thrown.
     *
     * @param key The key of the value.
     * @return Returns the value as an float.
     */
    public static String getValueAsString(String key) {
        return String.valueOf(varSaver.get(key));
    }
}
