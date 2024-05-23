package com.abdoul.backend.controller.admin;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.abdoul.backend.entities.ProductCategory;
import com.abdoul.backend.entities.others.ProductCategoryUpdateRequest;
import com.abdoul.backend.service.FileStorageService;
import com.abdoul.backend.service.ProductCategoryService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/admin/product-categories")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;
    private final FileStorageService fileStorageService;

    @Autowired
    public ProductCategoryController(ProductCategoryService productCategoryService, FileStorageService fileStorageService) {
        this.productCategoryService = productCategoryService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping
    public ResponseEntity<List<ProductCategory>> getAllProductCategories() {
        List<ProductCategory> productCategories = productCategoryService.getAllProductCategories();
        return ResponseEntity.ok(productCategories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCategory> getProductCategoryById(@PathVariable UUID id) {
        ProductCategory productCategory = productCategoryService.getProductCategoryById(id);
        if (productCategory == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productCategory);
    }

    @PostMapping
    public ResponseEntity<ProductCategory> createProductCategory(@RequestParam("name") String name, @RequestParam("image") MultipartFile image) {
        

        try {
                String imageUrl = fileStorageService.storeFile(image);
                ProductCategory productCategory = new ProductCategory();
                productCategory.setImage(imageUrl);
                productCategory.setName(name);
                ProductCategory createdProductCategory = productCategoryService.createProductCategory(productCategory);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdProductCategory);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Handle error if file storage fails
            }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductCategory> updateProductCategory(@PathVariable UUID id, @RequestBody ProductCategoryUpdateRequest productCategory) {
        ProductCategory updatedProductCategory = productCategoryService.updateProductCategory(id, productCategory);
        if (updatedProductCategory == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedProductCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductCategory(@PathVariable UUID id) {
        boolean deleted = productCategoryService.deleteProductCategory(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Product category Deleted Successful");
    }
}
