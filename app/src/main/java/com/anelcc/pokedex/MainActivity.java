package com.anelcc.pokedex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.anelcc.pokedex.models.Pokemon;
import com.anelcc.pokedex.models.Pokemons;
import com.anelcc.pokedex.pokeapi.PokeApiService;
import com.anelcc.pokedex.ui.PokemonListAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private String TAG = "MainActivity";
    private int offset;
    private boolean isLoading;

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

        isLoading = true;
        offset = 0;
        getData(offset);

        recyclerView = findViewById(R.id.pokemon_list);
        pokemonListAdapter = new PokemonListAdapter();
        recyclerView.setAdapter(pokemonListAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int childCount = gridLayoutManager.getChildCount();
                    int itemCount = gridLayoutManager.getItemCount();
                    int visibleItem = gridLayoutManager.findFirstVisibleItemPosition();
                    if (isLoading) {
                        if ((childCount + visibleItem) >=  itemCount) {
                            Log.d(TAG, "onScrolled: end" + offset);
                            isLoading = false;
                            offset += 20;
                            getData(offset);
                        }
                    }
                }
            }
        });
    }

    private void getData(int offset) {
        PokeApiService pokeApiService = retrofit.create(PokeApiService.class);
        Call <Pokemons> pokemonsCall = pokeApiService.getPokeList(20, offset);

        pokemonsCall.enqueue(new Callback<Pokemons>() {
            @Override
            public void onResponse(Call<Pokemons> call, Response<Pokemons> response) {
                if (response.isSuccessful()) {
                    isLoading = true;
                    Pokemons pokemons = response.body();
                    ArrayList<Pokemon> pokemonList = pokemons.getPokemon() ;
                    for (int i = 0; i < pokemonList.size(); i++) {
                        Pokemon pokemon = pokemonList.get(i);
                        Log.d(TAG, "onResponse: "+ pokemon.getName());
                    }
                    pokemonListAdapter.update(pokemonList);
                } else {
                    Log.d(TAG, "Error: " + response.errorBody());
                    Toast.makeText(getApplicationContext(), "Somthing is bad, Please try again!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Pokemons> call, Throwable t) {
                isLoading = true;
                Toast.makeText(getApplicationContext(), "Somthing is bad, Please try again!", Toast.LENGTH_LONG).show();
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
