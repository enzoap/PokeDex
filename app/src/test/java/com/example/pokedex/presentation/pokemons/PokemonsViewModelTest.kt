package com.example.pokedex.presentation.pokemons

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.pokedex.R
import com.example.pokedex.data.PokemonsListResult
import com.example.pokedex.data.model.PokemonList
import com.example.pokedex.data.repository.PokemonsRepository
import com.nhaarman.mockitokotlin2.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PokemonsViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var pokemonsLiveDataObserver: Observer<List<PokemonList>>
    @Mock
    private lateinit var viewFlipperLiveDataObserver: Observer<Pair<Int, Int?>>

    private lateinit var viewModel: PokemonsViewModel

    @Test
    fun `when view model getPokemons get success then set pokemonsLiveData`() {
        val pokemons = listOf(
            PokemonList("Bulbassaur", "https://google.com")
        )

        val resultSucess = MockRepository(PokemonsListResult.Success(pokemons))
        viewModel = PokemonsViewModel(resultSucess)
        viewModel.pokemonsLiveData.observeForever(pokemonsLiveDataObserver)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getPokemons()

        verify(pokemonsLiveDataObserver).onChanged(pokemons)
        verify(viewFlipperLiveDataObserver).onChanged(Pair(1, null))
    }

    @Test
    fun `when view model getPokemons get server error then set viewFlipperLiveData`(){
        val resultServerError = MockRepository(PokemonsListResult.ServerError)
        viewModel = PokemonsViewModel(resultServerError)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getPokemons()

        verify(viewFlipperLiveDataObserver).onChanged(Pair(2, R.string.pokemon_error_500_generic))
    }

    @Test
    fun `when view model getPokemons api error status code 401 then set viewFlipperLiveData`() {
        val resultApiError = MockRepository(PokemonsListResult.ApiError(401))
        viewModel = PokemonsViewModel(resultApiError)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getPokemons()

        verify(viewFlipperLiveDataObserver).onChanged(Pair(2,R.string.pokemons_error_401))
    }

    @Test
    fun `when view model getPokemons api error status code generic then set viewFlipperLiveData`() {
        val resultApiError = MockRepository(PokemonsListResult.ApiError(402))
        viewModel = PokemonsViewModel(resultApiError)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getPokemons()

        verify(viewFlipperLiveDataObserver).onChanged(Pair(2, R.string.pokemon_error_400_generic))
    }
}

class MockRepository(private val result: PokemonsListResult): PokemonsRepository {
    override fun getPokemons(pokemonsResultCallback: (listResult: PokemonsListResult) -> Unit) {
        pokemonsResultCallback(result)
    }
}