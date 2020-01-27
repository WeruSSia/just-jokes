package com.example.just_jokes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FavouritesFragment extends Fragment {


    private Set<String> favouriteJokesIds = new HashSet<>();
    private ArrayList<JokeDto> favouriteJokeDtos = new ArrayList<>();

    public FavouritesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getActivity().getString(R.string.favourite_jokes_SP), Context.MODE_PRIVATE);
        favouriteJokesIds = sharedPreferences.getStringSet(getActivity().getString(R.string.favourite_jokes_SP_key), new HashSet<String>());

        JokeRequestHandler jokeRequestHandler = new JokeRequestHandler();
        jokeRequestHandler.getJokesList(favouriteJokesIds, new JokesCallback() {
            @Override
            public void onResponse(Set<JokeDto> jokes, int numberOfFailures) {
                favouriteJokeDtos.addAll(jokes);
                ListView favouriteJokesListView = view.findViewById(R.id.favourite_jokes_list_view);
                FavouriteJokesListViewAdapter adapter = new FavouriteJokesListViewAdapter(getContext(), favouriteJokeDtos);
                favouriteJokesListView.setAdapter(adapter);
                if (numberOfFailures>0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    if(numberOfFailures==favouriteJokesIds.size()){
                        builder.setTitle("Error");
                        builder.setMessage("Couldn't load favourite jokes");
                    }else{
                        builder.setTitle("Error");
                        builder.setMessage("Failed to load " + numberOfFailures + " jokes");
                    }
                    builder.create().show();
                }
            }
        });
    }
}
