package edu.depaul.ntessema.jaxws.service;

import edu.depaul.ntessema.jaxws.Event;
import edu.depaul.ntessema.jaxws.Utilities;

import javax.jws.WebService;

/**
 * @author nardos
 *
 * The service endpoint implementation (SEI) class.
 *
 * Annotated with @WebService to define it as a web service endpoint.
 *
 * There is an explicit service endpoint interface, and it is specified
 * by the endpointInterface element of the @WebService annotation.
 * If the SEI (interface) is not referenced with this annotation,
 * an implicit SEI will be defined. Since I have defined one
 * explicitly, I reference it explicitly next.
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
     * Implement the getQuote method.
     * Get a quote from the Quotes abstraction
     * and log the event.
     */
    @Override
    public String getQuote() {
        String selectedQuote = Quotes.getQuote();
        Utilities.log(selectedQuote, Event.GET);
        return selectedQuote;
    }

    /*
     * Implementation of the addQuote method.
     * Incoming quotes will be cleaned and duplicates will be checked.
     * If a quote already exists, it will be rejected.
     */
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
            Utilities.log("(duplicate) " + quote, Event.REJECT);
        }
    }
}
