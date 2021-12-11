package com.example.mipokedex.datos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mipokedex.database.DataBaseManager
import com.example.mipokedex.database.MyAppDataSource

import com.example.mipokedex.database.Pokemon
import kotlinx.coroutines.launch

class GetDataModel: ViewModel() {
    val savedPokemon= MutableLiveData<List<Pokemon>>()
    fun getPokemon(){
        viewModelScope.launch {
            val pokemonDao = DataBaseManager.instance.database.pokemonDao()
            savedPokemon.value = MyAppDataSource(pokemonDao).getPokemon().value
        }
    }
    fun delete(pokemon: Pokemon){
        viewModelScope.launch {
            val pokemonDao = DataBaseManager.instance.database.pokemonDao()
            MyAppDataSource(pokemonDao).delete(pokemon)
            getPokemon()
        }

    }
}