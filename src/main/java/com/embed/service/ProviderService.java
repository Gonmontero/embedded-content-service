package com.embed.service;

import com.embed.entities.Provider;
import com.embed.rest.resources.request.RegisterProviderRequestResource;

import java.util.Set;

public interface ProviderService {

    /**
     * Retrieves an specific provider by it's name.
     *
     * @param providerName the provider's name
     * @return a Provider.
     */
    Provider getProvider(String providerName);

    /**
     * Registers an specific provider.
     *
     * @param resource the provider's Information provided
     * @return a Provider.
     */
    Provider registerProvider(RegisterProviderRequestResource resource);

    /**
     * Retrieves a list of all the registered providers.
     *
     * @return a list of Providers.
     */
    Set<Provider> retrieveProviders();

}
