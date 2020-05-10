package edu.depaul.ntessema.jaxws.service;

import edu.depaul.ntessema.jaxws.Event;
import edu.depaul.ntessema.jaxws.Utilities;

import javax.jws.WebService;

@WebService(endpointInterface = "edu.depaul.ntessema.jaxws.service.Service")
public class QuoteService implements Service {

    public QuoteService() {
        Quotes.initialize();
    }

    @Override
    public String getQuote() {
        String selectedQuote = Quotes.getNextQuote();
        Utilities.log(selectedQuote, Event.GET);
        return selectedQuote;
    }

    @Override
    public void addQuote(String q) {
        String quote = Utilities.cleanQuote(q);
        if(!Quotes.quoteExists(quote)) {
            Quotes.add(quote);
            Utilities.log(quote, Event.ADD);
        } else {
            Utilities.log("(duplicate) " + quote, Event.REJECTED);
        }
    }
}
