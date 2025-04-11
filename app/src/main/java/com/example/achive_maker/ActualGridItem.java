package com.example.achive_maker;

public class ActualGridItem implements GridItem {
    private long id;
    private String imgUrl;

    public ActualGridItem() {
    }

    public ActualGridItem(long id, String imgUrl) {
        this.id = id;
        this.imgUrl = imgUrl;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
