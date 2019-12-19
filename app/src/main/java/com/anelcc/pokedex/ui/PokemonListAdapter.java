package com.anelcc.pokedex.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anelcc.pokedex.R;
import com.anelcc.pokedex.models.Pokemon;
import com.anelcc.pokedex.models.Pokemons;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PokemonListAdapter extends RecyclerView.Adapter<PokemonListAdapter.ViewHolder> {
    private ArrayList<Pokemon> pokemons;

    public PokemonListAdapter(){
        pokemons = new ArrayList<>();
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_item, parent,false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull PokemonListAdapter.ViewHolder holder, int position) {
        Pokemon pokemon = pokemons.get(position);
        holder.pokemonName.setText(pokemon.getName());
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView pokemonImage;
        private TextView pokemonName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pokemonImage = itemView.findViewById(R.id.pokemon_image);
            pokemonName = itemView.findViewById(R.id.pokemon_name);

        }
    }

    public void update(ArrayList<Pokemon> pokemonList){
        this.pokemons = pokemonList;
        notifyDataSetChanged();
    }

}
