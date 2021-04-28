package com.example.pokedex.presentation.pokemons

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.data.model.PokemonList
import com.example.pokedex.data.repository.PokemonsApiDataSource
import com.example.pokedex.databinding.ActivityPokemonsBinding
import com.example.pokedex.presentation.base.BaseActivity
import com.example.pokedex.presentation.pokemonDetail.PokemonDetail

class PokemonsActivity : BaseActivity() {
    private lateinit var binding: ActivityPokemonsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupToolbar(binding.toolbarMain.toolbarMain, R.string.home_title)

        val viewModel = ViewModelProvider(
            this,
            PokemonsViewModel.MainViewModelFactory(PokemonsApiDataSource())
        ).get(PokemonsViewModel::class.java)

        viewModel.pokemonsLiveData.observe(this, {
            it?.let {
                with(binding.recyclerPokemon){
                    layoutManager = LinearLayoutManager(this@PokemonsActivity, RecyclerView.VERTICAL, false)
                    setHasFixedSize(true)
                    adapter = PokemonListAdapter(it) {imageUrl: String, pokemonNumber: Int, pokemonName: String ->
                        val intent = PokemonDetail.getStartIntent(this@PokemonsActivity, pokemonNumber, imageUrl, pokemonName)
                        startActivity(intent)
                    }
                }
            }
        })

        viewModel.viewFlipperLiveData.observe(this, {
            it?.let { viewFlipper ->
                binding.viewFlipperPokemons.displayedChild = viewFlipper.first
                viewFlipper.second?.let { errorMessageId ->
                    binding.textViewError.text = getString(errorMessageId)
                }
            }
        })

        viewModel.getPokemons()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("teste", "chegou")
    }

}