package com.embed.service.Impl;

import com.embed.dao.ProviderDAO;
import com.embed.entities.EmbeddedContent;
import com.embed.entities.Provider;
import com.embed.exceptions.ApplicationException;
import com.embed.exceptions.errors.ErrorCode;
import com.embed.service.EmbeddedService;
import com.embed.service.processor.DataProcessor;
import com.embed.utils.UrlProviderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmbeddedServiceImpl implements EmbeddedService {

    @Autowired
    private ProviderDAO providerDAO;

    @Autowired
    private DataProcessor<EmbeddedContent, String> contentDataProccessor;

    @Override
    public EmbeddedContent getResponse(String providerName, String url) {
        EmbeddedContent embeddedContent = new EmbeddedContent();

        Provider provider = providerDAO.findByName(providerName);

        if (provider == null) {
            throw new ApplicationException(ErrorCode.PROVIDER_NOT_FOUND,
                    "The Provider: " + providerName + ", is not registered");
        }

        if (!UrlProviderUtil.isRegisteredUrl(provider, url)) {
            throw new ApplicationException(ErrorCode.FIELD_VALIDATION_ERROR,
                    "The Url provided is not valid to retrieve content from " + providerName);
        }

        embeddedContent.setProvider(provider);

        contentDataProccessor.processData(embeddedContent, url);

        return embeddedContent;
    }
}
