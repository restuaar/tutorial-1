package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        productData.add(product);
        return product;
    }

    public Product edit(Product editedProduct) {
        String editedProductId = editedProduct.getProductId();
        Product productInRepository = this.findById(editedProductId);
        int indexEditedProduct = productData.indexOf(productInRepository);
        productData.set(indexEditedProduct, editedProduct);
        return editedProduct;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product findById(String findProductId) {
        Iterator<Product> productIterator = this.findAll();
        while (productIterator.hasNext()) {
            Product dataProduct = productIterator.next();
            if (dataProduct.getProductId().equals(findProductId)) {
                return dataProduct;
            }
        }
        return null;
    }
}
