package com.example.just_jokes;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

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
    private SharedPreferences sharedPreferences;
    private String favouriteJokesSPKey;
    private Set<String> favouriteJokes;
    private ImageButton favouriteButton;
    private ImageButton unfavouriteButton;

    JokeRequestHandler(Context context, TextView randomJokeTextView, TextView upvotesTextView, TextView downvotesTextView, ImageButton favouriteButton, ImageButton unfavouriteButton) {
        setUpJokeService();
        this.context = context;
        this.randomJokeTextView = randomJokeTextView;
        this.upvotesTextView = upvotesTextView;
        this.downvotesTextView = downvotesTextView;
        this.favouriteButton = favouriteButton;
        this.unfavouriteButton = unfavouriteButton;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.favourite_jokes_SP), Context.MODE_PRIVATE);
        favouriteJokesSPKey = context.getString(R.string.favourite_jokes_SP_key);
        favouriteJokes = new HashSet<>(sharedPreferences.getStringSet(favouriteJokesSPKey, new HashSet<String>()));
    }

    JokeRequestHandler() {
        setUpJokeService();
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
                upvotesTextView.setText(context.getString(R.string.upvotes_number, response.body().getUpvotes()));
                downvotesTextView.setText(context.getString(R.string.downvotes_number, response.body().getDownvotes()));
                jokeId = response.body().getId();
                if (isJokeInFavourites()) {
                    favouriteButton.setVisibility(View.INVISIBLE);
                    unfavouriteButton.setVisibility(View.VISIBLE);
                } else {
                    unfavouriteButton.setVisibility(View.INVISIBLE);
                    favouriteButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<JokeDto> call, Throwable t) {
                randomJokeTextView.setText(t.getMessage());
            }
        };
    }

    void setRandomJokeButtonOnClickListener(ImageButton randomJokeButton, final ImageButton favouriteButton, final ImageButton unfavouriteButton) {
        randomJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRandomJoke();
                if (isJokeInFavourites()) {
                    favouriteButton.setVisibility(View.INVISIBLE);
                    unfavouriteButton.setVisibility(View.VISIBLE);
                } else {
                    unfavouriteButton.setVisibility(View.INVISIBLE);
                    favouriteButton.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    void setUpvoteButtonOnClickListener(ImageButton upvoteButton) {
        upvoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jokeService.upvoteJoke(jokeId).enqueue(getJokeDtoCallback());
            }
        });
    }

    void setDownvoteButtonOnClickListener(ImageButton downvoteButton) {
        downvoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jokeService.downvoteJoke(jokeId).enqueue(getJokeDtoCallback());
            }
        });
    }

    void setFavouriteButtonOnClickListener(final ImageButton favouriteButton, final ImageButton unfavouriteButton) {
        favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favouriteJokes = new HashSet<>(sharedPreferences.getStringSet(favouriteJokesSPKey, new HashSet<String>()));
                favouriteJokes.add(jokeId);
                final SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putStringSet(favouriteJokesSPKey, favouriteJokes);
                editor.apply();
                if (isJokeInFavourites()) {
                    favouriteButton.setVisibility(View.INVISIBLE);
                    unfavouriteButton.setVisibility(View.VISIBLE);
                } else {
                    unfavouriteButton.setVisibility(View.INVISIBLE);
                    favouriteButton.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    void setUnfavouriteButtonOnClickListenerForHomepage(final ImageButton unfavouriteButton, final ImageButton favouriteButton) {
        unfavouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favouriteJokes.remove(jokeId);
                final SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putStringSet(favouriteJokesSPKey, favouriteJokes);
                editor.apply();
                if (isJokeInFavourites()) {
                    favouriteButton.setVisibility(View.INVISIBLE);
                    unfavouriteButton.setVisibility(View.VISIBLE);
                } else {
                    unfavouriteButton.setVisibility(View.INVISIBLE);
                    favouriteButton.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    boolean isJokeInFavourites() {
        return favouriteJokes.contains(jokeId);
    }

    void getRandomJoke() {
        jokeService.getRandomJoke().enqueue(getJokeDtoCallback());
    }

    void getJokesList(final Set<String> jokeIds, final JokesCallback jokesCallback) {
        final Set<JokeDto> jokes = new HashSet<>();
        final AtomicInteger failureCounter = new AtomicInteger();
        failureCounter.set(0);
        for (final String jokeId : jokeIds) {
            jokeService.getJokeById(jokeId).enqueue(new Callback<JokeDto>() {
                @Override
                public void onResponse(Call<JokeDto> call, Response<JokeDto> response) {
                    jokes.add(response.body());
                    if (jokeIds.size() == jokes.size() + failureCounter.get()) {
                        jokesCallback.onResponse(jokes, failureCounter.get());
                    }
                }

                @Override
                public void onFailure(Call<JokeDto> call, Throwable t) {
                    failureCounter.incrementAndGet();
                    if (jokeIds.size() == jokes.size() + failureCounter.get()) {
                        jokesCallback.onResponse(jokes, failureCounter.get());
                    }
                }
            });
        }
    }
}
