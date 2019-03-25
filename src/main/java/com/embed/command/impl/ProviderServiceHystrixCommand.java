package com.embed.command.impl;

import com.embed.command.ProviderService;
import com.embed.command.resources.ProviderEmbeddedResponse;
import com.embed.exceptions.ApplicationException;
import com.embed.exceptions.errors.ErrorCode;
import com.netflix.hystrix.HystrixCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import retrofit2.Call;
import retrofit2.Response;

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
            Call<ProviderEmbeddedResponse> httpRequest = providerService.getEmbeddedContent(url);
            LOGGER.info("Requesting Embedded Content to: " + httpRequest.request().url());

            Response<ProviderEmbeddedResponse> response = httpRequest.execute();

            if (!response.isSuccessful()) {
                LOGGER.error("Retrofit failed to connect when calling: " + url
                        + ". ERROR CODE: " + response.code() +" Error Message: " + response.errorBody());
                throw new ApplicationException(ErrorCode.PROVIDER_NOT_FOUND, "Failed to retrieve embedded content from Provider.");
            }
            return response.body();

        }catch (Exception e) {
            throw new ApplicationException(ErrorCode.UNEXPECTED_ERROR, "Failed to retrieve embedded content from Provider.");
        }
    }
}
