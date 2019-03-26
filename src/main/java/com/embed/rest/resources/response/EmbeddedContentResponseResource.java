package com.embed.rest.resources.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude (JsonInclude.Include.NON_EMPTY)
public class EmbeddedContentResponseResource {

    private String version;

    private EmbeddedProviderResource provider;

    @JsonIgnore
    private ContentResponseResource content;

    private ThumbnailResponseResource thumbnail;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @JsonProperty("object")
    public ContentResponseResource getContent() {
        return content;
    }

    @JsonProperty("object")
    public void setContent(ContentResponseResource content) {
        this.content = content;
    }

    public ThumbnailResponseResource getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(ThumbnailResponseResource thumbnail) {
        this.thumbnail = thumbnail;
    }


    public EmbeddedProviderResource getProvider() {
        return provider;
    }

    public void setProvider(EmbeddedProviderResource provider) {
        this.provider = provider;
    }
}
