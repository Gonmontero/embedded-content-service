package com.embed.service;

import com.embed.dao.ProviderDAO;
import com.embed.entities.Provider;
import com.embed.exceptions.ApplicationException;
import com.embed.exceptions.errors.ErrorCode;
import com.embed.rest.resources.request.RegisterProviderRequestResource;
import com.embed.service.Impl.ProviderServiceImpl;
import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(EasyMockRunner.class)
public class ProviderServiceTest extends EasyMockSupport {


    @TestSubject
    private ProviderServiceImpl providerService = new ProviderServiceImpl();

    @Mock
    private ProviderDAO providerDAO;


    public void setup() {
        injectMocks(providerDAO);
    }

    @Test
    public void getProviderByNameSuccessTest() {
        Provider provider = retrieveTestProviderWithName("test");

        EasyMock.expect(providerDAO.findByName(provider.getName().toLowerCase())).andReturn(provider);
        EasyMock.replay(providerDAO);

        Provider expectedProvider = providerService.getProvider(provider.getName());

        Assert.assertNotNull(expectedProvider);
        Assert.assertEquals(expectedProvider, provider);

        EasyMock.verify(providerDAO);

    }

    @Test
    public void getProviderByNameErrorTest() {
        Provider provider = retrieveTestProviderWithName("test");

        EasyMock.expect(providerDAO.findByName(provider.getName().toLowerCase())).andReturn(null);
        EasyMock.replay(providerDAO);

        try {
            Provider expectedProvider = providerService.getProvider(provider.getName());
        } catch (ApplicationException e) {
            Assert.assertEquals(ErrorCode.PROVIDER_NOT_FOUND, e.getErrorCode());
        }

        EasyMock.verify(providerDAO);

    }

    @Test
    public void getProvidersListTest() {
        List<Provider> providers = new ArrayList<>();

        for (int t = 0; t < 3 ; t++ ) {
            providers.add(retrieveTestProviderWithName("test" + t));
        }
        EasyMock.expect(providerDAO.findAll()).andReturn(providers);
        EasyMock.replay(providerDAO);

        Set<Provider> expectedProvider = providerService.retrieveProviders();

        Assert.assertNotNull(expectedProvider);
        expectedProvider.stream().map(providers::contains).forEach(Assert::assertTrue);

        EasyMock.verify(providerDAO);

    }

    @Test
    public void registerNewProviderSuccessTest() {

        Provider provider= retrieveTestProviderWithName("test");
        RegisterProviderRequestResource resource = new RegisterProviderRequestResource();
        resource.setProviderName(provider.getName());
        resource.setApiEndpoint(provider.getApiEndpoint());
        List<String> schemeTest= new ArrayList<String>();
        schemeTest.add("test");
        resource.setUrlSchemes(schemeTest);

        EasyMock.expect(providerDAO.save(EasyMock.anyObject())).andReturn(provider);
        EasyMock.replay(providerDAO);

        Provider expectedProvider = providerService.registerProvider(resource);

        Assert.assertNotNull(expectedProvider);

        EasyMock.verify(providerDAO);
    }

    @Test
    public void registerNewProviderSuccessFail() throws Exception {

        Provider provider= retrieveTestProviderWithName("test");
        RegisterProviderRequestResource resource = new RegisterProviderRequestResource();
        resource.setProviderName(provider.getName());
        resource.setApiEndpoint(provider.getApiEndpoint());
        List<String> schemeTest= new ArrayList<String>();
        schemeTest.add("test");
        resource.setUrlSchemes(schemeTest);

        EasyMock.expect(providerDAO.save(EasyMock.anyObject())).andReturn(null);
        EasyMock.replay(providerDAO);

        Provider expectedProvider = providerService.registerProvider(resource);

        Assert.assertNull(expectedProvider);

        EasyMock.verify(providerDAO);
    }

        private Provider retrieveTestProviderWithName(String name) {

        String providerName = name;
        Provider provider = new Provider();
        provider.setName(providerName);
        provider.setApiEndpoint(name + ".com");
        Set<String> schemeTest = new HashSet<>();
        schemeTest.add(name);
        provider.setUrlSchema(schemeTest);

        return provider;
    }
}
