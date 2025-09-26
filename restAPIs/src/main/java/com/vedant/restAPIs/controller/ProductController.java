package com.vedant.restAPIs.controller;

import com.vedant.restAPIs.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private List<Product> productList = new ArrayList<>();

    // GET all products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productList);
    }

    // GET product by id
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productList.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        return product.map(value ->
                    new ResponseEntity<>(value, HttpStatus.valueOf(200))
                )
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.valueOf(404)));
    }

    // GET products by name using RequestParam
    @GetMapping("/search")
    public ResponseEntity<List<Product>> getProductsByName(@RequestParam String name) {
        List<Product> result = new ArrayList<>();
        for (Product p : productList) {
            if (p.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(p);
            }
        }
        return ResponseEntity.ok(result);
    }

    // POST create new product
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        productList.add(product);
        return ResponseEntity.ok(product);
    }

    // PUT update product
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getId().equals(id)) {
                productList.set(i, updatedProduct);
                return ResponseEntity.ok(updatedProduct);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE product
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productList.removeIf(p -> p.getId().equals(id));
        return ResponseEntity.ok("Product deleted successfully");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> patchProduct(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {

        Optional<Product> optionalProduct = productList.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();

        if (optionalProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Product product = optionalProduct.get();

        updates.forEach((key, value) -> {
            try {
                Field field = Product.class.getDeclaredField(key);
                field.setAccessible(true);
                field.set(product, value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace(); // you can log instead of printing
            }
        });

        return ResponseEntity.ok(product);
    }


}
