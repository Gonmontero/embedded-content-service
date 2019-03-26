package com.embed.utils;

import com.embed.entities.Provider;

public class UrlProviderUtil {

    private static String SLASH_CHAR = "/";
    private static String SLASH_CHAR_REGEX = "(\\/|%2F)";
    private static String COLON_CHAR = ":";
    private static String COLON_CHAR_REGEX = "(:|%3A)";
    private static String ASTERISK_CHAR = "*";
    private static String ASTERISK_CHAR_REGEX = "([^*$])+";
    private static String OEMBED_ROUTE = "/oembed";

    public static String formatUrlSchemeToRegex(String url) {

        String urlRegex = url;
        urlRegex = urlRegex.replace(SLASH_CHAR, SLASH_CHAR_REGEX);
        urlRegex = urlRegex.replace(COLON_CHAR, COLON_CHAR_REGEX);
        urlRegex = urlRegex.replace(ASTERISK_CHAR, ASTERISK_CHAR_REGEX);

        return urlRegex;
    }

    public static String formatApiEndpoint(String apiEndpoint) {

        String formatted = apiEndpoint.toLowerCase();
        formatted = formatted.replace(OEMBED_ROUTE, SLASH_CHAR);

        return formatted;
    }

    public static boolean isRegisteredUrl(Provider provider, String url) {
        Boolean validated = false;

        for (String s : provider.getUrlSchema()) {
            validated = url.matches(s);
            if (validated) {
                break;
            }
        }
        return validated;
    }
}