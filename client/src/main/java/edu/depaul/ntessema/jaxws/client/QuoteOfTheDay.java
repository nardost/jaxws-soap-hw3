package edu.depaul.ntessema.jaxws.client;

import edu.depaul.ntessema.jaxws.client.generated.*;

public class QuoteOfTheDay {

    public static void main(String[] args) {
        QuoteServiceService service = new QuoteServiceService();
        Service port = service.getQuoteServicePort();
        System.out.println(port.serviceMethod());
    }
}
