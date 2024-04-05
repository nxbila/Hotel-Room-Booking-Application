package com.upgrad.PaymentService.services;

import com.upgrad.PaymentService.dto.PaymentDto;
import com.upgrad.PaymentService.entities.TransactionDetailsEntity;


public interface PaymentService {
    public int recvPaymentDetails(PaymentDto paymentDTO);
    public TransactionDetailsEntity getTransactionDetails(int transactionId);
}
