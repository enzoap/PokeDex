package com.example.pokedex.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

class Extensions {
    companion object {
        fun ImageView.load(imageUrl: String){
            Glide.with(this)
                    .load(imageUrl)
                    .into(this)
        }
    }

}