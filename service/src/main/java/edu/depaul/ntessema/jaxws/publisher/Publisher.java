package edu.depaul.ntessema.jaxws.publisher;

import edu.depaul.ntessema.jaxws.service.QuoteService;

import javax.xml.ws.Endpoint;

public class Publisher {

    private final static String url = "http://localhost:8888/endpoint";

    public static void main(String[] args) {
        System.out.println("Publishing service at endpoint " + url);
        Endpoint.publish(url, new QuoteService());
    }
}
