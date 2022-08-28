package com.example.softwareengineering;
import java.io.Serializable;

public class ProductModel implements Serializable{

    private int id;
    private String barcode;
    private String productName;
    private String price;
    private int quantity;

    /**
     * Default Constructor for ProductModel
     * @param id
     * @param productName
     * @param barcode
     * @param price
     * @param quantity
     */



    public ProductModel(int id, String barcode, String productName,String price, int quantity) {
        this.id = id;
        this.barcode = barcode;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public ProductModel() { }

    @Override
    public String toString() {
        int len = this.productName.length() > 15 ? 15 : this.productName.length();
        int spaces = 15 - len;
        String offset = "";
        for(int i = 0 ; i < spaces; i++){
            offset += " ";
        }
        String s = this.productName.substring(0, len) + offset;
        return ""+ s + "  |     " + this.quantity + "     |     " + this.price;

    }

    public String toString1() {
        return "ProductModel{" +
                "id=" + id +
                ", barcode='" + barcode + '\'' +
                ", productName='" + productName + '\'' +
                ", price='" + price + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getId() {
        return id;
    }
    public String getProductName() {
        return productName;
    }
    public String getBarcode() {
        return barcode;
    }
    public String getPrice() {
        return price;
    }
    public int getQuantity() {
        return quantity;
    }

}
