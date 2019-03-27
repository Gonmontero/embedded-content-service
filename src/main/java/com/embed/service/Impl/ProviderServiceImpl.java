package com.embed.service.Impl;

import com.embed.dao.ProviderDAO;
import com.embed.entities.Provider;
import com.embed.exceptions.ApplicationException;
import com.embed.exceptions.errors.ErrorCode;
import com.embed.rest.resources.request.RegisterProviderRequestResource;
import com.embed.service.ProviderService;
import com.embed.utils.UrlProviderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProviderServiceImpl implements ProviderService {

    Logger logger = LoggerFactory.getLogger(ProviderService.class);

    @Autowired
    private ProviderDAO providerDAO;

    @Override
    public Provider getProvider(String providerName) {

        logger.info("Attempting to retrieve Provider: " + providerName);
        Provider provider = providerDAO.findByName(providerName.toLowerCase());

        if (provider == null) {
            throw new ApplicationException(ErrorCode.PROVIDER_NOT_FOUND,
                    "The requested provider " + providerName + " could not be found");
        }

        return provider;
    }

    @Override
    public Provider registerProvider(RegisterProviderRequestResource resource) {

        validateProviderInformation(resource);

        Provider provider = new Provider();
        populateProviderDetails(resource, provider);

        logger.info("Registering provider of name: " + provider.getName()
                + ", API endpoint: " + provider.getApiEndpoint()
                + "' Url Schemes: " + provider.getUrlSchema());

        try {
            provider = providerDAO.save(provider);

        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.UNEXPECTED_ERROR, "There has been a problem when trying to register the provider");
        }
        return provider;
    }

    @Override
    public Set<Provider> retrieveProviders() {

        List<Provider> providers = providerDAO.findAll();

        if (CollectionUtils.isEmpty(providers)) {
            logger.info("The list of providers is empty");
        }

        return new HashSet<>(providers);

    }

    private void populateProviderDetails(RegisterProviderRequestResource resource, Provider provider) {

        provider.setName(resource.getProviderName().toLowerCase());
        provider.setApiEndpoint(UrlProviderUtil.formatApiEndpoint(resource.getApiEndpoint()));

        Set<String> urlSchemes = new HashSet<>();
        resource.getUrlSchemes().stream().forEach(u -> {
            urlSchemes.add(UrlProviderUtil.formatUrlSchemeToRegex(u));
        });

        provider.setUrlSchema(urlSchemes);
    }

    private void validateProviderInformation(RegisterProviderRequestResource resource) {

        if (providerDAO.findByName(resource.getProviderName()) != null) {
            throw new ApplicationException(ErrorCode.FIELD_VALIDATION_ERROR, "The provider has already been registered");
        }

        if (CollectionUtils.isEmpty(resource.getUrlSchemes())) {
            throw new ApplicationException(ErrorCode.FIELD_VALIDATION_ERROR, "The Url Scheme cannot be empty");
        }

        if (StringUtils.isEmpty(resource.getApiEndpoint())) {
            throw new ApplicationException(ErrorCode.FIELD_VALIDATION_ERROR, "The provider's API endpoint cannot be empty");
        }

        if (StringUtils.isEmpty(resource.getProviderName())) {
            throw new ApplicationException(ErrorCode.FIELD_VALIDATION_ERROR, "The provider's name cannot be empty");
        }
    }

    public ProviderDAO getProviderDAO() {
        return providerDAO;
    }

    public void setProviderDAO(ProviderDAO providerDAO) {
        this.providerDAO = providerDAO;
    }
}
