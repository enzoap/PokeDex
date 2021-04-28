package com.example.pokedex.data

import com.example.pokedex.data.model.Pokemon

sealed class PokemonResult{
    class Success(val pokemon: List<Pokemon>): PokemonResult()
    class ApiError(val StatusCode: Int): PokemonResult()
    object ServerError: PokemonResult()
}
