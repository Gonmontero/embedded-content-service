package com.embed.service.Impl;

import com.embed.command.impl.ProviderServiceCommandFactory;
import com.embed.dao.ProviderDAO;
import com.embed.entities.Provider;
import com.embed.exceptions.ApplicationException;
import com.embed.exceptions.errors.ErrorCode;
import com.embed.command.ProviderService;
import com.embed.command.resources.ProviderEmbeddedResponse;
import com.embed.service.EmbeddedService;
import com.embed.utils.UrlProviderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmbeddedServiceImpl implements EmbeddedService {

    @Autowired
    ProviderDAO providerDAO;

    @Override
    public ProviderEmbeddedResponse getResponse(String providerName, String url) {

        Provider provider = providerDAO.findByName(providerName);

        if (provider == null) {
            throw new ApplicationException(ErrorCode.PROVIDER_NOT_FOUND,
                    "The Provider: " + providerName + ", is not registered");
        }

        if (!UrlProviderUtil.isRegisteredUrl(provider, url)) {
            throw new ApplicationException(ErrorCode.FIELD_VALIDATION_ERROR,
                    "The Url is not registered to the provider " + providerName);
        }

        ProviderServiceCommandFactory productService = new ProviderServiceCommandFactory(ProviderService.class, provider.getApiEndpoint());

        ProviderEmbeddedResponse embeddedContent = productService.getEmbeddedContent(url);

        if (embeddedContent == null) {
            throw new ApplicationException(ErrorCode.CONTENT_NOT_FOUND, "Content was not found at the Provider");
        }

        return embeddedContent;
    }
}
