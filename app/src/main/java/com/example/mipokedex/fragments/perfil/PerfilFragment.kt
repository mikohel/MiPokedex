package com.example.mipokedex.fragments.perfil

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.example.mipokedex.R
import com.example.mipokedex.databinding.FragmentPerfilBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONObject

private lateinit var database: DatabaseReference

class PerfilFragment : Fragment() {

    private lateinit var binding: FragmentPerfilBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myDB = FirebaseDatabase.getInstance()
        database = myDB.reference



        binding.btnUser.setOnClickListener{
            if(!binding.tvBusqueda.text.toString().isEmpty())
            {
                getUser(binding.tvBusqueda.text.toString())
                binding.tvBusqueda.text.clear()

            }

        }


    }


    fun getUser(userId: String){
        database.child("usuarios").child(userId).get().addOnSuccessListener { record ->
            val json = JSONObject(record.value.toString())
            Log.d("ValoresFirebase", "got value ${record.value}")
            binding.t5.setText(json.getString("usuario"))
            binding.t6.setText(json.getString("mote"))
            binding.t7.setText(json.getString("capturados"))
            binding.t8.setText(json.getString("nivel"))


        }
    }
}