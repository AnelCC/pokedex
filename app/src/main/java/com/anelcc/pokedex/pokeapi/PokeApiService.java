package com.anelcc.pokedex.pokeapi;


import com.anelcc.pokedex.models.Pokemons;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PokeApiService {

    @GET("pokemon")
    Call<Pokemons> getPokeLits();
}
