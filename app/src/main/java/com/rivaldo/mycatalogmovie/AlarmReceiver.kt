package com.rivaldo.mycatalogmovie

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import com.rivaldo.mycatalogmovie.Activity.MainActivity
import com.rivaldo.mycatalogmovie.Activity.ReleaseActivity
import java.text.ParseException
import java.util.*

class AlarmReceiver : BroadcastReceiver(){

    companion object {
        const val DAILY_REMINDER = "DailyReminder"
        const val RELEASE_REMINDER = "ReleaseReminder"
        const val EXTRA_MESSAGE = "message"
        const val EXTRA_TYPE = "type"

        private const val ID_DAILY_REMINDER = 100
        private const val ID_RELEASE_REMINDER = 101

        private const val DATE_FORMAT = "yyyy-MM-dd"
        private const val TIME_FORMAT = "HH:mm"


    }
    override fun onReceive(context: Context?, intent: Intent?) {
        val type = intent?.getStringExtra(EXTRA_TYPE)
        val message = intent?.getStringExtra(EXTRA_MESSAGE)

        val title = if (type.equals(DAILY_REMINDER, ignoreCase = true)) DAILY_REMINDER else RELEASE_REMINDER
        val notifId = if (type.equals(DAILY_REMINDER, ignoreCase = true)) ID_DAILY_REMINDER else ID_RELEASE_REMINDER

        showToast(context, title, message)
    }

    private fun showToast(context: Context?, title: String, message: String?) {
        Toast.makeText(context, "$title : $message", Toast.LENGTH_LONG).show()

    }

    private fun showAlarmNotification(context: Context?, title: String, message: String, notifId: Int) {
        val CHANNEL_ID = "Channel_1"
        val CHANNEL_NAME = "AlarmManager channel"

        val intent = Intent(context, ReleaseActivity::class.java)
//          Intent ke activity Release Today
        val pendingIntent = PendingIntent.getActivity(context,0,intent,0)

        val notificationManagerCompat = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.baseline_access_time_black_18dp)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000,1000,1000, 1000)
            builder.setChannelId(CHANNEL_ID)
            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManagerCompat.notify(notifId, notification)


    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun setRepeatingAlarm(context: Context, type: String, time:String, message: String) {

        if (isDateInvalid(time, TIME_FORMAT)) return

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE,message)
        val putExtra = intent.putExtra(EXTRA_TYPE, type)

        val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND, 0)
        if (type.equals(DAILY_REMINDER, ignoreCase = true)) {
            val pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY_REMINDER, intent, 0)
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
            Toast.makeText(context, "Daily Reminder is Set Up", Toast.LENGTH_SHORT).show()
        } else {
            val pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE_REMINDER, intent, 0)
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
            Toast.makeText(context, "Release Reminder is Set Up", Toast.LENGTH_SHORT).show()
        }
        //alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)



    }

    fun cancelAlarm(context: Context?, type:String) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val requestCode = if (type.equals(DAILY_REMINDER, ignoreCase = true)) ID_DAILY_REMINDER else ID_RELEASE_REMINDER
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode,intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, "Repeating alarm dibatalkan", Toast.LENGTH_SHORT).show()

    }

    fun isAlarmSet(context: Context?, type: String): Boolean {
        val intent = Intent(context, AlarmReceiver::class.java)
        val requestCode = if (type.equals(DAILY_REMINDER,ignoreCase = true)) ID_DAILY_REMINDER else ID_RELEASE_REMINDER

        return PendingIntent.getBroadcast(context, requestCode,intent, PendingIntent.FLAG_NO_CREATE) != null

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun isDateInvalid(date: String, format: String): Boolean {
        return try {
            val df = SimpleDateFormat(format, Locale.getDefault())
            df.isLenient = false
            df.parse(date)
            false
        } catch (e: ParseException) {
            true
        }
    }
}