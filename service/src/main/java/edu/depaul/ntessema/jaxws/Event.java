package edu.depaul.ntessema.jaxws;

/**
 * @author nardos
 *
 * Events that happen on the service are
 * (1) Client sends a get request
 * (2) Client sends an add request
 * (3) Client sends an already existing quote
 *     for addition, which will be rejected.
 */
public enum Event {
    GET,
    ADD,
    REJECT,
}
