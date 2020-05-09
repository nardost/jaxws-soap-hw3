package edu.depaul.ntessema.jaxws.service;

import javax.jws.WebService;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebService(endpointInterface = "edu.depaul.ntessema.jaxws.service.Service")
public class QuoteService implements Service {

    private Map<Integer, Boolean> served = new HashMap<>();

    public QuoteService() {
        for(int i = 0; i < quotes.size(); i++) {
            served.put(i, false);
        }
    }

    @Override
    public String getQuote() {
        return nextQuote();
    }

    @Override
    public void addQuote(String quote) {
        if(!QUOTES.containsKey(quote.toLowerCase().hashCode())) {
            quotes.add(quote);
            QUOTES.put(quote.toLowerCase().hashCode(), quote);
            served.put(quotes.size() - 1, false);
            log(quote, Event.ADD);
        } else {
            log(quote, Event.REJECTED);
        }
    }

    private String nextQuote() {
        int selectedIndex;
        if(allQuotesServed()) {
            clearQuotesSeen();
        }
        do {
            selectedIndex = ThreadLocalRandom.current().nextInt(0, quotes.size());
        } while(served.get(selectedIndex));
        served.put(selectedIndex, true);
        final String selectedQuote = quotes.get(selectedIndex);
        updateSeenQuotes(selectedIndex);
        log(selectedQuote, Event.GET);
        return selectedQuote;
    }

    private void updateSeenQuotes(final int seen) {
        served.put(seen, true);
    }

    private boolean allQuotesServed() {
        for(Map.Entry<Integer, Boolean> q : served.entrySet()) {
            if(!q.getValue()) {
                return false;
            }
        }
        return true;
    }

    private void clearQuotesSeen() {
        for(Map.Entry<Integer, Boolean> q : served.entrySet()) {
            q.setValue(false);
        }
    }

    private void log(String message, final Event event, String... options) {
        String timestamp = "";
        if(options.length == 0 || Arrays.asList(options).indexOf("noTimestamp") < 0) {
            Instant instant = Instant.now();
            timestamp = Timestamp.from(instant).toString().replaceAll("\\.\\d{1,3}", "");
        }
        System.out.println(String.format("%19s %3s %s", timestamp, event, message));
    }

    /*
     * The Quotes Silo
     */
    private static List<String> quotes = Stream.of(new String[] {
            "There are so many dawns that have not yet broken.",
            "What does not destroy me makes me stronger.",
            "Everything has been figured out, except how to live.",
            "You can’t cross the sea merely by standing and staring at the water.",
            "Man is the only creature who refuses to be what he is."
    }).collect(Collectors.toList());

    private static Map<Integer, String> QUOTES = quotes.stream().collect(Collectors.toMap(q -> q.toLowerCase().hashCode(), q -> q));
}
