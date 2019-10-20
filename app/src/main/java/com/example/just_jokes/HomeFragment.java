package com.example.just_jokes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        final Button randomJokeButton = view.findViewById(R.id.random_joke_button);
        final Button upvoteButton = view.findViewById(R.id.upvote_button);
        final Button downvoteButton = view.findViewById(R.id.downvote_button);
        final Button favouriteButton = view.findViewById(R.id.favourite_button);

        JokeRequestHandler jokeRequestHandler = new JokeRequestHandler(this.getContext(), randomJokeTextView, upvotesTextView, downvotesTextView);
        jokeRequestHandler.getRandomJoke();
        jokeRequestHandler.setRandomJokeButtonOnClickListener(randomJokeButton);
        jokeRequestHandler.setUpvoteButtonOnClickListener(upvoteButton);
        jokeRequestHandler.setDownvoteButtonOnClickListener(downvoteButton);
        jokeRequestHandler.setFavouriteButtonOnClickListener(favouriteButton);
    }
}
