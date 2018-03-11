package com.example.hh.salle_library

import android.os.AsyncTask
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.URI

import java.net.URL

/**
 * Created by Bhupinder Kumar on 11/03/2018.
 */
class GettingBooksTask(var name : String, var mCallBack : taskCompletedListenner) : AsyncTask<URL,Integer,ArrayList<Book>>() {

    var bookList : ArrayList<Book> = ArrayList<Book>()
    var delegate = mCallBack



    override fun doInBackground(vararg p0: URL?): ArrayList<Book>? {

        var failed : Boolean = false

        var client = OkHttpClient()

        var request = Request.Builder()
                .url("https://www.googleapis.com/books/v1/volumes?q=$name&maxResults=20&key=AIzaSyA0MK7Suuz3RhOUzMhKfmHt6m5urhgJT-Q")
                .build()
        var response : Response? = null

        try {
            response = client.newCall(request).execute()
        }catch (ignore : IOException){
            failed = true
        }

        if (!failed){
            var responseStr : String? = null

            try {
                if (response != null) {
                    responseStr = response.body().string()
                }
            }catch (ignore : IOException){}


            var responseJson : JSONObject? = null
            try {
                responseJson = JSONObject(responseStr)
            }catch (ignore : JSONException){}


            if (responseJson != null) {

                var mJsonArray = responseJson.getJSONArray("items")

                for (i in 0..19){
                    var mJsonObjectItemInfo = mJsonArray.getJSONObject(i)
                    var mJsonObjectVolumeInfo = mJsonObjectItemInfo.getJSONObject("volumeInfo")

                    var itemTitle = mJsonObjectVolumeInfo["title"] as String
                    //var itemAuthorArray = mJsonObjectVolumeInfo.getJSONArray("authors")
                    //var itemAuthor = itemAuthorArray[0] as String
                    var itemDate = mJsonObjectVolumeInfo["publishedDate"] as String
                    //var itemDesc = mJsonObjectVolumeInfo["description"] as String

                    var mJasonObjectVolumeImg = mJsonObjectVolumeInfo.getJSONObject("imageLinks")

                    //var itemImgUrl = mJasonObjectVolumeImg["smallThumbnail"] as String
                    var itemImgUrl =  URI.create(mJasonObjectVolumeImg["smallThumbnail"] as String)

                    var book = Book(itemImgUrl, itemTitle,"holas",itemDate,"perdona?")
                    bookList.add(book)
                }
            }
        }
        return bookList
    }

    override fun onPostExecute(result: ArrayList<Book>?) {
        super.onPostExecute(result)
        if (result != null) {
            delegate.taskCompleted(result)
        }
    }

}