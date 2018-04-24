package com.example.hh.salle_library

import android.os.AsyncTask
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.URI

import java.net.URL

/**
 * Clase que nos permite realizar tareas asincronas
 *
 * Esta clase realiza la tarea de hacer una llamada a la API de Google Books, para recuperar los datos de los libros por su nombre.
 *
 * @property name Nombre del libro del cual se desea buscar información.
 * @property mCallBack Interfaz que comunica la clase con la actividad, y la cual se encarga de pasarle la información una vez se ha finalizado la tarea de recuperar información.
 * @param bookList lista de libro
 * @param delegate hace reload la lista
 */
class GettingBooksTask(var name : String,var mCallBack : taskCompletedListenner) : AsyncTask<URL,Integer,ArrayList<Book>>() {

    var bookList : ArrayList<Book> = ArrayList<Book>()
    var delegate = mCallBack


    /**
     * Método en el que se realiza toda tarea asincrona.
     *
     * Buscar información sobre libros.
     */
    override fun doInBackground(vararg p0: URL?): ArrayList<Book>? {

        var failed : Boolean = false

        var client = OkHttpClient()
        if (name.isEmpty()){
            name = "Happy"
        }
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

                for (i in 0 until mJsonArray.length()){
                    var mJsonObjectItemInfo = mJsonArray.getJSONObject(i)

                    var mJsonObjectVolumeInfo = mJsonObjectItemInfo.getJSONObject("volumeInfo")

                    var itemTitle = mJsonObjectVolumeInfo["title"] as String

                    var itemAuthor : String
                    try {
                        var itemAuthorArray : JSONArray = mJsonObjectVolumeInfo.getJSONArray("authors")
                        itemAuthor = itemAuthorArray[0] as String

                    }catch (e : JSONException){
                        itemAuthor = "Anonimo"
                    }

                    var itemDate : String
                    try {
                        itemDate = mJsonObjectVolumeInfo["publishedDate"] as String

                    }catch (e : JSONException){
                        itemDate = "Fecha no definida"

                    }

                    var itemDesc : String

                    try {
                        itemDesc = mJsonObjectVolumeInfo["description"] as String
                    }catch (e : JSONException){
                        itemDesc = "Descripción no disponible"
                    }


                    var itemImgUrlStr : String
                    var itemImgUrl : URL
                    try {
                        var mJasonObjectVolumeImg : JSONObject = mJsonObjectVolumeInfo.getJSONObject("imageLinks")
                        itemImgUrlStr = mJasonObjectVolumeImg["smallThumbnail"] as String
                         itemImgUrl = URL(itemImgUrlStr)

                    }catch (e : JSONException){
                        itemImgUrlStr = "http://thai-dream.ru/themes/classic/images/no-img.jpg"
                        itemImgUrl = URL(itemImgUrlStr)
                    }
                    var book = Book(itemImgUrl, itemTitle,itemAuthor,itemDate,itemDesc)
                    bookList.add(book)
                }
            }
        }
        return bookList
    }

    /**
     * Método que se ejecuta una vez finalizada la tarea asincrona.
     *
     * En este caso nos disponemos de pasar la información recuperada a la actividad.
     */
    override fun onPostExecute(result: ArrayList<Book>?) {
        super.onPostExecute(result)
        if (result != null) {
            delegate.taskCompleted(result)
        }
    }

}