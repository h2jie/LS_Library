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


class FavoriteActivity : AppCompatActivity() {

    var bookList : ArrayList<Book> = ArrayList()

    private lateinit var recyclerView : RecyclerView
    private  lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        var sharedPref = applicationContext.getSharedPreferences(FirebaseAuth.getInstance().currentUser!!.uid,Context.MODE_PRIVATE)


        /*val connectionsJSONString = getPreferences(Context.MODE_PRIVATE).getString(FirebaseAuth.getInstance().currentUser!!.uid, null)
        val type = object : TypeToken<Book>() {}.type
        val mbook = Gson().fromJson<Book>(connectionsJSONString,type)
        //val connections = Gson().fromJson(connectionsJSONString, type)*/


        val mbook = getSavedObjectFromPreference(this,"mPref",FirebaseAuth.getInstance().currentUser!!.uid, Book::class.java)

        //bookList.add(mbook!!)
        //bookList = getDataFromSharedPreferences(FirebaseAuth.getInstance().currentUser!!.uid,applicationContext) as ArrayList<Book>
        bookList = ObjectSerializer.deserialize(sharedPref.getString("books",ObjectSerializer.serialize(ArrayList<Book>()))) as ArrayList<Book>


        recyclerView = fav_recycler
        bookAdapter = BookAdapter(bookList, this)
        val mLayoutManager = GridLayoutManager(this, 1)
        recyclerView.layoutManager = mLayoutManager

        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = bookAdapter




    }
}
