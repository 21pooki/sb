//package com.example.inventory.service;
//
//public class ProductService {
//
//}



package com.example.inventory.service;

import com.example.inventory.model.Product;
import com.example.inventory.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        // Ensure stock is not negative upon creation
        if (product.getQuantityInStock() == null || product.getQuantityInStock() < 0) {
            product.setQuantityInStock(0);
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            // Only update stock if provided and valid, otherwise keep existing
            if (productDetails.getQuantityInStock() != null && productDetails.getQuantityInStock() >= 0) {
                product.setQuantityInStock(productDetails.getQuantityInStock());
            }
            return productRepository.save(product);
        }
        return null; // Or throw an exception for not found
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // --- Specific Inventory Operations ---

    public Product addStock(Long productId, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity to add must be positive.");
        }
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setQuantityInStock(product.getQuantityInStock() + quantity);
            return productRepository.save(product);
        }
        return null; // Product not found
    }

    public Product removeStock(Long productId, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity to remove must be positive.");
        }
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            if (product.getQuantityInStock() < quantity) {
                // Optionally throw an exception for insufficient stock
                throw new IllegalArgumentException("Insufficient stock for product " + product.getName());
            }
            product.setQuantityInStock(product.getQuantityInStock() - quantity);
            return productRepository.save(product);
        }
        return null; // Product not found
    }
}