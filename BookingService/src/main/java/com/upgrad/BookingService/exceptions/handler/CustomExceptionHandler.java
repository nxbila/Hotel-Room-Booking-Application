package com.upgrad.BookingService.exceptions.handler;

import com.upgrad.BookingService.exceptions.InvalidBookingIdException;
import com.upgrad.BookingService.exceptions.InvalidPaymentModeException;
import com.upgrad.BookingService.dto.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(InvalidBookingIdException.class)
    public final ResponseEntity<ExceptionDto> handleInvalidBookingIdException(InvalidBookingIdException e){
        ExceptionDto response = new ExceptionDto(e.getMessage(), 400);

        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(InvalidPaymentModeException.class)
    public final ResponseEntity<ExceptionDto> handleInvalidPaymentModeException(InvalidPaymentModeException e){
        ExceptionDto response = new ExceptionDto(e.getMessage(), 400);

        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }
}


