package com.embed.rest;


import com.embed.exceptions.ApplicationException;
import com.embed.exceptions.errors.ErrorCode;
import com.embed.command.resources.ProviderEmbeddedResponse;
import com.embed.rest.exception.ApplicationExceptionHandler;
import com.embed.service.EmbeddedService;
import com.github.dozermapper.core.Mapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Api(tags = { "Embedded" } )
@RestController
@RequestMapping
public class RestEmbeddedService extends ApplicationExceptionHandler {

    @Autowired
    private EmbeddedService embeddedService;

    @Autowired
    private Mapper mapper;


    @ApiOperation(value = "Gets embedded information from the provider")
    @RequestMapping(value = "/oembed/{providerName}", method = RequestMethod.GET)
    public ProviderEmbeddedResponse getEmbeddedContent(@PathVariable("providerName") String id,
                                                       @RequestParam(name = "url") String url,
                                                       HttpServletResponse httpResponse) {

        if(StringUtils.isEmpty(url)){
            throw new ApplicationException(ErrorCode.FIELD_VALIDATION_ERROR, "An Url needs to be provided");
        }

        ProviderEmbeddedResponse response = embeddedService.getResponse(id, url);

        if (response != null) {
            httpResponse.setStatus(HttpServletResponse.SC_OK);
        }
        return response;
    }

    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    public EmbeddedService getEmbeddedService() {
        return embeddedService;
    }

    public void setEmbeddedService(EmbeddedService embeddedService) {
        this.embeddedService = embeddedService;
    }
}
