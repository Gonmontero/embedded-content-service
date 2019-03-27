package com.embed.command.impl;

import com.embed.command.ProviderService;
import com.embed.command.resources.ProviderEmbeddedResponse;
import com.embed.exceptions.ApplicationException;
import com.embed.exceptions.errors.ErrorCode;
import com.netflix.hystrix.HystrixCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import retrofit.RetrofitError;


public class ProviderServiceHystrixCommand extends HystrixCommand<ProviderEmbeddedResponse> {

    private static final Logger LOGGER = LogManager.getLogger(ProviderServiceHystrixCommand.class);

    private final ProviderService providerService;
    private final String url;

    public ProviderServiceHystrixCommand(HystrixCommand.Setter setter, ProviderService service, String url) {
        super(setter);
        this.providerService = service;
        this.url = url;
    }

    @Override
    protected ProviderEmbeddedResponse run() {
        try {
            ProviderEmbeddedResponse httpRequest = providerService.getEmbeddedContent(url);
            return httpRequest;
            } catch(RetrofitError e) {
                LOGGER.error("ERROR when calling trying to reach url: " + e.getUrl() + " Reason: " + e.getResponse().getReason() + e);
                throw new ApplicationException(ErrorCode.UNEXPECTED_ERROR, "Failed to retrieve embedded content from Provider.");
        }
    }
}

