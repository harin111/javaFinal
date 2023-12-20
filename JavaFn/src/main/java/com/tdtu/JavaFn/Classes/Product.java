package com.tdtu.JavaFn.Classes;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String barcode;

    private String productName;

    private Double importPrice;

    private Double retailPrice;

    private String category;

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", barcode='" + barcode + '\'' +
                ", productName='" + productName + '\'' +
                ", importPrice=" + importPrice +
                ", retailPrice=" + retailPrice +
                ", category='" + category + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }

    public Product() {
    }

    public Product(int id, String barcode, String productName, Double importPrice, Double retailPrice, String category, LocalDate creationDate) {
        this.id = id;
        this.barcode = barcode;
        this.productName = productName;
        this.importPrice = importPrice;
        this.retailPrice = retailPrice;
        this.category = category;
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(Double importPrice) {
        this.importPrice = importPrice;
    }

    public Double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }
}
