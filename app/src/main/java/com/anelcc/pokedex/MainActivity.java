package com.anelcc.pokedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.util.Log;

import com.anelcc.pokedex.models.Pokemon;
import com.anelcc.pokedex.models.Pokemons;
import com.anelcc.pokedex.pokeapi.PokeApiService;
import com.anelcc.pokedex.ui.PokemonListAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private PokemonListAdapter pokemonListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        recyclerView = findViewById(R.id.pokemon_list);
        pokemonListAdapter = new PokemonListAdapter();
        recyclerView.setAdapter(pokemonListAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        getData();
    }

    private void getData() {
        PokeApiService pokeApiService = retrofit.create(PokeApiService.class);
        Call <Pokemons> pokemonsCall = pokeApiService.getPokeLits();

        pokemonsCall.enqueue(new Callback<Pokemons>() {
            @Override
            public void onResponse(Call<Pokemons> call, Response<Pokemons> response) {
                if (response.isSuccessful()) {
                    Pokemons pokemons = response.body();
                    ArrayList<Pokemon> pokemonList = pokemons.getPokemon() ;
                    for (int i = 0; i < pokemonList.size(); i++) {
                        Pokemon pokemon = pokemonList.get(i);
                        Log.d(TAG, "onResponse: "+ pokemon.getName());
                    }
                    pokemonListAdapter.update(pokemonList);
                } else {
                    Log.d(TAG, "Error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Pokemons> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
