package me.freitas.proleague.utils;

public class TimeUtil {
    public static String formatTime(long seconds) {
        long days = seconds / 86400;
        long remainder = seconds % 86400;
        long hours = remainder / 3600;
        remainder %= 3600;
        long minutes = remainder / 60;
        long secs = remainder % 60;

        StringBuilder sb = new StringBuilder();
        if (days > 0) {
            sb.append(days).append("d ");
        }
        if (hours > 0) {
            sb.append(hours).append("h ");
        }
        if (minutes > 0) {
            sb.append(minutes).append("m ");
        }
        sb.append(secs).append("s");
        return sb.toString();
    }
}