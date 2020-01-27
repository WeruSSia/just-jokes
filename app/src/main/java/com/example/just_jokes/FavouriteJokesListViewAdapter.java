package com.example.just_jokes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FavouriteJokesListViewAdapter extends ArrayAdapter<JokeDto> {

    public FavouriteJokesListViewAdapter(Context context, ArrayList<JokeDto> favouriteJokes) {
        super(context, 0, favouriteJokes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        JokeDto jokeDto = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.favourite_joke_item, parent, false);
        }
        TextView jokeContent = convertView.findViewById(R.id.joke_content);
        jokeContent.setText(jokeDto.getContent());
        return convertView;
    }
}
