package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

public class PaymentCOD extends Payment {
  public PaymentCOD(String id, Order order, Map<String, String> paymentData) {
    super(id, order, paymentData);
    setMethod(PaymentMethod.CASH_ON_DELIVERY.getValue());
  }

  public PaymentCOD(String id, Order order, Map<String, String> paymentData, String status) {
    super(id, order, paymentData, status);
    setMethod(PaymentMethod.CASH_ON_DELIVERY.getValue());
  }

  protected void setPaymentData(Map<String, String> paymentData) {
    super.setPaymentData(paymentData);

    if (!paymentData.containsKey("address") || !paymentData.containsKey("deliveryFee")) {
      throw new IllegalArgumentException();
    }

    if (paymentData.get("address") == null || paymentData.get("address").isEmpty()) {
      setStatus(PaymentStatus.REJECTED.getValue());
    }

    if (paymentData.get("deliveryFee") == null || paymentData.get("deliveryFee").isEmpty()) {
      setStatus(PaymentStatus.REJECTED.getValue());
    }

    this.paymentData = paymentData;
  }
}
