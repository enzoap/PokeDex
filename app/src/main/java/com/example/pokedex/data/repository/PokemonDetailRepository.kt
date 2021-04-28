package com.example.pokedex.data.repository

import com.example.pokedex.data.PokemonResult

interface PokemonDetailRepository {
    fun getPokemon(id: Int, pokemonResultCallback: (pokemonResult: PokemonResult) -> Unit)
}