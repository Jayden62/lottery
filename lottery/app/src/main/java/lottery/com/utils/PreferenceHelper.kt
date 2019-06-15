package lottery.com.utils

import android.content.Context
import android.preference.PreferenceManager
import lottery.com.model.User
import com.google.gson.Gson
import android.R.id.edit


object PreferenceHelper {


    fun saveUser(context: Context, user: User?) {
//        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
//        val edit = preferences.edit()
//        val gson = Gson()
//        val json = gson.toJson(user)
//        edit.putString(Constants.Data.MY_MODEL, json)
//        edit.apply()
        val mPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val prefsEditor = mPrefs.edit()
        val gson = Gson()
        val json = gson.toJson(user)
        prefsEditor.putString(Constants.Data.MY_MODEL, json)
        prefsEditor.apply()
    }


    fun getUser(context: Context): User? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val gson = Gson()
        val json = preferences.getString(Constants.Data.MY_MODEL, "")
        val obj = gson.fromJson(json, User::class.java)
        return obj
    }

}