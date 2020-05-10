package edu.depaul.ntessema.jaxws;

import java.sql.Timestamp;
import java.time.Instant;

public class Utilities {
    /*
     * Remove excess white spaces.
     * This makes it easier to identify a quote
     * that already exists
     */
    public static String cleanQuote(String quote) {
        return quote
                .trim()
                .replaceAll("\\s+", " ")
                .replaceAll("(\\s+)(\\p{Punct})", "$2");
    }

    public static void log(final String message, final Event event) {
        Instant instant = Instant.now();
        String timestamp = Timestamp.from(instant).toString().replaceAll("\\.\\d{1,3}", "");
        System.out.println(String.format("%19s %3s %s", timestamp, event, message));
    }

   public static void displayLogo(final String s) {
        final String FORMAT = "%8s%s\n";
        final int WIDTH = 45;
        final int L = (WIDTH - s.length()) / 2;
        final int R = WIDTH - s.length() - L - 2;
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(FORMAT, "", "+-------------------------------------------+"));
        sb.append(String.format(FORMAT, "", "|               Quote of the Day            |"));
        sb.append(String.format(FORMAT, "", "|      (A JAX-WS Based SOAP Web Service)    |"));
        sb.append(String.format(FORMAT, "", "|                Nardos Tessema             |"));
        sb.append(String.format(FORMAT, "", "+-------------------------------------------+"));
        sb.append(String.format("%8s%s%s%s%s%s\n", "", "|", spaces(L), s, spaces(R), "|"));
        sb.append(String.format(FORMAT, "", "+-------------------------------------------+"));
        System.out.println(sb.toString());
    }

    private static String spaces(final int n) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < n; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }
}
