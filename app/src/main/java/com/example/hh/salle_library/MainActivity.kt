package com.example.hh.salle_library

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO: https://developer.android.com/training/implementing-navigation/nav-drawer.html usando este
        //TODO: https://developer.android.com/training/implementing-navigation/nav-drawer.html?hl=es-419
        //TODO http://www.sgoliver.net/blog/interfaz-de-usuario-en-android-navigation-drawer-navigationview/

        nav_view.setNavigationItemSelectedListener {  }




    }
}
