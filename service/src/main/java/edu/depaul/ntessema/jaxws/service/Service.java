package edu.depaul.ntessema.jaxws.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface Service {

    @WebMethod
    public String getQuote();

    @WebMethod
    public void addQuote(String quote);
}
