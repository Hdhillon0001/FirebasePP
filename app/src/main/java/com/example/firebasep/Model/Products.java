package com.example.firebasep.Model;

public class Products {
    private String description , image;
 public  Products(){}

    public  Products (String description, String image) {
        this.description = description;
        this.image = image;
    }

    public String getDescription() {
        return description;

    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
