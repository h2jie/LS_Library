package com.example.hh.salle_library

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.MenuItem
import android.widget.TextView
import com.example.hh.salle_library.R.id.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main_screen.*
import kotlinx.android.synthetic.main.content_layout.*

class MainScreen : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, taskCompletedListenner {

    var bookList : ArrayList<Book> = ArrayList()


    private lateinit var recyclerView : RecyclerView
    private  lateinit var bookAdapter: BookAdapter

    //var userSession= UserSession(this)
    var mAuth = FirebaseAuth.getInstance()




    private var mCallback : taskCompletedListenner = this



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var myTask = GettingBooksTask("fools", mCallback)
        myTask.execute()




        setContentView(R.layout.activity_main_screen)
        nav_menu.setNavigationItemSelectedListener(this)
        setSupportActionBar(appbar)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        recyclerView = main_recycler_view
        bookAdapter = BookAdapter(bookList, this,"main",null)
        val mLayoutManager = GridLayoutManager(this, 1)
        recyclerView.layoutManager = mLayoutManager

        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = bookAdapter

        var header = nav_menu.getHeaderView(0)
        var name = header.findViewById<TextView>(R.id.currentuser)
        if (name != null) {
            name.text = FirebaseAuth.getInstance().currentUser!!.displayName
        }

        var email = header.findViewById<TextView>(R.id.currentemail)
        if(email != null){
            email.text = FirebaseAuth.getInstance().currentUser!!.email
        }

    }

    override fun taskCompleted(mList: ArrayList<Book>){
        for (i in 0 until mList.size){
            bookList.add(mList[i])
        }
        recyclerView.adapter.notifyDataSetChanged()


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            nav_fav -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
            logout ->{
                mAuth.signOut()
                val intent = Intent(this, AuthenticationActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        drawer_layout.closeDrawers()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{
                drawer_layout.openDrawer(Gravity.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
