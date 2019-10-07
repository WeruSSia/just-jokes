package com.example.just_jokes;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView randomJokeTextView = findViewById(R.id.random_joke_text_view);
        final TextView upvotesTextView = findViewById(R.id.upvotes_text_view);
        final TextView downvotesTextView = findViewById(R.id.downvotes_text_view);
        final Button randomJokeButton = findViewById(R.id.random_joke_button);
        final Button upvoteButton = findViewById(R.id.upvote_button);
        final Button downvoteButton = findViewById(R.id.downvote_button);

        JokeRequestHandler jokeRequestHandler = new JokeRequestHandler(this,randomJokeTextView,upvotesTextView,downvotesTextView);
        jokeRequestHandler.getRandomJoke();
        jokeRequestHandler.setRandomJokeButtonOnClickListener(randomJokeButton);
        jokeRequestHandler.setUpvoteButtonOnClickListener(upvoteButton);
        jokeRequestHandler.setDownvoteButtonOnClickListener(downvoteButton);
    }
}
