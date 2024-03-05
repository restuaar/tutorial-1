package id.ac.ui.cs.advprog.eshop.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

public class PaymentVoucherCodeTest {
  private List<Product> products;
  private Order order;

  private Map<String, String> paymentData = new HashMap<String, String>();

  @BeforeEach
  void setUp() {
    this.products = new ArrayList<>();
    Product product1 = new Product();
    product1.setProductId("e45d7d21-fd29-4533-a569-abbe0819579a");
    product1.setProductName("Sampo Cap Bambang");
    product1.setProductQuantity(2);

    Product product2 = new Product();
    product2.setProductId("8a76b99c-a0b3-46d2-a688-4c1831b21119");
    product2.setProductName("Sabun Cap Usep");
    product2.setProductQuantity(1);

    this.products.add(product1);
    this.products.add(product2);
    order = new Order("13652556-012a-4c07-8b3e-3e3e3e3e3e3e",
        this.products, 1708560000L, "Safira Sudrajat");
  }

  void setPaymentDataEmpty() {
    this.paymentData.clear();
  }

  void setPaymentDataVoucherValid() {
    this.paymentData.clear();
    this.paymentData.put("voucherCode", "ESHOP1234ABC5678");
  }

  void setPaymentDataVoucherInvalidCharacterLongToSmall() {
    this.paymentData.clear();
    this.paymentData.put("voucherCode", "ESHOP");
  }

  void setPaymentDataVoucherInvalidCharacterLongToLong() {
    this.paymentData.clear();
    this.paymentData.put("voucherCode", "ESHOP1234ABC56789");
  }

  void setPaymentDataVoucherInvalidStartWith() {
    this.paymentData.put("voucherCode", "MEOWW1234ABC5678");
  }

  void setPaymentDataVoucherInvalidContains8NumericalCharacter() {
    this.paymentData.clear();
    this.paymentData.put("voucherCode", "ESHOP1234ABC");
  }

  void setPaymentDataVoucherInvalidNull() {
    this.paymentData.clear();
    this.paymentData.put("voucherCode", null);
  }

  void setPaymentDataVoucherInvalidEmpty() {
    this.paymentData.clear();
    this.paymentData.put("voucherCode", "");
  }

  void setPaymentDataVoucherInvalidArgument() {
    this.paymentData.clear();
    this.paymentData.put("wow", "ESHOP1234ABC5678");
  }

  @Test
  void testCreatePaymentVoucherEmptyOrder() {
    this.order = null;
    setPaymentDataVoucherValid();

    assertThrows(IllegalArgumentException.class, () -> {
      @SuppressWarnings("unused")
      Payment payment = new PaymentVoucherCode("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order,
          this.paymentData);
    });
  }

  @Test
  void testCreatePaymentVoucherEmptyPaymentData() {
    setPaymentDataEmpty();

    assertThrows(IllegalArgumentException.class, () -> {
      @SuppressWarnings("unused")
      Payment payment = new PaymentVoucherCode("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order,
          this.paymentData);
    });
  }

  @Test
  void testCreatePaymentVoucherNullPaymentData() {
    this.paymentData = null;

    assertThrows(IllegalArgumentException.class, () -> {
      @SuppressWarnings("unused")
      Payment payment = new PaymentVoucherCode("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order,
          this.paymentData);
    });

    this.paymentData = new HashMap<String, String>();
  }


  @Test
  void testCreatePaymentVoucherInvalidVoucherCodeCharacterLongToSmall() {
    setPaymentDataVoucherInvalidCharacterLongToSmall();

    Payment payment = new PaymentVoucherCode("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order,
        this.paymentData);
    assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
  }

  @Test
  void testCreatePaymentVoucherInvalidVoucherCodeCharacterLongToLong() {
    setPaymentDataVoucherInvalidCharacterLongToLong();

    Payment payment = new PaymentVoucherCode("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order,
        this.paymentData);
    assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
  }

  @Test
  void testCreatePaymentVoucherInvalidVoucherCodeStartWith() {
    setPaymentDataVoucherInvalidStartWith();

    Payment payment = new PaymentVoucherCode("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order,
        this.paymentData);
    assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
  }

  @Test
  void testCreatePaymentVoucherInvalidVoucherCodeContains8NumericalCharacter() {
    setPaymentDataVoucherInvalidContains8NumericalCharacter();

    Payment payment = new PaymentVoucherCode("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order,
        this.paymentData);
    assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
  }

  @Test
  void testCreatePaymentVoucherInvalidNull() {
    setPaymentDataVoucherInvalidNull();

    Payment payment = new PaymentVoucherCode("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order,
        this.paymentData);
    assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
  }

  @Test
  void testCreatePaymentVoucherInvalidEmptyString() {
    setPaymentDataVoucherInvalidEmpty();

    Payment payment = new PaymentVoucherCode("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order,
        this.paymentData);
    assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
  }

  @Test
  void testCreatePaymentVoucherInvalidArgument() {
    setPaymentDataVoucherInvalidArgument();

    assertThrows(IllegalArgumentException.class, () -> {
      @SuppressWarnings("unused")
      Payment payment = new PaymentVoucherCode("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order,
          this.paymentData);
    });
  }

  @Test
  void testCreatePaymentVoucherDefaultStatus() {
    setPaymentDataVoucherValid();

    Payment payment = new PaymentVoucherCode("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order,
        this.paymentData);
    assertSame(this.order, payment.getOrder());
    assertEquals("13652556-012a-4c07-8b3e-3e3e3e3e3e3e", payment.getOrder().getId());
    assertEquals(1708560000L, payment.getOrder().getOrderTime());
    assertEquals("Safira Sudrajat", payment.getOrder().getAuthor());

    assertEquals("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", payment.getId());
    assertEquals(PaymentMethod.VOUCHER_CODE.getValue(), payment.getMethod());
    assertEquals(PaymentStatus.PENDING.getValue(), payment.getStatus());
    assertSame(this.paymentData, payment.getPaymentData());
    assertEquals("ESHOP1234ABC5678", payment.getPaymentData().get("voucherCode"));
  }

  @Test
  void testCreatePaymentVoucherSuccessStatus() {
    setPaymentDataVoucherValid();

    Payment payment = new PaymentVoucherCode("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order,
        this.paymentData, PaymentStatus.SUCCESS.getValue());
    assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
  }

  @Test
  void testCreatePaymentVoucherInvalidStatus() {
    setPaymentDataVoucherValid();

    assertThrows(IllegalArgumentException.class, () -> {
      @SuppressWarnings("unused")
      Payment payment = new PaymentVoucherCode("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order,
          this.paymentData, "MEOW");
    });
  }

  @Test
  void testSetStatusPaymentVoucherToRejected() {
    setPaymentDataVoucherValid();

    Payment payment = new PaymentVoucherCode("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order,
        this.paymentData);
    payment.setStatus(PaymentStatus.REJECTED.getValue());
    assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
  }

  @Test
  void testSetStatusPaymentVoucherInvalidStatus() {
    setPaymentDataVoucherValid();

    Payment payment = new PaymentVoucherCode("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order,
        this.paymentData);
    assertThrows(IllegalArgumentException.class, () -> {
      payment.setStatus("MEOW");
    });
  }
}
