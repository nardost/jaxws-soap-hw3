package edu.depaul.ntessema.jaxws.client;

/**
 * @author nardos
 *
 * Types of events that can happen on the client window.
 * (1) User types GET abd quote is successfully received from service.
 * (2) User types ADD and quote is successfully sent to service.
 * (3) User types HELP and client responds with information.
 * (4) User types in ADD without the quote argument and client reports error.
 */
enum Status {
    GET_OK,
    ADD_OK,
    INFO,
    ERROR
}
