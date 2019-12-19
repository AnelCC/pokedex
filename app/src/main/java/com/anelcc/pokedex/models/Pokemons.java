package com.anelcc.pokedex.models;

import java.util.ArrayList;

public class Pokemons {

    private ArrayList<Pokemon> results;

    public ArrayList<Pokemon> getPokemon() {
        return results;
    }

    public void setPokemon(ArrayList<Pokemon> pokemon) {
        this.results = pokemon;
    }
}
