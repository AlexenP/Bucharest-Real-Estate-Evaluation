package com.licenta.rapoarteimobiliare.DTO;

public class PropertyListingDTO {
    private String title;
    private String price;
    private String url;

    public PropertyListingDTO(String title, String price, String url) {
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
