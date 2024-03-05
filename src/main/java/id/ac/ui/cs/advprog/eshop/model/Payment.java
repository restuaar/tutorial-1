package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Payment {
  String id;
  Order order;
  Map<String, String> paymentData;
  String status;
  String method;

  @Builder
  public Payment(String id, Order order, Map<String, String> paymentData) {
    this.id = id;
    setOrder(order);
    setStatus(PaymentStatus.PENDING.getValue());
    setPaymentData(paymentData);
  }

  @Builder
  public Payment(String id, Order order, Map<String, String> paymentData, String status) {
    this(id, order, paymentData);
    setStatus(status);
  }

  protected void setOrder(Order order) {
    if (order == null) {
      throw new IllegalArgumentException();
    }
    this.order = order;
  }

  public void setStatus(String status) {
    if (!PaymentStatus.contains(status)) {
      throw new IllegalArgumentException();
    }
    this.status = status;
  }

  protected void setMethod(String method) {
    if (!PaymentMethod.contains(method)) {
      throw new IllegalArgumentException();
    }
    this.method = method;
  }

  protected void setPaymentData(Map<String, String> paymentData) {
    if (paymentData == null || paymentData.isEmpty()) {
      throw new IllegalArgumentException();
    }
    this.paymentData = paymentData;
  }

  protected long numericsInVoucherCounter(String voucherCode) {
    long numberOfNumerics = 0;

    for (char c : voucherCode.toCharArray()) {
      if (Character.isDigit(c)) {
        numberOfNumerics++;
      }
    }

    return numberOfNumerics;
  }
}