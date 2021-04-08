package com.example.pokedex.presentation.pokemons

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.data.repository.PokemonsApiDataSource
import com.example.pokedex.databinding.ActivityPokemonsBinding

class PokemonsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPokemonsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = getString(R.string.pokemons_title)
        setSupportActionBar(binding.toolbar)

        val viewModel = ViewModelProvider(
            this,
            PokemonsViewModel.MainViewModelFactory(PokemonsApiDataSource())
        ).get(PokemonsViewModel::class.java)
        viewModel.pokemonsLiveData.observe(this, {
            it?.let {
                with(binding.recyclerPokemon){
                    layoutManager = LinearLayoutManager(this@PokemonsActivity, RecyclerView.VERTICAL, false)
                    setHasFixedSize(true)
                    adapter = PokemonListAdapter(it) {
                        //todo: ClickListener
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
}