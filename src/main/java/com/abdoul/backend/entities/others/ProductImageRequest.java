package com.abdoul.backend.entities.others;

import java.util.UUID;

public class ProductImageRequest {
    private UUID productId;
    private String image;

    
    public ProductImageRequest() {
    }
    
    public UUID getProductId() {
        return productId;
    }
    public void setProductId(UUID productId) {
        this.productId = productId;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
       
}
