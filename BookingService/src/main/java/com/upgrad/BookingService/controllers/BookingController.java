package com.upgrad.BookingService.controllers;

import com.upgrad.BookingService.dto.BookingDto;
import com.upgrad.BookingService.dto.PaymentDto;
import com.upgrad.BookingService.entities.BookingInfoEntity;
import com.upgrad.BookingService.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// two Endpoints for the Booking Service Controller
@RestController
@RequestMapping(value = "/hotel") //Any Request to 127.0.0.1:<port>/hotel is mapped/locates this controller class/method
public class BookingController {

    //Injecting Service Class Object
    @Autowired
    private BookingService bookingService;

    // this collects information like fromDate, toDate,aadharNumber,numOfRooms
    // from the user and save it in its database.
    // Any Requests to 127.0.0.1:<port>/hotel/booking is handled here
    // Expects only the media type to be JSON
    // Here bookingDto gets the values passed in the request URL JSON Request body
    // using @RequestBody which maps the JSON values in the URL Requestbody to bookDto

        @PostMapping(value = "/booking", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity createBookingInformation(@RequestBody BookingDto bookingDto) {
        BookingInfoEntity bookingInfoEntity = bookingService.recvBookingInformation(bookingDto);
        return new ResponseEntity(bookingInfoEntity, HttpStatus.CREATED);
    }

    // This endpoint is responsible for taking the payment related details from the user and sending
    // it to the payment service. It gets the transactionId from the Payment service in response and saves
    // it in the booking table.
    // Any Requests to 127.0.0.1:<port>/hotel/booking/<booking ID>/transaction is handled here
    // Expects only the media type to be JSON
    // Here bookingDto gets the values passed in the request URL JSON Request body

    @PostMapping(value = "/booking/{id}/transaction", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity SynchronousRequestForPaymentApproval(@RequestBody PaymentDto transactionDto, @PathVariable(name = "id") int id) {
        BookingInfoEntity bookingInfoEntity = bookingService.createTransactionInfo(transactionDto);
        return new ResponseEntity(bookingInfoEntity, HttpStatus.CREATED);
    }
}