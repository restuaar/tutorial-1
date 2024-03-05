package id.ac.ui.cs.advprog.eshop.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PaymentTest {
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

  void setPaymentDataCODValid() {
    this.paymentData.clear();
    this.paymentData.put("address", "Jl. Raya Bogor No. 63, Jakarta");
    this.paymentData.put("deliveryFee", "20000");
  }

  void setPaymentDataCODInvalidEmptyString() {
    this.paymentData.clear();
    this.paymentData.put("address", "");
    this.paymentData.put("deliveryFee", "");
  }

  void setPaymentDataCODInvalidNull() {
    this.paymentData.clear();
    this.paymentData.put("address", null);
    this.paymentData.put("deliveryFee", null);
  }

  @Test
  void testCreatePaymentVoucherEmptyOrder() {
    this.order = null;
    setPaymentDataVoucherValid();

    assertThrows(IllegalArgumentException.class, () -> {
      @SuppressWarnings("unused")
      Payment payment = new Payment("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order, "VOUCHER_CODE",
          this.paymentData);
    });
  }

  @Test
  void testCreatePaymentVoucherEmptyPaymentData() {
    setPaymentDataEmpty();

    assertThrows(IllegalArgumentException.class, () -> {
      @SuppressWarnings("unused")
      Payment payment = new Payment("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order, "VOUCHER_CODE",
          this.paymentData);
    });
  }

  @Test
  void testCreatePaymentVoucherNullPaymentData() {
    this.paymentData = null;

    assertThrows(IllegalArgumentException.class, () -> {
      @SuppressWarnings("unused")
      Payment payment = new Payment("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order, "VOUCHER_CODE",
          this.paymentData);
    });

    this.paymentData = new HashMap<String, String>();
  }

  @Test
  void testCreatePaymentCODEmptyOrder() {
    this.order = null;
    setPaymentDataVoucherValid();

    assertThrows(IllegalArgumentException.class, () -> {
      @SuppressWarnings("unused")
      Payment payment = new Payment("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order, "CASH_ON_DELIVERY",
          this.paymentData);
    });
  }

  @Test
  void testCreatePaymentCODEmptyPaymentData() {
    setPaymentDataEmpty();

    assertThrows(IllegalArgumentException.class, () -> {
      @SuppressWarnings("unused")
      Payment payment = new Payment("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order, "CASH_ON_DELIVERY",
          this.paymentData);
    });
  }

  @Test
  void testCreatePaymentCODNullPaymentData() {
    this.paymentData = null;

    assertThrows(IllegalArgumentException.class, () -> {
      @SuppressWarnings("unused")
      Payment payment = new Payment("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order, "CASH_ON_DELIVERY",
          this.paymentData);
    });

    this.paymentData = new HashMap<String, String>();
  }

  @Test
  void testCreatePaymentVoucherInvalidVoucherCodeCharacterLongToSmall() {
    setPaymentDataVoucherInvalidCharacterLongToSmall();

    Payment payment = new Payment("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order, "VOUCHER_CODE",
        this.paymentData);
    assertEquals("REJECTED", payment.getStatus());
  }

  @Test
  void testCreatePaymentVoucherInvalidVoucherCodeCharacterLongToLong() {
    setPaymentDataVoucherInvalidCharacterLongToLong();

    Payment payment = new Payment("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order, "VOUCHER_CODE",
        this.paymentData);
    assertEquals("REJECTED", payment.getStatus());
  }

  @Test
  void testCreatePaymentVoucherInvalidVoucherCodeStartWith() {
    setPaymentDataVoucherInvalidStartWith();

    Payment payment = new Payment("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order, "VOUCHER_CODE",
        this.paymentData);
    assertEquals("REJECTED", payment.getStatus());
  }

  @Test
  void testCreatePaymentVoucherInvalidVoucherCodeContains8NumericalCharacter() {
    setPaymentDataVoucherInvalidContains8NumericalCharacter();

    Payment payment = new Payment("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order, "VOUCHER_CODE",
        this.paymentData);
    assertEquals("REJECTED", payment.getStatus());
  }

  @Test
  void testCreatePaymentCODInvalidEmptyString() {
    setPaymentDataCODInvalidEmptyString();

    Payment payment = new Payment("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order, "CASH_ON_DELIVERY",
        this.paymentData);
    assertEquals("REJECTED", payment.getStatus());
  }

  @Test
  void testCreatePaymentCODInvalidNull() {
    setPaymentDataCODInvalidNull();

    Payment payment = new Payment("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order, "CASH_ON_DELIVERY",
        this.paymentData);
    assertEquals("REJECTED", payment.getStatus());
  }

  @Test
  void testCreatePaymentVoucherDefaultStatus() {
    setPaymentDataVoucherValid();

    Payment payment = new Payment("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order, "VOUCHER_CODE", this.paymentData);
    assertSame(this.order, payment.getOrder());
    assertEquals("13652556-012a-4c07-8b3e-3e3e3e3e3e3e", payment.getOrder().getId());
    assertEquals(1708560000L, payment.getOrder().getOrderTime());
    assertEquals("Safira Sudrajat", payment.getOrder().getAuthor());

    assertEquals("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", payment.getId());
    assertEquals("VOUCHER_CODE", payment.getMethod());
    assertEquals("PENDING", payment.getStatus());
    assertSame(this.paymentData, payment.getPaymentData());
    assertEquals("ESHOP1234ABC5678", payment.getPaymentData().get("voucherCode"));
  }

  @Test
  void testCreatePaymentCODDefaultStatus() {
    setPaymentDataCODValid();

    Payment payment = new Payment("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order, "CASH_ON_DELIVERY",
        this.paymentData);
    assertSame(this.order, payment.getOrder());
    assertEquals("13652556-012a-4c07-8b3e-3e3e3e3e3e3e", payment.getOrder().getId());
    assertEquals(1708560000L, payment.getOrder().getOrderTime());
    assertEquals("Safira Sudrajat", payment.getOrder().getAuthor());

    assertEquals("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", payment.getId());
    assertEquals("CASH_ON_DELIVERY", payment.getMethod());
    assertEquals("PENDING", payment.getStatus());
    assertSame(this.paymentData, payment.getPaymentData());
    assertEquals("Jl. Raya Bogor No. 63, Jakarta", payment.getPaymentData().get("address"));
    assertEquals("20000", payment.getPaymentData().get("deliveryFee"));
  }

  @Test
  void testCreatePaymentVoucherSuccessStatus() {
    setPaymentDataVoucherValid();

    Payment payment = new Payment("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order, "VOUCHER_CODE",
        this.paymentData, "SUCCESS");
    assertEquals("SUCCESS", payment.getStatus());
  }

  @Test
  void testCreatePaymentVoucherInvalidStatus() {
    setPaymentDataVoucherValid();

    assertThrows(IllegalArgumentException.class, () -> {
      @SuppressWarnings("unused")
      Payment payment = new Payment("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order, "VOUCHER_CODE",
          this.paymentData, "MEOW");
    });
  }

  @Test
  void testCreatePaymentCODSuccessStatus() {
    setPaymentDataCODValid();

    Payment payment = new Payment("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order, "CASH_ON_DELIVERY",
        this.paymentData, "SUCCESS");
    assertEquals("SUCCESS", payment.getStatus());
  }

  @Test
  void testCreatePaymentCODInvalidStatus() {
    setPaymentDataVoucherValid();

    assertThrows(IllegalArgumentException.class, () -> {
      @SuppressWarnings("unused")
      Payment payment = new Payment("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order, "VOUCHER_CODE",
          this.paymentData, "MEOW");
    });
  }

  @Test
  void testSetStatusPaymentVoucherToRejected() {
    setPaymentDataVoucherValid();

    Payment payment = new Payment("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order, "VOUCHER_CODE",
        this.paymentData);
    payment.setStatus("REJECTED");
    assertEquals("REJECTED", payment.getStatus());
  }

  @Test
  void testSetStatusPaymentVoucherInvalidStatus() {
    setPaymentDataVoucherValid();

    Payment payment = new Payment("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order, "VOUCHER_CODE",
        this.paymentData);
    assertThrows(IllegalArgumentException.class, () -> {
      payment.setStatus("MEOW");
    });
  }

  @Test
  void testSetStatusPaymentCODToRejected() {
    setPaymentDataCODValid();

    Payment payment = new Payment("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order, "CASH_ON_DELIVERY",
        this.paymentData);
    payment.setStatus("REJECTED");
    assertEquals("REJECTED", payment.getStatus());
  }

  @Test
  void testSetStatusPaymentCODInvalidStatus() {
    setPaymentDataCODValid();

    Payment payment = new Payment("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order, "CASH_ON_DELIVERY",
        this.paymentData);
    assertThrows(IllegalArgumentException.class, () -> {
      payment.setStatus("MEOW");
    });
  }
}
