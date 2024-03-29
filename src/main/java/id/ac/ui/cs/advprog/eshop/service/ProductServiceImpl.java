package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.BaseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  private BaseRepository<Product> productRepository;

  @Override
  public Product create(Product product) {
    productRepository.create(product);
    return product;
  }
  
  @Override
  public List<Product> findAll() {
    Iterator<Product> productIterator = productRepository.findAll();
    List<Product> allProduct = new ArrayList<>();
    productIterator.forEachRemaining(allProduct::add);
    return allProduct;
  }
  
  @Override
  public Product findById(String findProductId) {
    return productRepository.findById(findProductId);
  }

  @Override
  public Product update(Product editedProduct) {
    productRepository.update(editedProduct);
    return editedProduct;
  }

  @Override
  public Product delete(String productId) {
    return productRepository.delete(productId);
  }
}
