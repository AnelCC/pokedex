package com.anelcc.pokedex.pokeapi;


import com.anelcc.pokedex.models.Pokemons;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokeApiService {

    @GET("pokemon")
    Call<Pokemons> getPokeList(@Query("limit") int limit, @Query("offset") int offset);
}
