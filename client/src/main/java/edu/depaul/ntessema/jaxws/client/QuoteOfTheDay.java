package edu.depaul.ntessema.jaxws.client;

public class QuoteOfTheDay {

    public static void main(String[] args) {
        QuoteServiceService service = new QuoteServiceService();
        Service port = service.getQuoteServicePort();
        System.out.println(port.serviceMethod());
    }
}
