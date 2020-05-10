package edu.depaul.ntessema.jaxws.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author nardos
 *
 * This class abstracts the addition and retrieval of
 * quotes for the service implementation.
 * If in the furture newly added quotes are required to be persisted
 * across restarts of the service, it can be implemented here
 * wihtout rewriting the service implementation.
 */
class Quotes {

    /*
     * The quotes silo. Five quotes to start with.
     * The List data structure is chosen over an array of strings
     * to make addition of new quotes at runtime possible.
     */
    private final static List<String> quotes = Stream.of(new String[] {
            "Conscience does make cowards of us all.",
            "What does not destroy me makes me stronger.",
            "Everything has been figured out, except how to live.",
            "There is no fate that cannot be surmounted by scorn.",
            "There are so many dawns that have not yet broken."
    }).collect(Collectors.toList());

    /*
     * Quotes already served are tracked in this hash map.
     */
    private final static Map<Integer, Boolean> served = new HashMap<>();

    /*
     * Needed to make the class a singleton.
     */
    private static Quotes INSTANCE = null;

    /*
     * Make the constructor private to make sure a single Quote
     * object is instantiated only through the initialize() method.
     */
    private Quotes() {
        /*
         * Initially, all quotes have not been served yet.
         */
        for(int i = 0; i < quotes.size(); i++) {
            markQuoteNotServed(i);
        }
    }

    /*
     * Make Quote a singleton. We want to make sure multiple
     * Quote objects are not created for our single service.
     */
    static void initialize() {
        if(INSTANCE == null) {
            synchronized (Quotes.class) {
                if(INSTANCE == null) {
                    INSTANCE = new Quotes();
                }
            }
        }
    }

    /**
     * The method that retrieves a quote from the
     * available set of quotes in a random manner.
     */
    static String getQuote() {
        int selectedIndex;
        /*
         * If all of the quotes have already been served,
         * mark all quotes as not served (start over).
         */
        if(allQuotesAreServed()) {
            markAllQuotesNotServed();
        }
        /*
         * Make sure to select a quote that has not been served yet.
         * Keep generating a random index integer until the quote
         * that is at the generated index location is a not-served one.
         */
        do {
            /*
             * ThreadLocalRandom is preferred over Random in a multi-threaded environment.
             */
            selectedIndex = ThreadLocalRandom.current().nextInt(0, quotes.size());
        } while(served.get(selectedIndex));
        final String selectedQuote = quotes.get(selectedIndex);
        /*
         * A quote should be marked as served once it is selected.
         */
        markQuoteServed(selectedIndex);
        return selectedQuote;
    }

    /**
     * Check if an incoming quote is in the initial list of quotes
     * or has already been added by a client at run time.
     */
    static boolean quoteExists(final String quote) {
        return quotes.contains(quote);
    }

    /**
     * Add the quote to the list of quotes and mark
     * is not served yet (update the served data structure).
     */
    static void addQuote(final String quote) {
        quotes.add(quote);
        markQuoteNotServed(quotes.size() - 1);
    }

    /**
     * Mark the quote at location index as already served.
     * @param index
     */
    private static void markQuoteServed(final int index) {
        served.put(index, true);
    }

    /**
     * Mark the quote at location index as not served.
     * @param index
     */
    private static void markQuoteNotServed(final int index) {
        served.put(index, false);
    }

    /**
     * Check if there is a quote that has not been served yet.
     * @return false if there is at least one quote that has not been served yet.
     */
    private static boolean allQuotesAreServed() {
        for(Map.Entry<Integer, Boolean> q : served.entrySet()) {
            if(!q.getValue()) {
                return false;
            }
        }
        return true;
    }

    /**
     * When all quotes have been served, mark all
     * as not served and start serving them all over again.
     */
    private static void markAllQuotesNotServed() {
        for(Map.Entry<Integer, Boolean> q : served.entrySet()) {
            q.setValue(false);
        }
    }
}
