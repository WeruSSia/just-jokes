package com.example.just_jokes;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface JokeService {

    String apiKeyHeader = "x-rapidapi-key:33271c12femshc43be3cbec9200cp164af8jsna6babdb58409";
    String contentTypeHeader = "content-type:application/x-www-form-urlencoded";

    @Headers(apiKeyHeader)
    @GET("joke/")
    Call<JokeDto> getRandomJoke(@Query("nsfw") boolean nsfw);

    @Headers(apiKeyHeader)
    @GET
    Call getJokeById(@Field("id") String id);

    @Headers(apiKeyHeader)
    @GET
    Call getJokeVotes(@Field("property") String property, @Field("id") String id);

    @Headers(apiKeyHeader + "," + contentTypeHeader)
    @POST
    Call downvoteJoke(@Field("id") String id);

    @Headers(apiKeyHeader + "," + contentTypeHeader)
    @POST
    Call upvoteJoke(@Field("id") String id);
}
