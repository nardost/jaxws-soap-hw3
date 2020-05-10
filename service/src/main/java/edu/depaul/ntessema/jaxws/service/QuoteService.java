package edu.depaul.ntessema.jaxws.service;

import edu.depaul.ntessema.jaxws.Event;
import edu.depaul.ntessema.jaxws.Utilities;

import javax.jws.WebService;

/**
 * @author nardos
 *
 * The service endpoint implementation class.
 * There is an explicit service endpoint interface, and it is specified
 * by the endpointInterface property of the @WebService annotation.
 */
@WebService(endpointInterface = "edu.depaul.ntessema.jaxws.service.Service")
public class QuoteService implements Service {

    /*
    * Initialize the class that abstracts
    * the quote access functionality.
    * Future upgrade to a persistent quote service
    * is made less painful by this abstraction of the
    * quote access functionality.
     */
    public QuoteService() {
        Quotes.initialize();
    }

    /*
     * Implement the
     */
    @Override
    public String getQuote() {
        String selectedQuote = Quotes.getQuote();
        Utilities.log(selectedQuote, Event.GET);
        return selectedQuote;
    }

    @Override
    public void addQuote(String q) {
        /*
         * Clean a quote before considering it
         * for addition. The cleaning is just
         * whitespace cleaning, such as removing
         * excess whitespaces... Refer to the method
         * definition of cleanQuote() for details.
         */
        String quote = Utilities.cleanQuote(q);
        /*
         * Add a quote if it does not already
         * exist. Otherwise, reject it. Log the event.
         */
        if(!Quotes.quoteExists(quote)) {
            Quotes.addQuote(quote);
            Utilities.log(quote, Event.ADD);
        } else {
            Utilities.log("(duplicate) " + quote, Event.REJECTED);
        }
    }
}
