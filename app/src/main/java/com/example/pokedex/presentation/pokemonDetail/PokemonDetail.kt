package com.example.pokedex.presentation.pokemonDetail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.pokedex.R
import com.example.pokedex.data.repository.PokemonDetailApiDataSource
import com.example.pokedex.databinding.ActivityPokemonDetailBinding
import com.example.pokedex.presentation.base.BaseActivity
import com.example.pokedex.utils.Extensions.Companion.load

class PokemonDetail : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPokemonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar(binding.toolbarMain.toolbarMain, R.string.detail_title, true)

        intent.getStringExtra(EXTRA_IMAGE_URL)?.let {
            binding.imagePokemon.load(it)
        }
        binding.numberPokemon.text = intent.getIntExtra(EXTRA_NUMBER, 0).toString()
        binding.name.text = intent.getStringExtra(EXTRA_NAME)

        val viewModel = ViewModelProvider(this, PokemonDetailViewModel.PokemonViewModelFactory(
            PokemonDetailApiDataSource()
        )).get(PokemonDetailViewModel::class.java)

        viewModel.pokemonLiveData.observe(this, {
            it?.let { pokemon ->
                for (item in pokemon){
                    when (item.statName) {
                        "hp" -> binding.hpBar.text = item.baseStat.toString()
                        "attack" -> binding.atackBar.text = item.baseStat.toString()
                        "defense" -> binding.defenseBar.text = item.baseStat.toString()
                        "speed" -> binding.speedBar.text = item.baseStat.toString()
                    }
                    binding.type1.text = item.type1
                    binding.type2.text = item.type2
                }
                when (binding.type1.text) {
//                    "grass" -> )
                }
            }
        })

        viewModel.errorLiveData.observe(this, {
            it?.let { status ->
                if (status.first == SUCESS){
                    binding.loading.visibility = View.GONE
                    binding.mainLayout.visibility = View.VISIBLE
                }
            }
        })

        viewModel.getPokemon(intent.getIntExtra(EXTRA_NUMBER, 0))
    }

    companion object {
        private const val EXTRA_NUMBER = "EXTRA_NUMBER"
        private const val EXTRA_NAME = "EXTRA_NAME"
        private const val EXTRA_IMAGE_URL = "EXTRA_IMAGE_URL"

        private const val SUCESS = 1
        private const val ERROR = 2

        fun getStartIntent(context: Context, numberPokemon: Int, imageUrl: String, namePokemon: String): Intent {
            return Intent(context, PokemonDetail::class.java)
                    .apply {
                        putExtra(EXTRA_NUMBER, numberPokemon)
                        putExtra(EXTRA_IMAGE_URL, imageUrl)
                        putExtra(EXTRA_NAME, namePokemon)
                    }
        }
    }
}