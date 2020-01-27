package com.example.just_jokes;

import java.util.Set;

interface JokesCallback {
    void onResponse(Set<JokeDto> jokes, int numberOfFailures);
}
