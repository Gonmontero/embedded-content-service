package com.embed.utils;

public class UrlProviderUtil {

    private static String SLASH_CHAR = "/";
    private static String SLASH_CHAR_REGEX = "(\\/|%2F)";
    private static String COLON_CHAR = ":";
    private static String COLON_CHAR_REGEX = "(:|%3A)";
    private static String ASTERISK_CHAR = "*";
    private static String ASTERISK_CHAR_REGEX = "([^*$])+";

    public static String formatUrlSchemeToRegex(String url) {

        String urlRegex = url;
        urlRegex = urlRegex.replace(SLASH_CHAR, SLASH_CHAR_REGEX);
        urlRegex = urlRegex.replace(COLON_CHAR, COLON_CHAR_REGEX);
        urlRegex = urlRegex.replace(ASTERISK_CHAR, ASTERISK_CHAR_REGEX);

        return urlRegex;
    }
}
