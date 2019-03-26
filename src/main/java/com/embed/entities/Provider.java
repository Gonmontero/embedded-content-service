package com.embed.entities;

import javax.persistence.*;
import java.util.Set;

/**
 *  Class to Model the Provider.
 */

@Entity
@Table(name= "provider")
public class Provider {

    @Id
    @Column(name = "provider_name", length = 50, unique = true)
    private String name;

    @Column(name = "url", length = 50)
    private String url;

    @Column(name = "api_url", length = 50)
    private String apiEndpoint;

    @ElementCollection
    @CollectionTable(name = "url_schemas")
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
