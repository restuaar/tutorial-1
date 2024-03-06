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

public class PaymentCODTest {
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

  void setPaymentDataCODInvalidArgument() {
    this.paymentData.clear();
    this.paymentData.put("wow", "Jl. Raya Bogor No. 63, Jakarta");
    this.paymentData.put("deliveryFee", "20000");
  }

  @Test
  void testCreatePaymentCODEmptyOrder() {
    this.order = null;
    setPaymentDataCODValid();

    assertThrows(IllegalArgumentException.class, () -> {
      @SuppressWarnings("unused")
      Payment payment = new PaymentCOD("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order,
          this.paymentData);
    });
  }

  @Test
  void testCreatePaymentCODEmptyPaymentData() {
    setPaymentDataEmpty();

    assertThrows(IllegalArgumentException.class, () -> {
      @SuppressWarnings("unused")
      Payment payment = new PaymentCOD("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order,
          this.paymentData);
    });
  }

  @Test
  void testCreatePaymentCODNullPaymentData() {
    this.paymentData = null;

    assertThrows(IllegalArgumentException.class, () -> {
      @SuppressWarnings("unused")
      Payment payment = new PaymentCOD("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order,
          this.paymentData);
    });

    this.paymentData = new HashMap<String, String>();
  }

  @Test
  void testCreatePaymentCODInvalidEmptyString() {
    setPaymentDataCODInvalidEmptyString();

    Payment payment = new PaymentCOD("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order,
        this.paymentData);
    assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
  }

  @Test
  void testCreatePaymentCODInvalidNull() {
    setPaymentDataCODInvalidNull();

    Payment payment = new PaymentCOD("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order,
        this.paymentData);
    assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
  }

  @Test
  void testCreatePaymentCODInvalidArgument() {
    setPaymentDataCODInvalidArgument();

    assertThrows(IllegalArgumentException.class, () -> {
      @SuppressWarnings("unused")
      Payment payment = new PaymentCOD("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order,
          this.paymentData);
    });
  }

  @Test
  void testCreatePaymentCODDefaultStatus() {
    setPaymentDataCODValid();

    Payment payment = new PaymentCOD("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order,
        this.paymentData);
    assertSame(this.order, payment.getOrder());
    assertEquals("13652556-012a-4c07-8b3e-3e3e3e3e3e3e", payment.getOrder().getId());
    assertEquals(1708560000L, payment.getOrder().getOrderTime());
    assertEquals("Safira Sudrajat", payment.getOrder().getAuthor());

    assertEquals("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", payment.getId());
    assertEquals(PaymentMethod.CASH_ON_DELIVERY.getValue(), payment.getMethod());
    assertEquals(PaymentStatus.PENDING.getValue(), payment.getStatus());
    assertSame(this.paymentData, payment.getPaymentData());
    assertEquals("Jl. Raya Bogor No. 63, Jakarta", payment.getPaymentData().get("address"));
    assertEquals("20000", payment.getPaymentData().get("deliveryFee"));
  }

  @Test
  void testCreatePaymentCODSuccessStatus() {
    setPaymentDataCODValid();

    Payment payment = new PaymentCOD("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order, this.paymentData,
        PaymentStatus.SUCCESS.getValue());
    assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
  }

  @Test
  void testCreatePaymentCODInvalidStatus() {
    setPaymentDataCODValid();

    assertThrows(IllegalArgumentException.class, () -> {
      @SuppressWarnings("unused")
      Payment payment = new PaymentCOD("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order,
          this.paymentData, "MEOW");
    });
  }

  @Test
  void testSetStatusPaymentCODToRejected() {
    setPaymentDataCODValid();

    Payment payment = new PaymentCOD("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order, this.paymentData);
    payment.setStatus(PaymentStatus.REJECTED.getValue());
    assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
  }

  @Test
  void testSetStatusPaymentCODInvalidStatus() {
    setPaymentDataCODValid();

    Payment payment = new PaymentCOD("16666666-012a-4c07-8b3e-3e3e3e3e3e3e", this.order, this.paymentData);
    assertThrows(IllegalArgumentException.class, () -> {
      payment.setStatus("MEOW");
    });
  }
}
