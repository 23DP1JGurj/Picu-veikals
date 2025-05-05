package lv.rvt;

public class ConsoleColors {
    public static final String RESET  = "\u001B[0m";

    public static final String RED = "\u001B[38;2;178;0;31m";
    public static final String YELLOW = "\u001B[33m";
    public static final String GREEN  = "\u001B[32m";
    public static final String CYAN   = "\u001B[36m";
    public static final String LIGHT_BROWN = "\u001B[38;5;180m";


    public static final String ORANGE = "\u001B[38;5;208m";

    public static String bold(String text) {
        return "\u001B[1m" + text + RESET;
    }
}
