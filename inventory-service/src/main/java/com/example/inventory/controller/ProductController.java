//package com.example.inventory.controller;
//
//public class ProductController {
//
//}



package com.example.inventory.controller;

import com.example.inventory.model.Product;
import com.example.inventory.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping // GET http://localhost:8081/api/products
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}") // GET http://localhost:8081/api/products/1
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping // POST http://localhost:8081/api/products
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}") // PUT http://localhost:8081/api/products/1
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product productDetails) {
        Product updatedProduct = productService.updateProduct(id, productDetails);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}") // DELETE http://localhost:8081/api/products/1
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // --- Inventory Specific Endpoints ---

    @PostMapping("/{id}/add-stock") // POST http://localhost:8081/api/products/1/add-stock
    public ResponseEntity<Product> addStock(@PathVariable("id") Long id, @RequestBody Map<String, Integer> request) {
        Integer quantity = request.get("quantity");
        try {
            Product updatedProduct = productService.addStock(id, quantity);
            if (updatedProduct != null) {
                return ResponseEntity.ok(updatedProduct);
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }
    }

    @PostMapping("/{id}/remove-stock") // POST http://localhost:8081/api/products/1/remove-stock
    public ResponseEntity<Product> removeStock(@PathVariable("id") Long id, @RequestBody Map<String, Integer> request) {
        Integer quantity = request.get("quantity");
        try {
            Product updatedProduct = productService.removeStock(id, quantity);
            if (updatedProduct != null) {
                return ResponseEntity.ok(updatedProduct);
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            // For simplicity, distinguishes between bad request (negative quantity)
            // and insufficient stock (which is also an IllegalArgumentException here)
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST); // 400 Bad Request
        }
    }
}