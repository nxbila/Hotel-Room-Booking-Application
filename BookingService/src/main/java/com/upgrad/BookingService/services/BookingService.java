package com.upgrad.BookingService.services;

import com.upgrad.BookingService.dto.BookingDto;
import com.upgrad.BookingService.dto.PaymentDto;
import com.upgrad.BookingService.entities.BookingInfoEntity;


public interface BookingService {
    public BookingInfoEntity recvBookingInformation(BookingDto bookingDTO);
    public BookingInfoEntity createTransactionInfo(PaymentDto transactionDto);
}
