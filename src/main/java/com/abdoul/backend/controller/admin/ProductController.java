package com.abdoul.backend.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.abdoul.backend.entities.Product;
import com.abdoul.backend.entities.ProductCategory;
import com.abdoul.backend.entities.ProductImage;
import com.abdoul.backend.entities.others.ProductRequest;
import com.abdoul.backend.service.ProductCategoryService;
import com.abdoul.backend.service.ProductService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/admin/products")
public class ProductController {

    private final ProductService productService;
    private final ProductCategoryService productCategoryService;

    @Autowired
    public ProductController(ProductService productService, ProductCategoryService productCategoryService) {
        this.productService = productService;
        this.productCategoryService = productCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable UUID id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequest productRequest) {

        ProductCategory productCategory = productCategoryService.getProductCategoryById(productRequest.getCategory());

        if(productCategory != null){
            Product product = new Product();
            product.setAvailable(true);
            product.setDescription(productRequest.getDescription());
            product.setName(productRequest.getName());
            product.setPrice(productRequest.getPrice());
            product.setQuantity(productRequest.getquantity());
            product.setCategory(productCategory);
            Product createdProduct = productService.createProduct(product);
            List<ProductImage> images = new ArrayList<>();
            createdProduct.setImages(images);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        }else{
            return ResponseEntity.notFound().build();
        }
        

    }


    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable UUID id, @RequestBody ProductRequest productRequest) {
        
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        ProductCategory productCategory = productCategoryService.getProductCategoryById(productRequest.getCategory());

        if(productCategory != null){
            product.setAvailable(true);
            product.setDescription(productRequest.getDescription());
            product.setName(productRequest.getName());
            product.setPrice(productRequest.getPrice());
            product.setQuantity(productRequest.getquantity());
            product.setCategory(productCategory);
            Product updatedProduct = productService.updateProduct(id, product);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedProduct);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        boolean deleted = productService.deleteProduct(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> changeProductVisibility(@PathVariable UUID id, @RequestBody Map<String, Boolean> requestBody) {
        Boolean visible = requestBody.get("visible");
        Product product = productService.changeProductVisibility(id, visible);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

}
