package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

public class PaymentVoucherCode extends Payment {

  public PaymentVoucherCode(String id, Order order, Map<String, String> paymentData) {
    super(id, order, paymentData);
    setMethod(PaymentMethod.VOUCHER_CODE.getValue());
  }

  public PaymentVoucherCode(String id, Order order, Map<String, String> paymentData, String status) {
    super(id, order, paymentData, status);
    setMethod(PaymentMethod.VOUCHER_CODE.getValue());
  }

  @Override
  public void setPaymentData(Map<String, String> paymentData) {
    super.setPaymentData(paymentData);

    if (!paymentData.containsKey("voucherCode")) {
      throw new IllegalArgumentException();
    }

    if (paymentData.get("voucherCode") == null || paymentData.get("voucherCode").isEmpty()) {
      this.setStatus(PaymentStatus.REJECTED.getValue());
      this.paymentData = paymentData;
      return;
    }

    if (!isVoucherValid(paymentData.get("voucherCode"))) {
      this.setStatus(PaymentStatus.REJECTED.getValue());
    }

    this.paymentData = paymentData;
  }

  private boolean isVoucherValid(String voucherCode) {
    return !(voucherCode.length() != 16 ||
        !voucherCode.startsWith("ESHOP") ||
        this.numericsInVoucherCounter(voucherCode) != 8);
  }
}
