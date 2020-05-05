package edu.depaul.ntessema.jaxws.service;

import javax.jws.WebService;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@WebService(endpointInterface = "edu.depaul.ntessema.jaxws.service.Service")
public class QuoteService implements Service {

    private final static String QUOTES[] = new String[] {
            "There are so many dawns that have not yet broken.",
            "What does not destroy me makes me stronger.",
            "Everything has been figured out, except how to live.",
            "You can’t cross the sea merely by standing and staring at the water.",
            "Man is the only creature who refuses to be what he is."
    };

    private static boolean[] quotesSeen;

    static {
        quotesSeen = new boolean[QUOTES.length];
        for(int i = 0; i < QUOTES.length; i++) {
            quotesSeen[i] = false;
        }
    }

    @Override
    public String serviceMethod() {
        int randomInt = ThreadLocalRandom.current().nextInt(0, QUOTES.length);
        final String selectedQuote = QUOTES[randomInt];
        System.out.println("Serving quote: " + selectedQuote);
        return selectedQuote;
    }
}
