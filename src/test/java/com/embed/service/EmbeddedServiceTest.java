package com.embed.service;

import com.embed.dao.ProviderDAO;
import com.embed.entities.EmbeddedContent;
import com.embed.entities.Provider;
import com.embed.exceptions.ApplicationException;
import com.embed.service.Impl.EmbeddedServiceImpl;
import com.embed.service.processor.DataProcessor;
import org.easymock.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.Set;

@RunWith(EasyMockRunner.class)
public class EmbeddedServiceTest extends EasyMockSupport {

    @TestSubject
    private EmbeddedServiceImpl providerService = new EmbeddedServiceImpl();

    @Mock
    private ProviderDAO providerDAO;

    @Mock
    private DataProcessor<EmbeddedContent, String> contentDataProccessor;

    private static String TEST_URL = "https://www.test.com/1234";
    private static String FAIL_TEST_URL = "https://failtest.com/1234";
    private static String SCHEMA_URL = "https(:|%3A)(\\/|%2F)(\\/|%2F)www.test.com(\\/|%2F)([^*$])+";

    public void setup() {
        injectMocks(providerDAO);
    }

    @Test(expected = ApplicationException.class)
    public void getContentProviderNotFoundTest() {
        Provider provider = retrieveTestProviderWithName("test");

        EasyMock.expect(providerDAO.findByName(provider.getName())).andReturn(null);
        EasyMock.replay(providerDAO);

        providerService.getResponse("test", TEST_URL);
        EasyMock.verify(providerDAO);

    }

    @Test(expected = ApplicationException.class)
    public void getContentProviderFoundFailUrlTest() {
        Provider provider = retrieveTestProviderWithName("test");

        EasyMock.expect(providerDAO.findByName(provider.getName())).andReturn(provider);
        EasyMock.replay(providerDAO);

        providerService.getResponse("test", FAIL_TEST_URL);
        EasyMock.verify(providerDAO);

    }

    @Test
    public void getContentProviderFoundTest() {
        Provider provider = retrieveTestProviderWithName("test");

        EasyMock.expect(providerDAO.findByName(provider.getName())).andReturn(provider);
        EasyMock.replay(providerDAO);

        EmbeddedContent content = providerService.getResponse("test", TEST_URL);
        Assert.assertNotNull(content);
        EasyMock.verify(providerDAO);

    }


    private Provider retrieveTestProviderWithName(String name) {

        String providerName = name;
        Provider provider = new Provider();
        provider.setName(providerName);
        provider.setApiEndpoint(TEST_URL);
        Set<String> schemeTest = new HashSet<>();
        schemeTest.add(TEST_URL);
        provider.setUrlSchema(schemeTest);

        return provider;
    }
}
