package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Product 1");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Product 1");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd7");
        product2.setProductName("Product 2");
        product2.setProductQuantity(200);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindByIdProduct() {
        Product product = new Product();
        product.setProductName("Product 1");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product findedProduct = productRepository.findById(product.getProductId());
        assertEquals(product.getProductId(), findedProduct.getProductId());
        assertEquals(product.getProductName(), findedProduct.getProductName());
        assertEquals(product.getProductQuantity(), findedProduct.getProductQuantity());
    }

    @Test
    void testFindByIdProductIfDoesNotExist() {
        Product product1 = new Product();
        product1.setProductName("Product 1");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductName("Product 2");
        product2.setProductQuantity(200);
        productRepository.create(product2);

        String randomId = UUID.randomUUID().toString();

        Product findedProduct = productRepository.findById(randomId);
        assertNull(findedProduct);
    }

    @Test
    void testFindByIdProductIfMoreThanOneProduct() {
        for (int i = 0; i < 5; i++) {
            Product product = new Product();
            product.setProductName(String.format("Product %d", i + 1));
            product.setProductQuantity(100 + i);
            productRepository.create(product);
        }

        Product lastProduct = new Product();
        lastProduct.setProductName("Last Product");
        lastProduct.setProductQuantity(130);
        productRepository.create(lastProduct);

        Product findedProduct = productRepository.findById(lastProduct.getProductId());
        assertEquals(lastProduct.getProductId(), findedProduct.getProductId());
        assertEquals(lastProduct.getProductName(), findedProduct.getProductName());
        assertEquals(lastProduct.getProductQuantity(), findedProduct.getProductQuantity());
    }

    @Test
    void testEditAndFindByIdProduct() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Product 1");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product findProductById = productRepository.findById(product.getProductId());
        assertEquals(findProductById.getProductId(), product.getProductId());
        assertEquals(findProductById.getProductName(), product.getProductName());
        assertEquals(findProductById.getProductQuantity(), product.getProductQuantity());

        Product editProductData = new Product();
        editProductData.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        editProductData.setProductName("Product 1 Edited");
        editProductData.setProductQuantity(200);
        productRepository.edit(editProductData);

        Product editedProduct = productRepository.findById(editProductData.getProductId());
        assertEquals(editProductData.getProductId(), editedProduct.getProductId());
        assertEquals("Product 1 Edited", editedProduct.getProductName());
        assertEquals(200, editedProduct.getProductQuantity());
    }

    @Test
    void testEditProductIfQuantityPositive() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Product 1");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product findProductById = productRepository.findById(product.getProductId());
        assertEquals(findProductById.getProductId(), product.getProductId());
        assertEquals(findProductById.getProductName(), product.getProductName());
        assertEquals(findProductById.getProductQuantity(), product.getProductQuantity());

        Product editProductData = new Product();
        editProductData.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        editProductData.setProductName("Product 1 Edited");
        editProductData.setProductQuantity(110);
        productRepository.edit(editProductData);

        Product editedProduct = productRepository.findById(editProductData.getProductId());
        assertEquals(editProductData.getProductId(), editedProduct.getProductId());
        assertEquals("Product 1 Edited", editedProduct.getProductName());
        assertEquals(110, editedProduct.getProductQuantity());
    }

    @Test
    void testEditProductIfQuantityNegative() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Product 1");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product findProductById = productRepository.findById(product.getProductId());
        assertEquals(findProductById.getProductId(), product.getProductId());
        assertEquals(findProductById.getProductName(), product.getProductName());
        assertEquals(findProductById.getProductQuantity(), product.getProductQuantity());

        Product editProductData = new Product();
        editProductData.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        editProductData.setProductName("Product 1 Edited");
        editProductData.setProductQuantity(-120);
        productRepository.edit(editProductData);

        Product editedProduct = productRepository.findById(editProductData.getProductId());
        assertEquals(editProductData.getProductId(), editedProduct.getProductId());
        assertEquals("Product 1 Edited", editedProduct.getProductName());
        assertEquals(0, editedProduct.getProductQuantity());
    }

    @Test
    void testDeleteAndFindByIdProduct() {
        Product product = new Product();
        product.setProductName("Product 1");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product deletedProduct = productRepository.delete(product.getProductId());
        assertEquals(product.getProductId(), deletedProduct.getProductId());
        assertEquals(product.getProductName(), deletedProduct.getProductName());
        assertEquals(product.getProductQuantity(), deletedProduct.getProductQuantity());

        Product deletedProductIfSearch = productRepository.findById(product.getProductId());
        assertNull(deletedProductIfSearch);
    }

    @Test
    void testDeleteProductIfEmpty() {
        String randomId = UUID.randomUUID().toString();

        Product deletedProduct = productRepository.delete(randomId);
        assertNull(deletedProduct);
    }

    @Test
    void testDeleteProductIfDoesNotExist() {
        Product product1 = new Product();
        product1.setProductName("Product 1");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductName("Product 2");
        product2.setProductQuantity(200);
        productRepository.create(product2);

        String randomId = UUID.randomUUID().toString();

        Product findedProduct = productRepository.delete(randomId);
        assertNull(findedProduct);
    }
}
