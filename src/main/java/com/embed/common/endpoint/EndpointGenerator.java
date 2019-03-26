package com.embed.common.endpoint;

import com.jakewharton.retrofit.Ok3Client;
import okhttp3.OkHttpClient;
import retrofit.RestAdapter;

public class EndpointGenerator {

    public static <T> T buildConnector(Class<T> type, String baseUrl) {

        final RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(baseUrl)
                .setClient(new Ok3Client(new OkHttpClient()));

        return builder.build().create(type);
    }
}
