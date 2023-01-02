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
     * @param tag The tag to use for the log message. Can be null
     * @param msg The message to log
     * @param tr An exception to log. Can be null
     */
    public static void e(String tag, String msg, Throwable tr){
        //red-colored message
        System.out.println(RED + "[LOG/E]: (" + tag + ") " + msg + RESET);
        if(tr != null) tr.printStackTrace();
    }
    public static void e(String tag, String msg){
        //red-colored message
        System.out.println(RED + "[LOG/E]: (" + tag + ") " + msg + RESET);
    }
    public static void e(String msg){
        //red-colored message
        System.out.println(RED + "[LOG/E]: " + msg + RESET);
    }

    /** Log an info message
     *
     * @param tag The tag to use for the log message. Can be null
     * @param msg The message to log
     * @param tr An exception to log. Can be null
     */
    public static void i(String tag, String msg, Throwable tr) {
        System.out.println("[LOG/I]: (" + tag + ") " + msg);
        if (tr != null) tr.printStackTrace();
    }
    public static void i(String tag, String msg) {
        System.out.println("[LOG/I]: (" + tag + ") " + msg);
    }
    public static void i(String msg){
        System.out.println("[LOG/I]: " + msg);
    }

    /** Log a debug message
     *
     * @param tag The tag to use for the log message. Can be null
     * @param msg The message to log
     * @param tr An exception to log. Can be null
     */
    public static void d(String tag, String msg, Throwable tr) {
        //blue-colored message
        System.out.println(BLUE + "[LOG/D]: (" + tag + ") " + msg + RESET);
        //print stack trace if exception is not null
        if(tr != null) tr.printStackTrace();
    }
    public static void d(String tag, String msg) {
        //blue-colored message
        System.out.println(BLUE + "[LOG/D]: (" + tag + ") " + msg + RESET);
    }
    public static void d(String msg){
        //blue-colored message
        System.out.println(BLUE + "[LOG/D]: " + msg + RESET);
    }
}
