package com.example.just_jokes;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class JokeRequestHandler {

    private String jokeId;
    private JokeService jokeService;
    private Context context;
    private TextView randomJokeTextView;
    private TextView upvotesTextView;
    private TextView downvotesTextView;

    public JokeRequestHandler(Context context, TextView randomJokeTextView, TextView upvotesTextView, TextView downvotesTextView) {
        setUpJokeService();
        this.context = context;
        this.randomJokeTextView = randomJokeTextView;
        this.upvotesTextView = upvotesTextView;
        this.downvotesTextView = downvotesTextView;
    }

    private void setUpJokeService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://joke3.p.rapidapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jokeService = retrofit.create(JokeService.class);
    }

    private Callback<JokeDto> getJokeDtoCallback() {
        return new Callback<JokeDto>() {
            @Override
            public void onResponse(Call<JokeDto> call, Response<JokeDto> response) {
                randomJokeTextView.setText(response.body().getContent());
                upvotesTextView.setText(context.getString(R.string.upvotes, response.body().getUpvotes()));
                downvotesTextView.setText(context.getString(R.string.downvotes, response.body().getDownvotes()));
                jokeId = response.body().getId();
            }

            @Override
            public void onFailure(Call<JokeDto> call, Throwable t) {
                randomJokeTextView.setText(t.getMessage());
            }
        };
    }

    void setRandomJokeButtonOnClickListener(Button randomJokeButton) {
        randomJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRandomJoke();
            }
        });
    }

    void setUpvoteButtonOnClickListener(Button upvoteButton) {
        upvoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jokeService.upvoteJoke(jokeId).enqueue(getJokeDtoCallback());
            }
        });
    }

    void setDownvoteButtonOnClickListener(Button downvoteButton) {
        downvoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jokeService.downvoteJoke(jokeId).enqueue(getJokeDtoCallback());
            }
        });
    }

    void setFavouriteButtonOnClickListener(Button favouriteButton) {
        favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SharedPreferences sharedPreferences = context.getSharedPreferences("favourites", Context.MODE_PRIVATE);
                final String favouriteJokesSPKey = "favouriteJokes";
                final Set<String> favouriteJokes = new HashSet<>(sharedPreferences.getStringSet(favouriteJokesSPKey, new HashSet<String>()));
                favouriteJokes.add(jokeId);
                final SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putStringSet(favouriteJokesSPKey, favouriteJokes);
                editor.apply();
            }
        });
    }

    void getRandomJoke() {
        jokeService.getRandomJoke().enqueue(getJokeDtoCallback());
    }

}
