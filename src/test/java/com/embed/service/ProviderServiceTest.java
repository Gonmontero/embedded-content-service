package com.embed.service;

import com.embed.dao.ProviderDAO;
import com.embed.entities.Provider;
import com.embed.exceptions.ApplicationException;
import com.embed.exceptions.errors.ErrorCode;
import com.embed.service.Impl.ProviderServiceImpl;
import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;
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
        Provider provider = retrieveTestProvider();

        EasyMock.expect(providerDAO.findByName(provider.getName().toLowerCase())).andReturn(provider);
        EasyMock.replay(providerDAO);

        Provider expectedProvider = providerService.getProvider(provider.getName());

        Assert.assertNotNull(expectedProvider);
        Assert.assertEquals(expectedProvider, provider);

        EasyMock.verify(providerDAO);

    }

    @Test
    public void getProviderByNameErrorTest() {
        Provider provider = retrieveTestProvider();

        EasyMock.expect(providerDAO.findByName(provider.getName().toLowerCase())).andReturn(null);
        EasyMock.replay(providerDAO);

        try {
            Provider expectedProvider = providerService.getProvider(provider.getName());
        } catch (ApplicationException e) {
            Assert.assertEquals(ErrorCode.PROVIDER_NOT_FOUND, e.getErrorCode());
        }

        EasyMock.verify(providerDAO);

    }

    private Provider retrieveTestProvider() {

        String providerName = "TestProvider";
        Provider provider = new Provider();
        provider.setName(providerName);
        provider.setApiEndpoint("www.testAPI.com");
        Set<String> schemeTest = new HashSet<>();
        schemeTest.add("test");
        provider.setUrlSchema(schemeTest);

        return provider;
    }
}
