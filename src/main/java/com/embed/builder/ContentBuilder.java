package com.embed.builder;

import com.embed.entities.Content;

public class ContentBuilder {

    private String title;
    private String type;
    private String authorName;
    private String authorUrl;
    private String html;
    private String url;
    private Integer width;
    private Integer height;

    private ContentBuilder() {

    }

    public static ContentBuilder Builder() {
        return new ContentBuilder();
    }

    public ContentBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public ContentBuilder withType(String type) {
        this.type = type;
        return this;
    }

    public ContentBuilder withAuthorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public ContentBuilder withAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
        return this;
    }

    public ContentBuilder withHtml(String html) {
        this.html = html;
        return this;
    }

    public ContentBuilder withUrl(String url) {
        this.url = url;
        return this;
    }

    public ContentBuilder withWidth(Integer width) {
        this.width = width;
        return this;
    }

    public ContentBuilder withHeigth(Integer height) {
        this.height = height;
        return this;
    }

    public Content build() {
        Content content = new Content();
        content.setTitle(title);
        content.setAuthorName(authorName);
        content.setAuthorUrl(authorUrl);
        content.setHeight(height);
        content.setWidth(width);
        content.setHtml(html);
        content.setType(type);
        content.setUrl(url);

        return content;
    }
}
