package com.embed.rest;

import com.embed.entities.Provider;
import com.embed.rest.exception.ApplicationExceptionHandler;
import com.embed.rest.resources.request.RegisterProviderRequestResource;
import com.embed.rest.resources.response.ProviderResponseResource;
import com.embed.service.ProviderService;
import com.github.dozermapper.core.Mapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;


@Api(tags = { "Admin" } )
@RestController
@RequestMapping("/admin")
public class RestAdminService extends ApplicationExceptionHandler {

    Logger logger = LoggerFactory.getLogger(RestAdminService.class);

    @Autowired
    private ProviderService providerService;

    @Autowired
    private Mapper mapper;

    @ApiOperation(value = "Gets information of a registered provider")
    @RequestMapping(value = "/providers/{providerName}", method = RequestMethod.GET)
    public ProviderResponseResource getProviderInformation(@PathVariable("providerName") String name,
                                                           HttpServletResponse httpResponse) {

        ProviderResponseResource response = null;

        Provider provider = providerService.getProvider(name);

        response = mapper.map(provider, ProviderResponseResource.class);

        if (response != null) {
            httpResponse.setStatus(HttpServletResponse.SC_OK);
        }

        return response;
    }

    @ApiOperation(value = "Retrieves a list of all registered providers")
    @RequestMapping(value = "/providers", method = RequestMethod.GET)
    public Set<ProviderResponseResource> listProvidersInformation(HttpServletResponse httpResponse) {

        Set<Provider> providers = providerService.retrieveProviders();
        Set<ProviderResponseResource> response = new HashSet<>();

        if (providers != null) {
            providers.forEach(p -> response.add(mapper.map(p, ProviderResponseResource.class)));
            httpResponse.setStatus(HttpServletResponse.SC_OK);
        }

        return response;
    }

    @ApiOperation(value = "Register information of a new provider")
    @RequestMapping(value = "/providers", method = RequestMethod.POST)
    public ProviderResponseResource addProviderInformation(@RequestBody RegisterProviderRequestResource request,
                                                           HttpServletResponse httpResponse) {

        ProviderResponseResource response = null;

        Provider provider = providerService.registerProvider(request);

        response = mapper.map(provider, ProviderResponseResource.class);

        if (response != null) {
            httpResponse.setStatus(HttpServletResponse.SC_OK);
        }

        return response;
    }

}
