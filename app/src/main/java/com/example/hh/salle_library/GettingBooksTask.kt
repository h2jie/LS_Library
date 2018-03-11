package com.example.hh.salle_library

import android.os.AsyncTask
import com.android.volley.toolbox.JsonObjectRequest
import java.net.URL

/**
 * Created by Bhupinder Kumar on 11/03/2018.
 */
class GettingBooksTask() : AsyncTask<URL,Integer,ArrayList<Book>>() {


    override fun doInBackground(vararg p0: URL?): ArrayList<Book>? {
        val url = "https://www.googleapis.com/books/v1/volumes?q=fools&key=AIzaSyA0MK7Suuz3RhOUzMhKfmHt6m5urhgJT-Q"

        //val jsonObjectRequest = JsonObjectRequest
        return null


    }
}