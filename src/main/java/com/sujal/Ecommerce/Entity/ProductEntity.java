package com.sujal.Ecommerce.Entity;


import jakarta.persistence.*;


@Entity
@Table(name = "Product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long pid;

    @Column(nullable = false, name = "product_name")
    private String pname;

    @Column(nullable = false)
    private String product_description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private float discount_percentage;

    @Column(nullable = false)
    private Double net_price;

    @Column(nullable = false)
    private String category;

//
//    @Column(nullable = false)
//    private String product_img;


    public ProductEntity() {
    }

    public ProductEntity(String pname, String product_description, Double price, float discount_percentage, Double net_price, String category) {
        this.pname = pname;
        this.product_description = product_description;
        this.price = price;
        this.discount_percentage = discount_percentage;
        this.net_price = net_price;
        this.category = category;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public float getDiscount_percentage() {
        return discount_percentage;
    }

    public void setDiscount_percentage(float discount_percentage) {
        this.discount_percentage = discount_percentage;
    }

    public Double getNet_price() {
        return net_price;
    }

    public void setNet_price(Double net_price) {
        this.net_price = net_price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
