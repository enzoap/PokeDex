package com.example.pokedex.presentation.pokemonDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pokedex.R
import com.example.pokedex.databinding.ActivityPokemonDetailBinding
import com.example.pokedex.presentation.base.BaseActivity

class PokemonDetail : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPokemonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar(binding.toolbarMain.toolbarMain, R.string.detail_title, true)
    }
}