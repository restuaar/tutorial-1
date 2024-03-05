package id.ac.ui.cs.advprog.eshop.model;

import java.util.Arrays;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Payment {
  String id;
  Order order;
  String method;
  Map<String, String> paymentData;
  String status;

  public Payment(String id, Order order, String method, Map<String, String> paymentData) {
    this.id = id;
    if (order == null) {
      throw new IllegalArgumentException();
    } else {
      this.order = order;
    }

    String[] methodList = { "VOUCHER_CODE", "CASH_ON_DELIVERY" };
    if (Arrays.stream(methodList).noneMatch(item -> item.equals(method))) {
      throw new IllegalArgumentException();
    } else {
      this.method = method;
    }

    this.status = "PENDING";

    if (paymentData == null || paymentData.isEmpty()) {
      throw new IllegalArgumentException();
    }

    if (method.equals("VOUCHER_CODE") && !paymentData.containsKey("voucherCode")) {
      throw new IllegalArgumentException();
    } else if (method.equals("VOUCHER_CODE")) {
      if (paymentData.get("voucherCode") == null || paymentData.get("voucherCode").isEmpty()
          || !paymentData.get("voucherCode").startsWith("ESHOP")
          || numericsInVoucherCounter(paymentData.get("voucherCode")) != 8
          || paymentData.get("voucherCode").length() != 16) {
        setStatus("REJECTED");
      }
    }

    if (method.equals("CASH_ON_DELIVERY") && !paymentData.containsKey("address")
        && !paymentData.containsKey("deliveryFee")) {
      throw new IllegalArgumentException();
    } else if (method.equals("CASH_ON_DELIVERY")) {
      if (paymentData.get("address") == null || paymentData.get("address").isEmpty()
          || paymentData.get("deliveryFee") == null || paymentData.get("deliveryFee").isEmpty()) {
        setStatus("REJECTED");
      }
    }

    this.paymentData = paymentData;
  }

  public Payment(String id, Order order, String method, Map<String, String> paymentData, String status) {
    this(id, order, method, paymentData);
    setStatus(status);
  }

  public void setStatus(String status) {
    String[] statusList = { "PENDING", "REJECTED", "SUCCESS" };
    if (Arrays.stream(statusList).noneMatch(item -> item.equals(status))) {
      throw new IllegalArgumentException();
    }
    this.status = status;
  }

  private long numericsInVoucherCounter(String voucherCode) {
    long numberOfNumerics = 0;

    for (char c : voucherCode.toCharArray()) {
      if (Character.isDigit(c)) {
        numberOfNumerics++;
      }
    }

    return numberOfNumerics;
  }
}