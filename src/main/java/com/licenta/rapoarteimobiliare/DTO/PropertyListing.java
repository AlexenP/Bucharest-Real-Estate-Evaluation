package com.licenta.rapoarteimobiliare.DTO;

public class PropertyListing {
    private String title;
    private String price;
    private String url;

    public PropertyListing(String title, String price, String url) {
        this.title = title;
        this.price = price;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getUrl() {
        return url;
    }
}
