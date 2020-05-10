package edu.depaul.ntessema.jaxws.publisher;

import edu.depaul.ntessema.jaxws.Utilities;
import edu.depaul.ntessema.jaxws.service.QuoteService;

import javax.xml.ws.Endpoint;

public class Publisher {

    private static final String URL = "http://localhost:8888/quotes";

    public static void main(String[] args) {
        Utilities.displayLogo(URL);
        Endpoint.publish(URL, new QuoteService());
    }
}
