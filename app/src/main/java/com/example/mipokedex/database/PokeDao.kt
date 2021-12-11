package com.example.mipokedex.database


import androidx.room.*

@Dao
interface PokeDao {
    @Delete
    fun delete(pokemon: PokemonEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun save(pokemon: PokemonEntity)

    @Query("SELECT * FROM $TABLE_POKEMON")
    fun getPokemonFromDatabase(): List<PokemonEntity>
}