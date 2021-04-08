package com.example.pokedex.presentation.pokemons

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokedex.R
import com.example.pokedex.data.PokemonsListResult
import com.example.pokedex.data.model.PokemonList
import com.example.pokedex.data.repository.PokemonsRepository

class PokemonsViewModel(private val dataSource: PokemonsRepository): ViewModel() {
    val pokemonsLiveData: MutableLiveData<List<PokemonList>> = MutableLiveData()
    val viewFlipperLiveData: MutableLiveData<Pair<Int, Int?>> = MutableLiveData()

    fun getPokemons(){
        dataSource.getPokemons { result ->
            when(result) {
                is PokemonsListResult.Success -> {
                    pokemonsLiveData.value = result.pokemons
                    viewFlipperLiveData.value = Pair(VIEW_FLIPPER_POKEMONS, null)
                }
                is PokemonsListResult.ApiError -> {
                    if(result.statusCode == 401){
                        viewFlipperLiveData.value = Pair(VIEW_FLIPPER_ERROR, R.string.pokemons_error_401)
                    }else {
                        viewFlipperLiveData.value = Pair(VIEW_FLIPPER_ERROR, R.string.books_error_400_generic)
                    }
                }
                is PokemonsListResult.ServerError -> {
                    viewFlipperLiveData.value = Pair(VIEW_FLIPPER_ERROR, R.string.books_error_500_generic)
                }
            }

        }
    }

    companion object {
        private const val VIEW_FLIPPER_POKEMONS = 1
        private const val VIEW_FLIPPER_ERROR = 2
    }

    @Suppress("UNCHECKED_CAST")
    class MainViewModelFactory(private val dataSource: PokemonsRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PokemonsViewModel(dataSource) as T
        }

    }

}