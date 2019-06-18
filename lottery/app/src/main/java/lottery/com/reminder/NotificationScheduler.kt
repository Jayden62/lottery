package lottery.com.reminder

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.support.v4.app.TaskStackBuilder
import lottery.com.R
import lottery.com.screens.home.HomeActivity
import java.util.*

object NotificationScheduler {
    private const val DAILY_REMINDER_REQUEST_CODE = 100
    val TAG = "NotificationScheduler"

    internal fun setReminder(context: Context, hour: Int, min: Int) {
        val calendar = Calendar.getInstance()

        val mCalendar = Calendar.getInstance()
        mCalendar.set(Calendar.HOUR_OF_DAY, hour)
        mCalendar.set(Calendar.MINUTE, min)
        mCalendar.set(Calendar.SECOND, 0)

        // cancel already scheduled reminders
        cancelReminder(context)

        if (mCalendar.before(calendar))
            mCalendar.add(Calendar.DATE, 1)

        // Enable a receiver

        val receiver = ComponentName(context, AlarmReceiver::class.java)
        val pm = context.packageManager

        pm.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )


        val intent1 = Intent(context, AlarmReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, DAILY_REMINDER_REQUEST_CODE, intent1, PendingIntent.FLAG_UPDATE_CURRENT)
        val am = context.getSystemService(ALARM_SERVICE) as AlarmManager
        am?.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            mCalendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

    }

    private fun cancelReminder(context: Context) {
        // Disable a receiver

        val receiver = ComponentName(context, AlarmReceiver::class.java)
        val pm = context.packageManager

        pm.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )

        val intent1 = Intent(context, AlarmReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, DAILY_REMINDER_REQUEST_CODE, intent1, PendingIntent.FLAG_UPDATE_CURRENT)
        val am = context.getSystemService(ALARM_SERVICE) as AlarmManager
        am?.cancel(pendingIntent)
        pendingIntent.cancel()
    }

    internal fun showNotification(context: Context?) {
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationIntent = Intent(context, HomeActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        val stackBuilder = TaskStackBuilder.create(context!!)
        stackBuilder.addParentStack(HomeActivity::class.java)
        stackBuilder.addNextIntent(notificationIntent)

        val pendingIntent =
            stackBuilder.getPendingIntent(DAILY_REMINDER_REQUEST_CODE, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context)

        val notification = builder.setContentTitle("Bạn có lịch hẹn tại phòng mạch ?")
            .setContentText("Xem ngay.")
            .setAutoCancel(true)
            .setSound(alarmSound)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentIntent(pendingIntent).build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(DAILY_REMINDER_REQUEST_CODE, notification)

    }

}