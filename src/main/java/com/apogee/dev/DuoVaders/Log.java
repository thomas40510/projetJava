package com.apogee.dev.DuoVaders;

/** Utility class for logging info on console.
 * Implements the Android-like Log.p command.
 * @author PRV
 * @version 1.0
 * @see <a href="https://developer.android.com/reference/android/util/Log">Android Log</a>
 */
public class Log {
    private static final String BLUE = "\u001B[34m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String RESET = "\u001B[0m";
    public static void main(String[] args) {
        return;
    }
    /** Log an error message
    * @param msg The message to log
    */
    public static void e(String msg){
        //red-colored message
        System.out.println(RED + "[LOG/E]: " + msg + RESET);
    }

    /** Log an info message
     *
     * @param msg The message to log
     */
    public static void i(String msg){
        //blue-colored message
        System.out.println("[LOG/I]: " + msg);
    }

    /** Log a debug message
     *
     * @param msg The message to log
     */
    public static void d(String msg){
        //blue-colored message
        System.out.println(BLUE + "[LOG/D]: " + msg + RESET);
    }
}
