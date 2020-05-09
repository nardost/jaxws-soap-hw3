package edu.depaul.ntessema.jaxws.client;

import java.util.stream.Stream;

public class QuoteOfTheDay {

    private static final String[] MORE_QUOTES = new String[] {
            "The butterfly counts not months but moments, and has time enough.",
            "The soul is healed by being with children.",
            "Taking a new step, uttering a new word, is what people fear most.",
            "Hell is other people!",
            "We are our choices."
    };

    public static void main(String[] args) {
        QuoteServiceService service = new QuoteServiceService();
        Service port = service.getQuoteServicePort();
        Stream.of(MORE_QUOTES).forEach(q -> port.addQuote(q));
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(500L);
                System.out.println(port.getQuote());
            } catch (InterruptedException ie) {
            }
        }
    }
}
