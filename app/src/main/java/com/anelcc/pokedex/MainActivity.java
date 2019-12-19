package com.anelcc.pokedex;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.anelcc.pokedex.models.Pokemons;
import com.anelcc.pokedex.pokeapi.PokeApiService;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        
        getData();
    }

    private void getData() {
        PokeApiService pokeApiService = retrofit.create(PokeApiService.class);
        Call <Pokemons> pokemonsCall = pokeApiService.getPokeLits();

        pokemonsCall.enqueue(new Callback<Pokemons>() {
            @Override
            public void onResponse(Call<Pokemons> call, Response<Pokemons> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "getData: " + response.body());
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
