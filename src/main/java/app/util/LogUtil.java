package app.util;

public class LogUtil {
    public static void setJulFormat() {
        System.setProperty("java.util.logging.SimpleFormatter.format",
            "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$-7s %3$-30s %5$s %6$s%n");
    }

    public static void setJulFormatCompact() {
        System.setProperty("java.util.logging.SimpleFormatter.format",
            "%5$s %6$s%n");
    }
}
