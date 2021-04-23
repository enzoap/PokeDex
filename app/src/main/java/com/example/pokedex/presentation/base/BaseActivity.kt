package com.example.pokedex.presentation.base

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.pokedex.R

open class BaseActivity: AppCompatActivity() {
    protected fun setupToolbar(toolbar: Toolbar, titleIdRes: Int, showBackButton: Boolean = false){
        toolbar.title = getString(titleIdRes)
        toolbar.setTitleTextColor(resources.getColor(R.color.white))
        setSupportActionBar(toolbar)
        if (showBackButton){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }
}