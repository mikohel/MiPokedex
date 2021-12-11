package com.example.mipokedex.datos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mipokedex.database.DataBaseManager
import com.example.mipokedex.database.MyAppDataSource
import com.example.mipokedex.database.Pokemon
import kotlinx.coroutines.launch

class SaveDataModel: ViewModel() {
    fun save(pokemon: Pokemon){
        viewModelScope.launch {
            val pokemonDao = DataBaseManager.instance.database.pokemonDao()
            MyAppDataSource(pokemonDao).save(pokemon)
        }
    }

}