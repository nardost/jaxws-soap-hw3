package edu.depaul.ntessema.jaxws.publisher;

import edu.depaul.ntessema.jaxws.service.QuoteService;

import javax.xml.ws.Endpoint;

public class Publisher {

    private static final String URL = "http://localhost:8888/quotes";

    public static void main(String[] args) {
        System.out.println("Publishing service at " + URL);
        Endpoint.publish(URL, new QuoteService());
    }
}
