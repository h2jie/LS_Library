package com.example.hh.salle_library

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import kotlinx.android.synthetic.main.activity_authentication.*

class AuthenticationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        setSupportActionBar(toolbar)
        view_pager.adapter = TabsAdapter(supportFragmentManager)

        tab_layout.setupWithViewPager(view_pager)

    }



}
