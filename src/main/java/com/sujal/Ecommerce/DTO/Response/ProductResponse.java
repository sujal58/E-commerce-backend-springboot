package com.sujal.Ecommerce.DTO.Response;

public class ProductResponse {

    private Long pid;

    private String pname;

    private String product_description;

    private Double price;

    private Float discount_percentage;

    private Double net_price;

    private String category;

    public ProductResponse(){}

    public ProductResponse(Long pid, String pname, String product_description, Double price, Float discount_percentage, Double net_price, String category) {
        this.pid = pid;
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

    public Float getDiscount_percentage() {
        return discount_percentage;
    }

    public void setDiscount_percentage(Float discount_percentage) {
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
