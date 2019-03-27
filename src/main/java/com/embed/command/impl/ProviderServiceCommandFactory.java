package com.embed.command.impl;

import com.embed.command.CommandFactoryBase;
import com.embed.command.config.HystrixConfig;
import com.embed.command.ProviderService;
import com.embed.command.resources.ProviderEmbeddedResponse;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ProviderServiceCommandFactory extends CommandFactoryBase<ProviderService> {

    private static final Logger LOGGER = LogManager.getLogger(ProviderServiceCommandFactory.class);

    public ProviderServiceCommandFactory(Class<ProviderService> type, String endpoint) {
        super(type, endpoint);
    }

    /**
     * Retrieve Content using Provider service.
     * @param url the content target Url
     *
     * @return provider Response
     */
    public ProviderEmbeddedResponse getEmbeddedContent(String url) {
        try {
            return new ProviderServiceHystrixCommand(HystrixConfig.initHttpCommandSetter(HystrixConfig.GroupKey.PROVIDER_SERVICE),
                    getService(), url).execute();
        } catch (HystrixRuntimeException hre) {
            LOGGER.error(String.format("Failed to look up Provider from the provider service: %s", url));
            return null;
        }
    }
}
