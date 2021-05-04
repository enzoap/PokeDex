package com.example.pokedex.presentation.pokemonDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokedex.R
import com.example.pokedex.data.PokemonResult
import com.example.pokedex.data.model.Pokemon
import com.example.pokedex.data.repository.PokemonDetailRepository

class PokemonDetailViewModel(private val dataSource: PokemonDetailRepository) : ViewModel() {
    val pokemonLiveData: MutableLiveData<List<Pokemon>> = MutableLiveData()
    val errorLiveData: MutableLiveData<Pair<Int, Int?>> = MutableLiveData()

    fun getPokemon(id: Int) {
        dataSource.getPokemon(id) { pokemonResult ->
            when (pokemonResult) {
                is PokemonResult.Success -> {
                    pokemonLiveData.value = pokemonResult.pokemon
                    errorLiveData.value = Pair(VIEW_FLIPPER_POKEMONS, null)
                }
                is PokemonResult.ApiError -> {
                    if(pokemonResult.StatusCode == 401){
                        errorLiveData.value = Pair(VIEW_FLIPPER_ERROR, R.string.pokemons_error_401)
                    }else {
                        errorLiveData.value = Pair(VIEW_FLIPPER_ERROR, R.string.pokemon_error_400_generic)
                    }
                }
                is PokemonResult.ServerError -> {
                    errorLiveData.value = Pair(VIEW_FLIPPER_ERROR, R.string.pokemon_error_500_generic)
                }
            }
        }
    }

    companion object {
        private const val VIEW_FLIPPER_POKEMONS = 1
        private const val VIEW_FLIPPER_ERROR = 2
    }

    @Suppress("UNCHECKED_CAST")
    class PokemonViewModelFactory(private val dataSource: PokemonDetailRepository) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PokemonDetailViewModel(dataSource) as T
        }

    }

}

