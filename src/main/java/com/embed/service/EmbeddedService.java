package com.embed.service;

import com.embed.command.resources.ProviderEmbeddedResponse;

public interface EmbeddedService {

    ProviderEmbeddedResponse getResponse(String providerName, String url);

}
