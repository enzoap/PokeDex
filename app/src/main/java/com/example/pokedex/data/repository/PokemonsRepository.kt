package com.example.pokedex.data.repository

import com.example.pokedex.data.PokemonResult
import com.example.pokedex.data.PokemonsListResult

interface PokemonsRepository {
    fun getPokemons(pokemonsResultCallback: (listResult: PokemonsListResult) -> Unit)

}