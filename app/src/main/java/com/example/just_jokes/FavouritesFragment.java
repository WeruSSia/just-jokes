package com.example.just_jokes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.HashSet;
import java.util.Set;

public class FavouritesFragment extends Fragment {

    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getActivity().getString(R.string.favourite_jokes_SP), Context.MODE_PRIVATE);
    Set<String> favouriteJokes = new HashSet<>();

    public FavouritesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favouriteJokes = sharedPreferences.getStringSet(getActivity().getString(R.string.favourite_jokes_SP),new HashSet<String>());

        ListView favouriteJokesListView = view.findViewById(R.id.favourite_jokes_list_view);
//        favouriteJokesListView.setAdapter();

    }
}
