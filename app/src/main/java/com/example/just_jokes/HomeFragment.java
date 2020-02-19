package com.example.just_jokes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TextView randomJokeTextView = view.findViewById(R.id.random_joke_text_view);
        final TextView upvotesTextView = view.findViewById(R.id.upvotes_text_view);
        final TextView downvotesTextView = view.findViewById(R.id.downvotes_text_view);
        final ImageButton randomJokeButton = view.findViewById(R.id.random_joke_button);
        final ImageButton upvoteButton = view.findViewById(R.id.upvote_button);
        final ImageButton downvoteButton = view.findViewById(R.id.downvote_button);
        final ImageButton favouriteButton = view.findViewById(R.id.favourite_button);
        final ImageButton unfavouriteButton = view.findViewById(R.id.unfavourite_button);

        JokeRequestHandler jokeRequestHandler = new JokeRequestHandler(this.getContext(), randomJokeTextView, upvotesTextView, downvotesTextView, favouriteButton, unfavouriteButton);
        jokeRequestHandler.getRandomJoke();
        if (jokeRequestHandler.isJokeInFavourites()) {
            favouriteButton.setVisibility(View.INVISIBLE);
            unfavouriteButton.setVisibility(View.VISIBLE);
        } else {
            unfavouriteButton.setVisibility(View.INVISIBLE);
            favouriteButton.setVisibility(View.VISIBLE);
        }
        jokeRequestHandler.setFavouriteButtonOnClickListener(favouriteButton, unfavouriteButton);
        jokeRequestHandler.setUnfavouriteButtonOnClickListenerForHomepage(unfavouriteButton, favouriteButton);
        jokeRequestHandler.setRandomJokeButtonOnClickListener(randomJokeButton, favouriteButton, unfavouriteButton);
        jokeRequestHandler.setUpvoteButtonOnClickListener(upvoteButton);
        jokeRequestHandler.setDownvoteButtonOnClickListener(downvoteButton);

    }
}
