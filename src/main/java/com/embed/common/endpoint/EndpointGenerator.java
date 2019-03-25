package com.embed.common.endpoint;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EndpointGenerator {

    private static OkHttpClient.Builder httpClient
            = new OkHttpClient.Builder();

    public static <T> T buildConnector(Class<T> type, String baseUrl) {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        return retrofit.create(type);
    }
}
