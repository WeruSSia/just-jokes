package com.example.just_jokes;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Joke {
    @GET
    Call getRandomJoke(@Field("nsfw") boolean nsfw);

    @GET
    Call getJokeById(@Field("id") String id);

    @GET
    Call getJokeVotes(@Field("property") String property, @Field("id") String id);

    @POST
    Call downvoteJoke(@Field("id") String id);

    @POST
    Call upvoteJoke(@Field("id") String id);
}
