package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository implements BaseRepository<Product> {
  private List<Product> productData = new ArrayList<>();

  public Product create(Product product) {
    productData.add(product);
    return product;
  }

  public Iterator<Product> findAll() {
    return productData.iterator();
  }

  public Product findById(String productId) {
    for (Product product : productData) {
      if (product.getProductId().equals(productId)) {
        return product;
      }
    }
    return null;
  }

  public Product update(Product updatedProduct) {
    String updatedProductId = updatedProduct.getProductId();
    Product productInRepository = this.findById(updatedProductId);

    if (productInRepository == null) {
      return null;
    }

    int updatedProductQuantity = updatedProduct.getProductQuantity();
    if (updatedProductQuantity <= 0)
      productInRepository.setProductQuantity(0);
    else
      productInRepository.setProductQuantity(updatedProduct.getProductQuantity());

    productInRepository.setProductName(updatedProduct.getProductName());

    return updatedProduct;
  }

  public Product delete(String productId) {
    Product deletedProduct = this.findById(productId);
    productData.remove(deletedProduct);
    return deletedProduct;
  }
}
