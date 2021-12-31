package com.vibhu.nitjsr.jsoupdemo;

public class ParseItem {
    private String imgUrl,title;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ParseItem(String imgUrl, String title) {
        this.imgUrl = imgUrl;
        this.title = title;
    }
}
