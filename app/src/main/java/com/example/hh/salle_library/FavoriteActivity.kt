package com.example.hh.salle_library

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_favorite.*


class FavoriteActivity : AppCompatActivity(), taskCompletedListenner{

    var bookList : ArrayList<Book> = ArrayList()

    private lateinit var recyclerView : RecyclerView
    private  lateinit var bookAdapter: BookAdapter
    private var mCallback : taskCompletedListenner = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        var sharedPref = applicationContext.getSharedPreferences(FirebaseAuth.getInstance().currentUser!!.uid,Context.MODE_PRIVATE)

        bookList = ObjectSerializer.deserialize(sharedPref.getString("books",ObjectSerializer.serialize(ArrayList<Book>()))) as ArrayList<Book>


        recyclerView = fav_recycler
        bookAdapter = BookAdapter(bookList, this,"any",mCallback)
        val mLayoutManager = GridLayoutManager(this, 1)
        recyclerView.layoutManager = mLayoutManager

        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = bookAdapter

    }

    override fun taskCompleted(mList: ArrayList<Book>) {
        bookList = mList
        recyclerView.adapter.notifyDataSetChanged() //TODO: Crear otro adapter para solo esta lista.
    }
}
