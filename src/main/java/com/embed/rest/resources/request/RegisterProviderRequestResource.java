package com.embed.rest.resources.request;

import java.util.List;

public class RegisterProviderRequestResource {

    private String providerName;
    private String apiEndpoint;
    private List<String> urlSchemes;

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getApiEndpoint() {
        return apiEndpoint;
    }

    public void setApiEndpoint(String apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
    }

    public List<String> getUrlSchemes() {
        return urlSchemes;
    }

    public void setUrlSchemes(List<String> urlSchemes) {
        this.urlSchemes = urlSchemes;
    }
}
