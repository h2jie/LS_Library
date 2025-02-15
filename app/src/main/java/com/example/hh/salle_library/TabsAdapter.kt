package com.example.hh.salle_library

import android.support.v4.app.FragmentManager
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter

/**
 * Clase adaptador que controla que fragment se muestra por pantalla.
 */
class TabsAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager){

    val fragmentTitles = arrayOf("Register", "Login")

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> RegisterFragment()
            1 -> LoginFragment()
            else -> RegisterFragment()
        }
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence {
        return fragmentTitles[position]
    }

}