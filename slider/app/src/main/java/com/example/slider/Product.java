package com.example.slider;

public class Product {
    private String name,price,desc,image;
    public Product(){

    }

    public  Product(String name, String price, String desc, String image){
        this.name=name;
        this.price=price;
        this.desc=desc;
        this.image=image;

    }

    public String getName(){
        return name;
    }

    public String getPrice(){
        return price;
    }

    public String getDesc(){
        return desc;
    }

    public String getImage(){
        return image;
    }



    public void setName(String name){
        this.name = name;
    }

    public void setPrice(String price){
        this.price=price;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
