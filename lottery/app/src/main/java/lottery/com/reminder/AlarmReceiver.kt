package lottery.com.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log


class AlarmReceiver : BroadcastReceiver() {

    private var TAG = "AlarmReceiver"

    override fun onReceive(context: Context?, intent: Intent) {

        if (intent.action != null && context != null) {
            if (intent.action!!.equals(Intent.ACTION_BOOT_COMPLETED, ignoreCase = true)) {
                // Set the alarm here.
                Log.d(TAG, "onReceive: BOOT_COMPLETED")
                val localData = LocalData(context)
                NotificationScheduler.setReminder(context, localData.getHour, localData.getMin)
                return
            }
        }

        Log.d(TAG, "onReceive: ")
        //Trigger the notification
        NotificationScheduler.showNotification(context)

    }
}