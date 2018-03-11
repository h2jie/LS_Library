package com.example.hh.salle_library

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.MenuItem
import com.example.hh.salle_library.R.id.nav_fav
import kotlinx.android.synthetic.main.activity_main_screen.*
import kotlinx.android.synthetic.main.content_layout.*
import java.net.URI

class MainScreen : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, taskCompletedListenner {

    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var adapter: RecyclerAdapter
    var bookList : ArrayList<Book> = ArrayList()

    private var mCallback : taskCompletedListenner = this



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var book = Book(URI(""), "nicotine","heroine","need","perdona?")
        bookList.add(book)
        var myTask = GettingBooksTask("fools", mCallback)
        myTask.execute()


        setContentView(R.layout.activity_main_screen)
        nav_menu.setNavigationItemSelectedListener(this)
        setSupportActionBar(appbar)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        main_recycler_view.layoutManager = linearLayoutManager


    }

    override fun taskCompleted(mList: ArrayList<Book>){
        for (i in 0 until mList.size){
            bookList.add(mList[i])
        }
        //adapter = RecyclerAdapter(mList)
        adapter = RecyclerAdapter(bookList)
        adapter.notifyItemChanged(bookList.size)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            nav_fav -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
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
