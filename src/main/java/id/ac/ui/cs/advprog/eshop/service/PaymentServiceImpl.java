package id.ac.ui.cs.advprog.eshop.service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.PaymentCOD;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.PaymentVoucherCode;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;

public class PaymentServiceImpl implements PaymentService {

  @Autowired
  private PaymentRepository paymentRepository;

  @Override
  public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
    Payment payment;

    if (paymentRepository.findById(order.getId()) != null) {
      return null;
    }

    if (isCOD(method)) {
      payment = new PaymentCOD(order.getId(), order, paymentData);
    } else if (isVoucherCode(method)) {
      payment = new PaymentVoucherCode(method, order, paymentData);
    } else {
      throw new IllegalArgumentException();
    }

    paymentRepository.save(payment);
    return payment;
  }

  @Override
  public List<Payment> getAllPayments() {
    return paymentRepository.findAll();
  }

  @Override
  public Payment getPayment(String id) {
    Payment payment = paymentRepository.findById(id);
    if (payment == null) {
      throw new NoSuchElementException();
    }
    return payment;
  }

  @Override
  public Payment setStatus(Payment payment, String status) {
    payment.setStatus(status);

    if (paymentRepository.findById(payment.getId()) == null) {
      throw new NoSuchElementException();
    }

    if (isStatusSuccess(payment.getStatus())) {
      payment.getOrder().setStatus(OrderStatus.SUCCESS.getValue());
    } else if (isStatusRejected(payment.getStatus())) {
      payment.getOrder().setStatus(OrderStatus.FAILED.getValue());
    } else {
      throw new IllegalArgumentException();
    }

    return payment;
  }

  private boolean isCOD(String method) {
    return method.equals(PaymentMethod.CASH_ON_DELIVERY.getValue());
  }

  private boolean isVoucherCode(String method) {
    return method.equals(PaymentMethod.VOUCHER_CODE.getValue());
  }

  private boolean isStatusSuccess(String status) {
    return status.equals(PaymentStatus.SUCCESS.getValue());
  }

  private boolean isStatusRejected(String status) {
    return status.equals(PaymentStatus.REJECTED.getValue());
  }
}