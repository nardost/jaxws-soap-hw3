package edu.depaul.ntessema.jaxws.service;

import javax.jws.WebService;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@WebService(endpointInterface = "edu.depaul.ntessema.jaxws.service.Service")
public class QuoteService implements Service {

    private Map<Integer, Boolean> seenQuotes = new HashMap<>();
    private static int numberOfQuotesSeen;

    public QuoteService() {
        numberOfQuotesSeen = 0;
        for(int i = 0; i < QUOTES.length; i++) {
            seenQuotes.put(i, false);
        }
    }

    @Override
    public String serviceMethod() {
        return nextQuote();
    }

    private String nextQuote() {
        int selectedIndex;
        if(numberOfQuotesSeen == QUOTES.length) {
            clearQuotesSeen();
        }
        do {
            selectedIndex = ThreadLocalRandom.current().nextInt(0, QUOTES.length);
        } while(seenQuotes.get(selectedIndex));
        seenQuotes.put(selectedIndex, true);
        final String selectedQuote = QUOTES[selectedIndex];
        updateSeenQuotes(selectedIndex);
        log(selectedQuote);
        if(numberOfQuotesSeen == QUOTES.length) {
            log("------- all quotes served -------", true);
        }
        return selectedQuote;
    }

    private void updateSeenQuotes(final int seen) {
        numberOfQuotesSeen++;
        seenQuotes.put(seen, true);
    }

    private void clearQuotesSeen() {
        for(Map.Entry<Integer, Boolean> q : seenQuotes.entrySet()) {
            q.setValue(false);
        }
        numberOfQuotesSeen = 0;
    }

    private void log(String message, boolean... noTimestamp) {
        String prefix = "";
        if(noTimestamp.length == 0 || !noTimestamp[0]) {
            Instant instant = Instant.now();
            Timestamp timestamp = Timestamp.from(instant);
            prefix = timestamp.toString().replaceAll("\\.\\d{1,3}", "");
        }
        System.out.println(String.format("%s %s", prefix, message));
    }

    private final static String QUOTES[] = new String[]{
            "There are so many dawns that have not yet broken.",
            "What does not destroy me makes me stronger.",
            "Everything has been figured out, except how to live.",
            "You can’t cross the sea merely by standing and staring at the water.",
            "Man is the only creature who refuses to be what he is."
    };
}
