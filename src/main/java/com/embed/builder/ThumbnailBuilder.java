package com.embed.builder;

import com.embed.entities.Thumbnail;

public class ThumbnailBuilder {

    private String url;
    private Integer width;
    private Integer height;

    private ThumbnailBuilder() {

    }

    public static ThumbnailBuilder Builder() {
        return new ThumbnailBuilder();
    }

    public ThumbnailBuilder withUrl(String url) {
        this.url = url;
        return this;
    }

    public ThumbnailBuilder withWidth(Integer width) {
        this.width = width;
        return this;
    }

    public ThumbnailBuilder withHeigth(Integer height) {
        this.height = height;
        return this;
    }

    public Thumbnail build() {
        Thumbnail thumbnail = new Thumbnail();
        thumbnail.setHeight(height);
        thumbnail.setWidth(width);
        thumbnail.setUrl(url);

        return thumbnail;
    }
}
