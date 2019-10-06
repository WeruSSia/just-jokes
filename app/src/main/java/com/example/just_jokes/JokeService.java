package com.example.just_jokes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface JokeService {

    String hostHeader = "x-rapidapi-host:joke3.p.rapidapi.com";
    String apiKeyHeader = "x-rapidapi-key:33271c12femshc43be3cbec9200cp164af8jsna6babdb58409";
    String contentTypeHeader = "content-type:application/x-www-form-urlencoded";

    @Headers(apiKeyHeader)
    @GET("joke/")
    Call<JokeDto> getRandomJoke(@Query("nsfw") boolean nsfw);

    @Headers(apiKeyHeader)
    @GET("joke/{id}/")
    Call<JokeDto> getJokeById(@Path("id") String id);

    @Headers({hostHeader, apiKeyHeader, contentTypeHeader})
    @POST("joke/{id}/downvote")
    Call<JokeDto> downvoteJoke(@Path("id") String id);

    @Headers({hostHeader, apiKeyHeader, contentTypeHeader})
    @POST("joke/{id}/upvote")
    Call<JokeDto> upvoteJoke(@Path("id") String id);
}
