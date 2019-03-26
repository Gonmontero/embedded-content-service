package com.embed.command;

import com.embed.command.resources.ProviderEmbeddedResponse;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

public interface ProviderService {

    @GET("/oembed")
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    ProviderEmbeddedResponse getEmbeddedContent(@Query("url") String url);

}
