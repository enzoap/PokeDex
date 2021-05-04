package com.example.pokedex.presentation.pokemonDetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.pokedex.R
import com.example.pokedex.data.PokemonResult
import com.example.pokedex.data.model.Pokemon
import com.example.pokedex.data.repository.PokemonDetailRepository
import com.nhaarman.mockitokotlin2.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PokemonDetailViewModelTest{
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var pokemonLiveDataObserver: Observer<List<Pokemon>>
    @Mock
    private lateinit var errorLiveDataObserver: Observer<Pair<Int, Int?>>

    private lateinit var viewModel: PokemonDetailViewModel

    @Test
    fun `when viel model getPokemon get success then set pokemonLiveData`() {
        val pokemon = listOf(
            Pokemon(1, "attack", "Normal", "Fire")
        )

        val resultSuccess = MockRepository(PokemonResult.Success(pokemon))
        viewModel = PokemonDetailViewModel(MockRepository(PokemonResult.Success(pokemon)))
        viewModel.pokemonLiveData.observeForever(pokemonLiveDataObserver)
        viewModel.errorLiveData.observeForever(errorLiveDataObserver)

        viewModel.getPokemon(1)

        verify(pokemonLiveDataObserver).onChanged(pokemon)
        verify(errorLiveDataObserver).onChanged(Pair(1, null))
    }

    @Test
    fun `when viel model getPokemon get server error then set errorLiveData`() {
        val resultServerError = MockRepository(PokemonResult.ServerError)
        viewModel = PokemonDetailViewModel(resultServerError)
        viewModel.errorLiveData.observeForever(errorLiveDataObserver)

        viewModel.getPokemon(1)

        verify(errorLiveDataObserver).onChanged(Pair(2, R.string.pokemon_error_500_generic))
    }

    @Test
    fun `when view model getPokemon get api error status code 401 then set errorLiveData`() {
        val resultApiError = MockRepository(PokemonResult.ApiError(401))
        viewModel = PokemonDetailViewModel(resultApiError)
        viewModel.errorLiveData.observeForever(errorLiveDataObserver)

        viewModel.getPokemon(1)

        verify(errorLiveDataObserver).onChanged(Pair(2, R.string.pokemons_error_401))
    }

    @Test
    fun `when view model getPokemon get api error status code generic then set errorLiveData`() {
        val resultApiError = MockRepository(PokemonResult.ApiError(402))
        viewModel = PokemonDetailViewModel(resultApiError)
        viewModel.errorLiveData.observeForever(errorLiveDataObserver)

        viewModel.getPokemon(1)
        verify(errorLiveDataObserver).onChanged(Pair(2, R.string.pokemon_error_400_generic))
    }
}

class MockRepository(private val result: PokemonResult): PokemonDetailRepository {
    override fun getPokemon(
        id: Int,
        pokemonResultCallback: (pokemonResult: PokemonResult) -> Unit
    ) {
        pokemonResultCallback(result)
    }

}