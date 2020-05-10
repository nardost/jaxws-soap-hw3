package edu.depaul.ntessema.jaxws.publisher;

import edu.depaul.ntessema.jaxws.Utilities;
import edu.depaul.ntessema.jaxws.service.QuoteService;

import javax.xml.ws.Endpoint;

/**
 * @author nardos
 *
 * This class publishes the web service
 * at the URL http://localhost:8888/quotes
 *
 * Almost all of it is boilerplate code so there
 * is not much whys to comment about.
 */
public class Publisher {

    /*
     * The URL is hard coded here, which does not look alright to me.
     * There should be a way to get the URL dynamically since it is in the WSDL.
     * I don't know if it is the right approach to parse the WSDL,
     * which is an ordinary xml file in the classpath, and extract
     * the URL at run time. So, I leave it hard coded for now.
     */

    private static final String URL = "http://localhost:8888/quotes";

    public static void main(String[] args) {
        /*
         * Display an ASCII logo.
         */
        Utilities.displayLogo(URL);
        /*
         * Create the implementor of the service and
         * publish the service at the specified URL.
         */
        Endpoint.publish(URL, new QuoteService());
    }
}
