package com.embed.command;

import com.embed.command.resources.ProviderEmbeddedResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ProviderService {

    @GET("/oembed")
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    Call<ProviderEmbeddedResponse> getEmbeddedContent(@Query("url") String url);

}
