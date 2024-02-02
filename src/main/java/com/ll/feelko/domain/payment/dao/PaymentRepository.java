package com.ll.feelko.domain.payment.dao;

import com.ll.feelko.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

}
