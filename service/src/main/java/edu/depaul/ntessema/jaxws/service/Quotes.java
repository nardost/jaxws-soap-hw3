package edu.depaul.ntessema.jaxws.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Quotes {

    /*
     * The quotes silo
     */
    private final static List<String> quotes = Stream.of(new String[] {
            "Conscience does make cowards of us all.",
            "What does not destroy me makes me stronger.",
            "Everything has been figured out, except how to live.",
            "There is no fate that cannot be surmounted by scorn.",
            "There are so many dawns that have not yet broken."
    }).collect(Collectors.toList());

    private final static Map<Integer, Boolean> served = new HashMap<>();

    private static Quotes INSTANCE = null;

    private Quotes() {
        for(int i = 0; i < quotes.size(); i++) {
            markQuoteNotServed(i);
        }
    }

    static void initialize() {
        if(INSTANCE == null) {
            synchronized (Quotes.class) {
                if(INSTANCE == null) {
                    INSTANCE = new Quotes();
                }
            }
        }
    }

    static String getNextQuote() {
        int selectedIndex;
        if(allQuotesServed()) {
            markAllQuotesNotServed();
        }
        do {
            selectedIndex = ThreadLocalRandom.current().nextInt(0, quotes.size());
        } while(served.get(selectedIndex));
        final String selectedQuote = quotes.get(selectedIndex);
        markQuoteServed(selectedIndex);
        return selectedQuote;
    }

    static boolean quoteExists(String quote) {
        return quotes.contains(quote);
    }

    static void add(String quote) {
        quotes.add(quote);
        markQuoteNotServed(quotes.size() - 1);
    }

    private static void markQuoteServed(final int index) {
        served.put(index, true);
    }

    private static void markQuoteNotServed(final int index) {
        served.put(index, false);
    }

    private static boolean allQuotesServed() {
        for(Map.Entry<Integer, Boolean> q : served.entrySet()) {
            if(!q.getValue()) {
                return false;
            }
        }
        return true;
    }

    private static void markAllQuotesNotServed() {
        for(Map.Entry<Integer, Boolean> q : served.entrySet()) {
            q.setValue(false);
        }
    }
}
