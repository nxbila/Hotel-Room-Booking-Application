package com.upgrad.PaymentService.dao;

import com.upgrad.PaymentService.entities.TransactionDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDao extends JpaRepository<TransactionDetailsEntity, Integer> {

}
