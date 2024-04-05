package com.upgrad.BookingService.exceptions;

public class InvalidBookingIdException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public InvalidBookingIdException(String message) {super(message);}
}
