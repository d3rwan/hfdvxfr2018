package com.github.d3rwan.hfdvxfr2018.ad;

public class Ad {
    private String title;
    private String description;
    private String imageUrl;
    private String targetUrl;

    public Ad() {
    }

    public Ad(String title, String description, String imageUrl, String targetUrl) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.targetUrl = targetUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }
}
