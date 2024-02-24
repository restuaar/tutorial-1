package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductControllerTest {

  @Mock
  private ProductService productService;

  @Mock
  private Model model;

  @InjectMocks
  private ProductController productController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateProductPage() {
    String expectedViewName = "createproduct";
    String actualViewName = productController.createProductPage(model);
    assertEquals(expectedViewName, actualViewName);
  }

  @Test
  void testCreateProductPost() {
    Product product = new Product();
    String expectedViewName = "redirect:list";
    String actualViewName = productController.createProductPost(product, model);
    assertEquals(expectedViewName, actualViewName);
    verify(productService, times(1)).create(product);
  }

  @Test
  void testProductListPage() {
    List<Product> productList = new ArrayList<>();
    when(productService.findAll()).thenReturn(productList);

    String expectedViewName = "listproduct";
    String actualViewName = productController.productListPage(model);
    assertEquals(expectedViewName, actualViewName);
    verify(model, times(1)).addAttribute("products", productList);
  }

  @Test
  void testDeleteProduct() {
    Product product = new Product();
    String productId = product.getProductId();
    String expectedViewName = "redirect:../list";
    String actualViewName = productController.deleteProduct(productId);
    assertEquals(expectedViewName, actualViewName);
    verify(productService, times(1)).delete(productId);
  }

  @Test
  void testEditProductPage() {
    Product product = new Product();
    String productId = product.getProductId();
    when(productService.findById(productId)).thenReturn(product);

    String expectedViewName = "editproduct";
    String actualViewName = productController.editProductPage(productId, model);
    assertEquals(expectedViewName, actualViewName);
    verify(model, times(1)).addAttribute("product", product);
  }

  @Test
  void testEditProductPost() {
    Product product = new Product();
    String expectedViewName = "redirect:list";
    String actualViewName = productController.editProductPost(product);
    assertEquals(expectedViewName, actualViewName);
    verify(productService, times(1)).update(product);
  }
}
