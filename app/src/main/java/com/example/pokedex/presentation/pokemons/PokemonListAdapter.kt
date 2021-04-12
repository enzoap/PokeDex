package com.example.pokedex.presentation.pokemons

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.data.model.Pokemon
import com.example.pokedex.data.model.PokemonList
import com.example.pokedex.databinding.ItemPokemonBinding
import com.example.pokedex.utils.Extensions.Companion.load

class PokemonListAdapter(private val pokemons: List<PokemonList>, private val onItemClickListener: ((pokemon: PokemonList) -> Unit)): RecyclerView.Adapter<PokemonListAdapter.ViewHolder>() {

    class ViewHolder(itemPokemonBinding: ItemPokemonBinding, private val onItemClickListener: (pokemon: PokemonList) -> Unit): RecyclerView.ViewHolder(itemPokemonBinding.root){
        private val number = itemPokemonBinding.numberPokemon
        private val name = itemPokemonBinding.namePokemon
        private val image = itemPokemonBinding.imgPokemon

        fun bindView(pokemon: PokemonList, pokemonNumber: Int){
            number.text = pokemonNumber.toString()
            name.text = pokemon.name
            image.load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonNumber.png")

            itemView.setOnClickListener {
                onItemClickListener.invoke(pokemon)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(pokemons[position], position+1)
    }

    override fun getItemCount() = pokemons.count()


}