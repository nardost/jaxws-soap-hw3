package edu.depaul.ntessema.jaxws.service;

import javax.jws.WebService;
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
        final int selected = selectQuote();
        final String selectedQuote = QUOTES[selected];
        seenQuotes.put(selected, true);
        updateSeenQuotes(selected);
        return selectedQuote;
    }

    private int selectQuote() {
        int randomInt;
        if(numberOfQuotesSeen == QUOTES.length) {
            clearQuotesSeen();
        }
        do {
            randomInt = ThreadLocalRandom.current().nextInt(0, QUOTES.length);
        } while(seenQuotes.get(randomInt));
        return randomInt;
    }

    private void updateSeenQuotes(final int seen) {
        numberOfQuotesSeen++;
        seenQuotes.put(seen, true);
        System.out.println("Serving quote: " + QUOTES[seen]);
        if(numberOfQuotesSeen == QUOTES.length) {
            System.out.println("All quotes seen.");
        }
    }

    private void clearQuotesSeen() {
        for(Map.Entry<Integer, Boolean> q : seenQuotes.entrySet()) {
            q.setValue(false);
        }
        numberOfQuotesSeen = 0;
    }

    private final static String QUOTES[] = new String[]{
            "There are so many dawns that have not yet broken.",
            "What does not destroy me makes me stronger.",
            "Everything has been figured out, except how to live.",
            "You can’t cross the sea merely by standing and staring at the water.",
            "Man is the only creature who refuses to be what he is."
    };
}
