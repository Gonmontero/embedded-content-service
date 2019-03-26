package com.embed.service;

import com.embed.entities.EmbeddedContent;

public interface EmbeddedService {

    EmbeddedContent getResponse(String providerName, String url);

}
