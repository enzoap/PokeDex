package com.example.pokedex.data

import com.example.pokedex.data.model.PokemonList

sealed class PokemonsListResult{
    class Success(val pokemons: List<PokemonList>): PokemonsListResult()
    class ApiError(val statusCode: Int): PokemonsListResult()
    object ServerError: PokemonsListResult()
}
