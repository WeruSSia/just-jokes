package com.example.just_jokes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FavouriteJokesListViewAdapter extends ArrayAdapter<JokeDto> {

    private SharedPreferences sharedPreferences;
    private String favouriteJokesSPKey;
    private Set<String> favouriteJokes;
    private FavouritesFragment favouritesFragment;

    FavouriteJokesListViewAdapter(Context context, ArrayList<JokeDto> favouriteJokes, FavouritesFragment favouritesFragment) {
        super(context, 0, favouriteJokes);
        this.favouritesFragment = favouritesFragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        JokeDto jokeDto = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.favourite_joke_item, parent, false);
        }
        TextView jokeContent = convertView.findViewById(R.id.joke_content);
        jokeContent.setText(jokeDto.getContent());
        ImageButton unfavouriteButton = convertView.findViewById(R.id.joke_unfav);
        setUnfavouriteButtonOnClickListenerForFavourites(unfavouriteButton, jokeDto);
        TextView upvotesTextView = convertView.findViewById(R.id.favourite_upvotes);
        TextView downvotesTextView = convertView.findViewById(R.id.favourite_downvotes);
        upvotesTextView.setText(getContext().getString(R.string.upvotes_number, jokeDto.getUpvotes()));
        downvotesTextView.setText(getContext().getString(R.string.downvotes_number, jokeDto.getDownvotes()));
        return convertView;
    }


    private void setUnfavouriteButtonOnClickListenerForFavourites(final ImageButton unfavouriteButton, final JokeDto jokeDto) {
        unfavouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setMessage("Are you sure you want to remove this joke from favourites?");
                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sharedPreferences = getContext().getSharedPreferences(getContext().getString(R.string.favourite_jokes_SP), Context.MODE_PRIVATE);
                        favouriteJokesSPKey = getContext().getString(R.string.favourite_jokes_SP_key);
                        favouriteJokes = new HashSet<>(sharedPreferences.getStringSet(favouriteJokesSPKey, new HashSet<String>()));
                        favouriteJokes.remove(jokeDto.getId());
                        final SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putStringSet(favouriteJokesSPKey, favouriteJokes);
                        editor.apply();
                        notifyDataSetChanged();
                        favouritesFragment.refresh();
                    }
                });
                alertDialogBuilder.setNegativeButton("no", null);
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }
}
