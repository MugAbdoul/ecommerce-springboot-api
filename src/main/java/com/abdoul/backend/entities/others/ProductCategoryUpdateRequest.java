package com.abdoul.backend.entities.others;

public class ProductCategoryUpdateRequest {
    private String name;
    private String image;

    
    public ProductCategoryUpdateRequest() {
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
