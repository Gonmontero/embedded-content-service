package com.embed.rest.resources.response;

import java.util.Set;

public class ProviderResponseResource {


    private String name;

    private String apiEndpoint;

    private Set<String> urlSchema;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApiEndpoint() {
        return apiEndpoint;
    }

    public void setApiEndpoint(String apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
    }

    public Set<String> getUrlSchema() {
        return urlSchema;
    }

    public void setUrlSchema(Set<String> urlSchema) {
        this.urlSchema = urlSchema;
    }
}
