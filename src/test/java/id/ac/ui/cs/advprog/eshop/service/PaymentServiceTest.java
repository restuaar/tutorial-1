package id.ac.ui.cs.advprog.eshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.PaymentCOD;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.model.PaymentVoucherCode;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
  @InjectMocks
  PaymentServiceImpl paymentService;
  @Mock
  PaymentRepository paymentRepository;
  List<Payment> payments;
  Map<String, String> paymentData;

  @BeforeEach
  void setup() {
    List<Product> products = new ArrayList<>();
    Product product1 = new Product();
    product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
    product1.setProductName("Sampo Cap Bambang");
    product1.setProductQuantity(2);
    products.add(product1);

    List<Order> orders = new ArrayList<>();
    Order order1 = new Order("13652556-012a-4c07-8b3e-3e3e3e3e3e3e",
        products, 1708560000L, "Safira Sudrajat");
    orders.add(order1);
    Order order2 = new Order("7f9e15bb-3e3e-3e3e-3e3e-3e3e3e3e3e3e",
        products, 1708570000L, "Safira Sudrajat");
    orders.add(order2);

    this.payments = new ArrayList<>();
    Payment payment1 = new PaymentCOD(order1.getId(), order1, new HashMap<String, String>(
        Map.of("address", "123 Fake Street", "deliveryFee", "2000")));
    this.payments.add(payment1);
    Payment payment2 = new PaymentVoucherCode(order2.getId(), order2,
        new HashMap<String, String>(
            Map.of("voucherCode", "ESHOP12345678ABC")));
    this.payments.add(payment2);
  }

  @Test
  void testAddPaymentVoucher() {
    Payment paymentVoucher = payments.get(1);
    doReturn(paymentVoucher).when(paymentRepository).save(any(Payment.class));

    paymentVoucher = paymentService.addPayment(
        paymentVoucher.getOrder(),
        paymentVoucher.getMethod(),
        paymentVoucher.getPaymentData());

    doReturn(paymentVoucher).when(paymentRepository).findById(paymentVoucher.getId());

    Payment findResult = paymentService.getPayment(paymentVoucher.getId());

    assertEquals(paymentVoucher.getId(), findResult.getId());
    assertEquals(paymentVoucher.getMethod(), findResult.getMethod());
    assertEquals(paymentVoucher.getStatus(), findResult.getStatus());
    assertEquals(paymentVoucher.getOrder(), findResult.getOrder());
    assertEquals(paymentVoucher.getPaymentData(), findResult.getPaymentData());

    verify(paymentRepository, times(1)).save(paymentVoucher);
  }

  @Test
  void testAddPaymentCOD() {
    Payment paymentCOD = payments.get(0);
    doReturn(paymentCOD).when(paymentRepository).save(any(Payment.class));

    paymentCOD = paymentService.addPayment(
        paymentCOD.getOrder(),
        paymentCOD.getMethod(),
        paymentCOD.getPaymentData());

    doReturn(paymentCOD).when(paymentRepository).findById(paymentCOD.getId());

    Payment findResult = paymentService.getPayment(paymentCOD.getId());

    assertEquals(paymentCOD.getId(), findResult.getId());
    assertEquals(paymentCOD.getMethod(), findResult.getMethod());
    assertEquals(paymentCOD.getStatus(), findResult.getStatus());
    assertEquals(paymentCOD.getOrder(), findResult.getOrder());
    assertEquals(paymentCOD.getPaymentData(), findResult.getPaymentData());

    verify(paymentRepository, times(1)).save(paymentCOD);
  }

  @Test
  void testAddPaymentIfAlreadyExists() {
    Payment payment = payments.get(1);
    doReturn(payment).when(paymentRepository).findById(payment.getId());

    assertNull(paymentService.addPayment(payment.getOrder(), payment.getMethod(), payment.getPaymentData()));
  }

  @Test
  void testUpdateStatusVoucherPaymentSuccess() {
    Payment paymentVoucher = payments.get(1);
    doReturn(paymentVoucher).when(paymentRepository).save(any(Payment.class));
    paymentVoucher = paymentService.addPayment(
        paymentVoucher.getOrder(),
        paymentVoucher.getMethod(),
        paymentVoucher.getPaymentData());

    doReturn(paymentVoucher).when(paymentRepository).findById(paymentVoucher.getId());
    Payment result = paymentService.getPayment(paymentVoucher.getId());
    assertEquals(PaymentStatus.PENDING.getValue(), result.getStatus());

    paymentService.setStatus(result, PaymentStatus.SUCCESS.getValue());
    assertEquals(paymentVoucher.getId(), result.getId());
    assertEquals(paymentVoucher.getMethod(), result.getMethod());
    assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
    assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
    assertEquals(OrderStatus.SUCCESS.getValue(), result.getOrder().getStatus());
    verify(paymentRepository, times(1)).save(any(Payment.class));
  }

  @Test
  void testUpdateStatusVoucherPaymentRejected() {
    Payment paymentVoucher = payments.get(1);
    doReturn(paymentVoucher).when(paymentRepository).save(any(Payment.class));
    paymentVoucher = paymentService.addPayment(
        paymentVoucher.getOrder(),
        paymentVoucher.getMethod(),
        paymentVoucher.getPaymentData());

    doReturn(paymentVoucher).when(paymentRepository).findById(paymentVoucher.getId());
    Payment result = paymentService.getPayment(paymentVoucher.getId());
    assertEquals(PaymentStatus.PENDING.getValue(), result.getStatus());

    paymentService.setStatus(result, PaymentStatus.REJECTED.getValue());
    assertEquals(paymentVoucher.getId(), result.getId());
    assertEquals(paymentVoucher.getMethod(), result.getMethod());
    assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
    assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
    assertEquals(OrderStatus.FAILED.getValue(), result.getOrder().getStatus());
    verify(paymentRepository, times(1)).save(any(Payment.class));
  }

  @Test
  void testUpdateStatusCODSuccess() {
    Payment paymentCOD = payments.get(0);
    doReturn(paymentCOD).when(paymentRepository).save(any(Payment.class));
    paymentCOD = paymentService.addPayment(
        paymentCOD.getOrder(),
        paymentCOD.getMethod(),
        paymentCOD.getPaymentData());

    doReturn(paymentCOD).when(paymentRepository).findById(paymentCOD.getId());
    Payment result = paymentService.getPayment(paymentCOD.getId());
    assertEquals(PaymentStatus.PENDING.getValue(), result.getStatus());

    paymentService.setStatus(result, PaymentStatus.SUCCESS.getValue());
    assertEquals(paymentCOD.getId(), result.getId());
    assertEquals(paymentCOD.getMethod(), result.getMethod());
    assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
    assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
    assertEquals(OrderStatus.SUCCESS.getValue(), result.getOrder().getStatus());
    verify(paymentRepository, times(1)).save(any(Payment.class));
  }

  @Test
  void testUpdateStatusCODRejected() {
    Payment paymentCOD = payments.get(0);
    doReturn(paymentCOD).when(paymentRepository).save(any(Payment.class));
    paymentCOD = paymentService.addPayment(
        paymentCOD.getOrder(),
        paymentCOD.getMethod(),
        paymentCOD.getPaymentData());

    doReturn(paymentCOD).when(paymentRepository).findById(paymentCOD.getId());
    Payment result = paymentService.getPayment(paymentCOD.getId());
    assertEquals(PaymentStatus.PENDING.getValue(), result.getStatus());

    paymentService.setStatus(result, PaymentStatus.REJECTED.getValue());
    assertEquals(paymentCOD.getId(), result.getId());
    assertEquals(paymentCOD.getMethod(), result.getMethod());
    assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
    assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
    assertEquals(OrderStatus.FAILED.getValue(), result.getOrder().getStatus());
    verify(paymentRepository, times(1)).save(any(Payment.class));
  }

  @Test
  void testUpdateStatusVoucherInvalidStatus() {
    Payment paymentVoucher = payments.get(1);

    assertThrows(IllegalArgumentException.class, () -> {
      paymentService.setStatus(paymentVoucher, "MEOW");
    });

    verify(paymentRepository, times(0)).save(any(Payment.class));
  }

  @Test
  void testUpdateStatusCODInvalidStatus() {
    Payment paymentCOD = payments.get(1);

    assertThrows(IllegalArgumentException.class, () -> {
      paymentService.setStatus(paymentCOD, "MEOW");
    });

    verify(paymentRepository, times(0)).save(any(Payment.class));
  }

  @Test
  void testUpdateStatusInvalidPaymentId() {
    Payment payment = payments.get(0);
    doReturn(null).when(paymentRepository).findById(payment.getId());
    assertThrows(NoSuchElementException.class, () -> {
      paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());
    });

    verify(paymentRepository, times(0)).save(any(Payment.class));
  }

  @Test
  void testFindByIdIfIdFound() {
    Payment payment = payments.get(1);
    doReturn(payment).when(paymentRepository).findById(payment.getId());

    Payment result = paymentService.getPayment(payment.getId());

    assertEquals(payment.getId(), result.getId());
    assertEquals(payment.getMethod(), result.getMethod());
    assertEquals(payment.getOrder(), result.getOrder());
    assertEquals(payment.getPaymentData(), result.getPaymentData());
    verify(paymentRepository, times(0)).save(any(Payment.class));
  }

  @Test
  void testFindByIdIfIdNotFound() {
    doReturn(null).when(paymentRepository).findById("zczc");
    assertThrows(NoSuchElementException.class, () -> {
      paymentService.getPayment("zczc");
    });

    verify(paymentRepository, times(0)).save(any(Payment.class));
  }

  @Test
  void testGetAllPayments() {
    doReturn(payments).when(paymentRepository).findAll();
    List<Payment> result = paymentService.getAllPayments();
    assertEquals(payments, result);
    verify(paymentRepository, times(0)).save(any(Payment.class));
  }
}