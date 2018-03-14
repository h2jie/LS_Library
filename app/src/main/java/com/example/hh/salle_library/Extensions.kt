package com.example.hh.salle_library

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import android.R.id.edit
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONArray









/**
 * Created by Bhupinder Kumar on 11/03/2018.
 */
fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun saveObjectToSharedPreference(context: Context, preferenceFileName: String, serializedObjectKey: String, `object`: Any) {
    val sharedPreferences = context.getSharedPreferences(preferenceFileName, 0)
    val sharedPreferencesEditor = sharedPreferences.edit()
    val gson = Gson()
    val serializedObject = gson.toJson(`object`)
    sharedPreferencesEditor.putString(serializedObjectKey, serializedObject)
    sharedPreferencesEditor.apply()
}

fun <GenericClass> getSavedObjectFromPreference(context: Context, preferenceFileName: String, preferenceKey: String, classType: Class<GenericClass>): GenericClass? {
    val sharedPreferences = context.getSharedPreferences(preferenceFileName, 0)
    if (sharedPreferences.contains(preferenceKey)) {
        val gson = Gson()
        return gson.fromJson(sharedPreferences.getString(preferenceKey, ""), classType)
    }
    return null
}

private fun setDataFromSharedPreferences(curProduct: Book, context: Context, uid : String) {
    val gson = Gson()
    val jsonCurProduct = gson.toJson(curProduct)
    val sharedPref = context.getSharedPreferences(uid, Context.MODE_PRIVATE)
    val editor = sharedPref.edit()
    editor.putString("book", jsonCurProduct)
    editor.commit()
}

fun getDataFromSharedPreferences(uid : String, context: Context): List<Book> {
    val gson = Gson()
    var productFromShared: List<Book> = ArrayList()
    val sharedPref = context.getSharedPreferences(uid, Context.MODE_PRIVATE)
    val jsonPreferences = sharedPref.getString("book", "")

    val type = object : TypeToken<List<Book>>() {}.type
    productFromShared = gson.fromJson(jsonPreferences, type)

    return productFromShared
}

fun addInJSONArray(productToAdd: Book, uid: String, context: Context) {

    val gson = Gson()
    val sharedPref = context.getSharedPreferences(uid, Context.MODE_PRIVATE)

    val jsonSaved = sharedPref.getString("book", "")
    val jsonNewproductToAdd = gson.toJson(productToAdd)

    var jsonArrayProduct = JSONArray()

    try {
        if (jsonSaved!!.isNotEmpty()) {
            jsonArrayProduct = JSONArray(jsonSaved)
        }
        jsonArrayProduct.put(JSONObject(jsonNewproductToAdd))
    } catch (e: JSONException) {
        e.printStackTrace()
    }

    val editor = sharedPref.edit()
    editor.putString("book", gson.toJson(jsonArrayProduct))
    editor.commit()
}


