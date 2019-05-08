package lottery.com.helper

import android.content.Context
import android.preference.PreferenceManager

object PreferenceHelper {
    /**
     * Save phone number in screen.
     * @param context
     * @param value
     */
    fun savePhoneNumberInScreen(context: Context, value: String?) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val edit = preferences.edit()
        edit.putString("", value)
        edit.apply()
    }

    /**
     * Get phone number in screen.
     * @param context
     */
    fun getPhoneNumberInScreen(context: Context): String {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("", "")
    }

}