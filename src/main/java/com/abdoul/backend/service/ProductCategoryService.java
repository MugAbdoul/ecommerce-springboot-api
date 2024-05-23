package com.abdoul.backend.service;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.abdoul.backend.entities.ProductCategory;
import com.abdoul.backend.entities.others.ProductCategoryUpdateRequest;
import com.abdoul.backend.repository.ProductCategoryRepository;

@Service
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public List<ProductCategory> getAllProductCategories() {
        return productCategoryRepository.findAll();
    }

    public ProductCategory getProductCategoryById(UUID id) {
        return productCategoryRepository.findById(id).orElse(null);
    }

    public ProductCategory createProductCategory(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    // public ProductCategory updateProductCategory(UUID id, ProductCategory productCategory) {
    //     if (!productCategoryRepository.existsById(id)) {
    //         return null;
    //     }
    //     productCategory.setId(id);
    //     return productCategoryRepository.save(productCategory);
    // }

    public ProductCategory updateProductCategory(UUID id, ProductCategoryUpdateRequest updateDTO) {
        if (!productCategoryRepository.existsById(id)) {
            return null;
        }
        ProductCategory existingCategory = productCategoryRepository.findById(id).orElse(null);
        if (existingCategory != null) {
            if (updateDTO.getName() != null) {
                existingCategory.setName(updateDTO.getName());
            }
            if (updateDTO.getImage() != null) {
                existingCategory.setImage(updateDTO.getImage());
            }
            return productCategoryRepository.save(existingCategory);
        }
        return null;
    }

    public boolean deleteProductCategory(UUID id) {
        if (!productCategoryRepository.existsById(id)) {
            return false;
        }
        productCategoryRepository.deleteById(id);
        return true;
    }
}
