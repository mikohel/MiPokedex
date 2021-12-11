package com.example.mipokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.ui.setupWithNavController


class MainActivity : AppCompatActivity() {
    private  lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.bottom_nav)
        val navHomeFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHomeFragment.navController
        navView.setupWithNavController(navController)



    }
    }



