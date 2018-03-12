package com.example.hh.salle_library

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import android.R.id.edit
import android.content.Context
import android.content.SharedPreferences



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
