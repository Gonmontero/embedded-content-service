package com.embed.utils;

import org.easymock.EasyMockRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EasyMockRunner.class)
public class UrlProviderUtilTest {

    private static String URL_SCHEME_TEST = "https://*.test.com/*/api/*";

    private static String URL_TEST = "https://www.test.com/test123/api/123";

    private static String REGEX_TEST = "https(:|%3A)(\\/|%2F)(\\/|%2F)([^*$])+.test.com(\\/|%2F)([^*$])+(\\/|%2F)api(\\/|%2F)([^*$])+";

    @Test
    public void urlSchemeToRegexTest() {
        String urlScheme = URL_SCHEME_TEST;
        urlScheme = UrlProviderUtil.formatUrlSchemeToRegex(urlScheme);

        Assert.assertEquals(REGEX_TEST, urlScheme);
        Assert.assertTrue(URL_TEST.matches(urlScheme));
    }
}
