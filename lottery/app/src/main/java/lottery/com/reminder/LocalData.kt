package lottery.com.reminder

import android.content.Context
import android.content.SharedPreferences

class LocalData(context: Context) {

    private val appSharedPrefs: SharedPreferences
    private val prefsEditor: SharedPreferences.Editor

    init {
        this.appSharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE)
        this.prefsEditor = appSharedPrefs.edit()
    }
    // Settings Page Reminder Time (Hour)

    var getHour: Int
        get() = appSharedPrefs.getInt(hour, 20)
        set(h) {
            prefsEditor.putInt(hour, h)
            prefsEditor.commit()
        }

    // Settings Page Reminder Time (Minutes)

    var getMin: Int
        get() = appSharedPrefs.getInt(min, 0)
        set(m) {
            prefsEditor.putInt(min, m)
            prefsEditor.commit()
        }

    // Settings Page Set Reminder

    fun getReminderStatus(): Boolean {
        return appSharedPrefs.getBoolean(reminderStatus, false)
    }

    fun setReminderStatus(status: Boolean) {
        prefsEditor.putBoolean(reminderStatus, status)
        prefsEditor.commit()
    }

    fun reset() {
        prefsEditor.clear()
        prefsEditor.commit()

    }

    companion object {

        private const val APP_SHARED_PREFS = "RemindMePref"

        private const val reminderStatus = "reminderStatus"
        private const val hour = "hour"
        private const val min = "min"
    }

}
