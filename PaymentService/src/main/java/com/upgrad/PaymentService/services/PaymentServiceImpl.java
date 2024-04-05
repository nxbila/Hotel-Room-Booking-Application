package com.upgrad.PaymentService.services;

import com.upgrad.PaymentService.dao.PaymentDao;
import com.upgrad.PaymentService.dto.PaymentDto;
import com.upgrad.PaymentService.entities.TransactionDetailsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentDao transactionDetailsDao;

    @Override
    public int recvPaymentDetails(PaymentDto paymentDTO) {

        TransactionDetailsEntity transactionDetailsEntity = new TransactionDetailsEntity();

        transactionDetailsEntity.setPaymentMode(paymentDTO.getPaymentMode());
        transactionDetailsEntity.setBookingId(paymentDTO.getBookingId());
        transactionDetailsEntity.setUpiId(paymentDTO.getUpiId());
        transactionDetailsEntity.setCardNumber(paymentDTO.getCardNumber());

        return transactionDetailsDao.save(transactionDetailsEntity).getId();
    }

    @Override
    public TransactionDetailsEntity getTransactionDetails(int transactionId) {

        Optional<TransactionDetailsEntity> transactionDetailsEntity = transactionDetailsDao.findById(transactionId);

        return transactionDetailsEntity.get();
    }
}