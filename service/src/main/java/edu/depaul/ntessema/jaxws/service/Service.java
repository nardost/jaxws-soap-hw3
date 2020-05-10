package edu.depaul.ntessema.jaxws.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * @author nardos
 *
 * The service endpoint interface that defines the public
 * methods of the service must be annotated with @WebService.
 *
 * The business methods that are exposed to the
 * client must be annotated with @WebMethod.
 */
@WebService
public interface Service {

    @WebMethod
    public String getQuote();

    @WebMethod
    public void addQuote(String quote);
}
