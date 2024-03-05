package id.ac.ui.cs.advprog.eshop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.PaymentCOD;
import id.ac.ui.cs.advprog.eshop.model.PaymentVoucherCode;
import id.ac.ui.cs.advprog.eshop.model.Product;

public class PaymentRepositoryTest {
  PaymentRepository paymentRepository;
  List<Payment> payments;

  @BeforeEach
  void setUp() {
    this.paymentRepository = new PaymentRepository();

    List<Product> products = new ArrayList<>();

    Product product1 = new Product();
    product1.setProductId("e45d7d21-fd29-4533-a569-abbe0819579a");
    product1.setProductName("Sampo Cap Bambang");
    product1.setProductQuantity(2);
    products.add(product1);

    Product product2 = new Product();
    product2.setProductId("8a76b99c-a0b3-46d2-a688-4c1831b21119");
    product2.setProductName("Sabun Cap Usep");
    product2.setProductQuantity(1);
    products.add(product2);

    List<Order> orders = new ArrayList<>();
    Order order1 = new Order("13652556-012a-4c07-8b3e-3e3e3e3e3e3e",
        products, 1708560000L, "Safira Sudrajat");
    orders.add(order1);
    Order order2 = new Order("7f9e15bb-3e3e-3e3e-3e3e-3e3e3e3e3e3e",
        products, 1708570000L, "Safira Sudrajat");
    orders.add(order2);
    Order order3 = new Order("e334ef40-3e3e-3e3e-3e3e-3e3e3e3e3e3e",
        products, 1708580000L, "Bambang Sudrajat");
    orders.add(order3);

    this.payments = new ArrayList<>();
    Payment payment1 = new PaymentCOD("11111111-012a-4c07-8b3e-3e3e3e3e3e3e", order1, new HashMap<String, String>(
        Map.of("address", "123 Fake Street", "deliveryFee", "2000")));
    this.payments.add(payment1);

    Payment payment2 = new PaymentVoucherCode("22222222-3e3e-3e3e-3e3e-3e3e3e3e3e3e", order2,
        new HashMap<String, String>(
            Map.of("voucherCode", "ESHOP12345678ABC")));
    this.payments.add(payment2);

    Payment payment3 = new PaymentCOD("33333333-3e3e-3e3e-3e3e-3e3e3e3e3e3e", order3, new HashMap<String, String>(
        Map.of("address", "789 Fake Street", "deliveryFee", "3000")));
    this.payments.add(payment3);
  }

  @Test
  void testSaveCreateVoucher() {
    Payment payment = this.payments.get(1);
    Payment result = this.paymentRepository.save(payment);

    Payment findResult = this.paymentRepository.findById(this.payments.get(1).getId());

    assertEquals(payment.getId(), result.getId());
    assertEquals(payment.getId(), findResult.getId());
    assertEquals(payment.getMethod(), findResult.getMethod());
    assertEquals(payment.getStatus(), findResult.getStatus());
    assertEquals(payment.getOrder(), findResult.getOrder());
    assertEquals(payment.getPaymentData(), findResult.getPaymentData());
  }

  @Test
  void testSaveCreateCOD() {
    Payment payment = this.payments.get(2);
    Payment result = this.paymentRepository.save(payment);

    Payment findResult = this.paymentRepository.findById(this.payments.get(2).getId());

    assertEquals(payment.getId(), result.getId());
    assertEquals(payment.getId(), findResult.getId());
    assertEquals(payment.getMethod(), findResult.getMethod());
    assertEquals(payment.getStatus(), findResult.getStatus());
    assertEquals(payment.getOrder(), findResult.getOrder());
    assertEquals(payment.getPaymentData(), findResult.getPaymentData());
  }

  @Test
  void testSaveEditVoucher() {
    Payment payment = this.payments.get(1);
    this.paymentRepository.save(payment);

    Payment payment2 = new PaymentVoucherCode("22222222-3e3e-3e3e-3e3e-3e3e3e3e3e3e", payment.getOrder(),
        new HashMap<String, String>(Map.of("voucherCode", "ESHOP87654321CBA")));

    Payment result = this.paymentRepository.save(payment2);

    Payment findResult = this.paymentRepository.findById(this.payments.get(1).getId());

    assertEquals(payment2.getId(), result.getId());
    assertEquals(payment2.getId(), findResult.getId());
    assertEquals(payment2.getMethod(), findResult.getMethod());
    assertEquals(payment2.getStatus(), findResult.getStatus());
    assertEquals(payment2.getOrder(), findResult.getOrder());
    assertEquals(payment2.getPaymentData(), findResult.getPaymentData());
  }

  @Test
  void testSaveEditCOD() {
    Payment payment = this.payments.get(2);
    this.paymentRepository.save(payment);

    Payment payment2 = new PaymentCOD("33333333-3e3e-3e3e-3e3e-3e3e3e3e3e3e", payment.getOrder(),
        new HashMap<String, String>(Map.of("address", "1000 Fake Street", "deliveryFee", "10000")));

    Payment result = this.paymentRepository.save(payment2);

    Payment findResult = this.paymentRepository.findById(this.payments.get(2).getId());

    assertEquals(payment2.getId(), result.getId());
    assertEquals(payment2.getId(), findResult.getId());
    assertEquals(payment2.getMethod(), findResult.getMethod());
    assertEquals(payment2.getStatus(), findResult.getStatus());
    assertEquals(payment2.getOrder(), findResult.getOrder());
    assertEquals(payment2.getPaymentData(), findResult.getPaymentData());
  }

  @Test
  void testFindByIdIfIdFound() {
    for (Payment payment : payments) {
      paymentRepository.save(payment);
    }

    Payment findResult = paymentRepository.findById(payments.get(1).getId());

    assertEquals(payments.get(1).getId(), findResult.getId());
    assertEquals(payments.get(1).getMethod(), findResult.getMethod());
    assertEquals(payments.get(1).getStatus(), findResult.getStatus());
    assertEquals(payments.get(1).getOrder(), findResult.getOrder());
    assertEquals(payments.get(1).getPaymentData(), findResult.getPaymentData());
  }

  @Test
  void testFindByIdIfIdNotFound() {
    Payment findResult = paymentRepository.findById("12345678-1234-3e3e-3e3e-3e3e3e3e3e3e");

    assertNull(findResult);
  }

  @Test
  void testFindAll() {
    for (Payment payment : payments) {
      paymentRepository.save(payment);
    }

    List<Payment> findResult = paymentRepository.findAll();

    assertEquals(3, findResult.size());
    assertTrue(findResult.containsAll(payments));
  }
}
