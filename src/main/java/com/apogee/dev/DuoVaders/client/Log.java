package com.apogee.dev.DuoVaders.client;

/** Utility class for logging info on console.
 * Implements the Android-like Log command.
 * @author PRV
 * @version 1.0
 * @see <a href="https://developer.android.com/reference/android/util/Log">Android Log</a>
 */
public class Log {
    private static final String BLUE = "\u001B[34m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String RESET = "\u001B[0m";

    /**
     * Constructor of the Log class. This class is not meant to be instantiated.
     */
    public static void main() {
        return;
    }
    /** Log a detailed error message
     * @param tag The tag to use for the log message. Can be null
     * @param msg The message to log
     * @param tr An exception to log. Can be null
     */
    public static void e(String tag, String msg, Throwable tr){
        System.out.println(RED + "[LOG/E]: (" + tag + ") " + msg + RESET);
        if(tr != null) tr.printStackTrace();
    }

    /**
     * Log a labelled error message
     * @param tag
     * @param msg
     */
    public static void e(String tag, String msg){
        System.out.println(RED + "[LOG/E]: (" + tag + ") " + msg + RESET);
    }

    /**
     * Log a concise error message
     * @param msg
     */
    public static void e(String msg){
        System.out.println(RED + "[LOG/E]: " + msg + RESET);
    }

    /** Log a detailed info message
     *
     * @param tag The tag to use for the log message. Can be null
     * @param msg The message to log
     * @param tr An exception to log. Can be null
     */
    public static void i(String tag, String msg, Throwable tr) {
        System.out.println("[LOG/I]: (" + tag + ") " + msg);
        if (tr != null) tr.printStackTrace();
    }

    /** Log a labelled info message
     *
     * @param tag The tag to use for the log message. Can be null
     * @param msg The message to log
     */
    public static void i(String tag, String msg) {
        System.out.println("[LOG/I]: (" + tag + ") " + msg);
    }

    /** Log a concise info message
     *
     * @param msg The message to log
     */
    public static void i(String msg){
        System.out.println("[LOG/I]: " + msg);
    }

    /** Log a detailed debug message
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

    /** Log a labelled debug message
     *
     * @param tag The tag to use for the log message. Can be null
     * @param msg The message to log
     */
    public static void d(String tag, String msg) {
        //blue-colored message
        System.out.println(BLUE + "[LOG/D]: (" + tag + ") " + msg + RESET);
    }

    /** Log a concise debug message
     *
     * @param msg The message to log
     */
    public static void d(String msg){
        //blue-colored message
        System.out.println(BLUE + "[LOG/D]: " + msg + RESET);
    }
}
