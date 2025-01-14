package com.sujal.Ecommerce.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;


@Entity
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long pid;

    @Column(nullable = false, name = "product_name")
    private String pname;

    @Column(nullable = false, name = "product_description")
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false, name = "discount_percentage")
    private Float discount;

    @Column(nullable = false, name = "net_price")
    private Double netPrice;

    @Column(nullable = false, name = "product_img")
    private String image;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;


    public Product() {
    }

    public Product(String pname,
                   String description,
                   Double price,
                   Float discount,
                   Double netPrice,
                   Category category,
                   String image
                   ) {
        this.pname = pname;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.netPrice = netPrice;
        this.image = image;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Double getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(Double netPrice) {
        this.netPrice = netPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
