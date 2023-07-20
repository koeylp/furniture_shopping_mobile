package com.bibon.furnitureshopping.models;

public class Category {
    private String _id;
    private String categoryName;
    private String img;

    public Category(String _id, String categoryName, String img) {
        this._id = _id;
        this.categoryName = categoryName;
        this.img = img;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}

