package edu.depaul.ntessema.jaxws.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * @author nardos
 *
 * The interface that defines the public methods of the service.
 */
@WebService
public interface Service {

    @WebMethod
    public String getQuote();

    @WebMethod
    public void addQuote(String quote);
}
