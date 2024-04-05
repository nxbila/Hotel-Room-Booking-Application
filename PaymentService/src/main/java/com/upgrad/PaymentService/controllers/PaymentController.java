package com.upgrad.PaymentService.controllers;

import com.upgrad.PaymentService.dto.PaymentDto;
import com.upgrad.PaymentService.entities.TransactionDetailsEntity;
import com.upgrad.PaymentService.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // It takes details such as bookingId, paymentMode,upiId or cardNumber and returns the
    // transactionId automatically generated while storing the details in the ‘transaction’ table.
    // After receiving the transactionId from 'Payment' service, confirmation message is printed on the console.

    // Handle the HTTP POST requests matched with given URI expression.
    // Any Requests to 127.0.0.1:<port>/payment/transaction is handled here
    // Expects only the media type to be JSON
    // Here paymentDto gets the values passed in the request URL JSON Request body
    // using @RequestBody which maps the JSON values in the URL Requestbody to paymentDto

    @PostMapping(value = "/transaction" , consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity transactionConfirmation(@RequestBody PaymentDto paymentDto) {

        int transactionId = paymentService.recvPaymentDetails(paymentDto);

        return new ResponseEntity(transactionId, HttpStatus.CREATED);
    }


    // Handle the GET requests matched with given URI expression.
    // Any Requests to 127.0.0.1:<port>/payment/transaction/<Transaction ID> is handled here
    // Expects only the media type to be JSON
    // The id is the Transaction ID path variable

    @GetMapping(value = "/transaction/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity transactionDetails(@PathVariable(name="id") int id) {

        TransactionDetailsEntity transaction = paymentService.getTransactionDetails(id);

        return  new ResponseEntity(transaction, HttpStatus.OK);}
    }