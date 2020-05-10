package edu.depaul.ntessema.jaxws;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * @author nardos
 *
 * Utility methods that have been littering the
 * service implementation class and were factored out.
 */
public class Utilities {
    /*
     * (1) Replace all sequences of one or more whitespace characters
     *     (such as the tab character, multiple spaces, etc.)
     *     by a single whitespace character.
     * (2) Remove trailing and leading whitespaces.
     * (3) Remove whitespaces between two punctuation marks as in "!   !".
     * (4) Remove whitespace right before a punctuation mark as in "word   , word"
     *
     * This makes it easier to tell if a quote already exists.
     * If two quotes differ only by whitespaces, they will be
     * considered as the same quotes.
     */
    public static String cleanQuote(String quote) {
        return quote
                .trim()
                .replaceAll("\\s+", " ")
                .replaceAll("(\\s+)(\\p{Punct})", "$2");
    }

    /**
     * Print console outputs with timestamps and event
     * information. i.e.if the even was GET, ADD, REJECTED.
     * This is handy for future upgrades when more
     * production-like logging is required.
     */
    public static void log(final String message, final Event event) {
        Instant instant = Instant.now();
        /*
         * remove milliseconds from timestamps.
         */
        String timestamp = Timestamp.from(instant).toString().replaceAll("\\.\\d{1,3}", "");
        System.out.println(String.format("%19s %3s %s", timestamp, event, message));
    }

    /**
     * Print an ASCII logo.
     */
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

    /**
     * Whitespaces to format the ASCII logo.
     */
    private static String spaces(final int n) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < n; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }
}
