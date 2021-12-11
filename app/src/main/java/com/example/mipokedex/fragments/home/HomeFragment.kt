package com.example.mipokedex.fragments.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.mipokedex.database.Pokemon

import com.example.mipokedex.databinding.FragmentHomeBinding
import com.example.mipokedex.datos.SaveDataModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONObject
import java.security.Principal



class HomeFragment : Fragment() {

    lateinit var queue: RequestQueue
    private lateinit var poke_image: String
    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: DatabaseReference
    private val saveDatamodel : SaveDataModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        val etPokemonName = binding.etPokemonSearch.text

        queue = Volley.newRequestQueue(activity)

        binding.btnSearch.setOnClickListener{
            if(!binding.etPokemonSearch.text.toString().isEmpty())
            {
                getPokemon(binding.etPokemonSearch.text.toString())
                binding.etPokemonSearch.text.clear()

            }

        }
        binding.guardarDatos.setOnClickListener {
            guardar()
            actualizarPerfil()
            val destination= HomeFragmentDirections.actionHomeFragment2ToPokedexFragment2()
            NavHostFragment.findNavController(this).navigate(destination)
        }


        return binding.root

    }
    fun getPokemon(pokemonName: String) {
        val url = "https://pokeapi.co/api/v2/pokemon/${pokemonName.lowercase()}"

        val jsonRequest = JsonObjectRequest(url, Response.Listener<JSONObject> { response ->
            val name = response.getString("name")
            val id = response.getString("id")
            val peso = response.getInt("weight")
            val tipo = response.getJSONArray("types").getJSONObject(0).getJSONObject("type").getString("name")
            val nombre= response.getString("name")
            val hp = response.getJSONArray("stats").getJSONObject(0).getInt("base_stat")
            val ataqye = response.getJSONArray("stats").getJSONObject(1).getInt("base_stat")
            val defensa = response.getJSONArray("stats").getJSONObject(2).getInt("base_stat")
            val velocidad = response.getJSONArray("stats").getJSONObject(5).getInt("base_stat")


            poke_image = response.getJSONObject("sprites").getString("front_default")
            val infoString = "$name #$id"
            binding.tvPokemonInfo.setText(infoString)
            binding.mostrarNombre.setText(nombre.toString())
            binding.mostrarTipo.setText(tipo)
            binding.mostrarHp.setText(hp.toString())
            binding.mostrarAtack.setText(ataqye.toString())
            binding.mostrarDefense.setText(defensa.toString())
            binding.mostrarSpeed.setText(velocidad.toString())
            binding.mostrarWeight.setText(peso.toString())
            binding.mostrarID.setText(id.toString())
            Glide.with(this).load(poke_image).into(binding.imageView3)
        },
            Response.ErrorListener { errorMessage ->
               binding.tvPokemonInfo.setText("404 Pokemon Not Found")
            })
        queue.add(jsonRequest)
    }
    override fun onStop(){
        super.onStop()
        queue.cancelAll("stopped")
    }

    private fun guardar(){
        saveDatamodel.save(
            Pokemon(

                binding.mostrarID.text.toString().toInt(),
                binding.mostrarNombre.text.toString(),
                binding.mostrarTipo.text.toString(),
                binding.mostrarHp.text.toString().toInt(),
                binding.mostrarAtack.text.toString().toInt(),
                binding.mostrarDefense.text.toString().toInt(),
                binding.mostrarSpeed.text.toString().toInt(),
                binding.mostrarWeight.text.toString().toInt(),
                poke_image
        )
        )

        Log.d("mensaje", "${poke_image}")
    }

    private fun actualizarPerfil(){
        val myDB= FirebaseDatabase.getInstance()
        database=myDB.reference
        var pokemon:Int=0
        database.child("usuarios").child("1").get().addOnSuccessListener { record ->

            if (record.exists()) {
                val json = JSONObject(record.value.toString())
                pokemon=json.getInt("pokemon")
                val actualizarPok = hashMapOf<String, Any>(
                    "/usuarios/1/pokemon" to pokemon+1
                )
                Log.d("nuevop", "${pokemon}")
                database.updateChildren(actualizarPok)
            }else{
                Log.d("Mensaje", "No se encontró el usuario")
            }
        }


    }

    }

