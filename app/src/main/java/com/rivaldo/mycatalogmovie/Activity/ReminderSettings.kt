package com.rivaldo.mycatalogmovie.Activity

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Switch
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.rivaldo.mycatalogmovie.AlarmReceiver
import com.rivaldo.mycatalogmovie.R

class ReminderSettings : AppCompatActivity() {

    private lateinit var alarmReceiver: AlarmReceiver
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder_settings)
        supportActionBar?.elevation = 0f
        alarmReceiver = AlarmReceiver()
        val releaseSwitch = findViewById<Switch>(R.id.Release_switch)
        val dailySwitch = findViewById<Switch>(R.id.Daily_switch)
        val sharedPrefs = getSharedPreferences("SwitchState", Context.MODE_PRIVATE)
        dailySwitch.setChecked(sharedPrefs.getBoolean("DailySwitch", true))
        dailySwitch.setOnCheckedChangeListener { _, isChecked ->

//          val msg = if (isChecked) "Daily Reminder Diaktifkan" else "Daily Reminder Dimatikan"
//            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
//            val settings = getSharedPreferences("DailyState", 0)
            if (isChecked) {
            val msg = "Daily Reminder Diaktifkan"
                Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
                val editor = getSharedPreferences(
                    "SwitchState",
                    Context.MODE_PRIVATE
                ).edit()
                editor.putBoolean("DailySwitch", true)
                editor.commit()
                alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.DAILY_REMINDER, "07:00", "Selamat Pagi !... Jangan lupa untuk selalu cek film favorit kamu ya")

            }
            else {
                val msg = "Daily Reminder dinonaktifkan"
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                val editor = getSharedPreferences(
                    "SwitchState", Context.MODE_PRIVATE
                ).edit()
                editor.putBoolean("DailySwitch", false)
                editor.commit();
                alarmReceiver.cancelAlarm(this, AlarmReceiver.DAILY_REMINDER)
            }
        }

        releaseSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val msg = "Release Reminder diaktifkan"
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                val editor = getSharedPreferences(
                    "SwitchState",
                    Context.MODE_PRIVATE
                ).edit()
                editor.putBoolean("ReleaseSwitch", true)
                editor.commit()
                alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.RELEASE_REMINDER, "08:00", "Halo... Ada yang baru nih. Buruan ya cek ")
            } else {
                val msg = "Release Reminder dinonaktifkan"
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                val editor = getSharedPreferences(
                    "SwitchState",
                    Context.MODE_PRIVATE
                ).edit()
                editor.putBoolean("ReleaseSwitch", false)
                editor.commit()
                alarmReceiver.cancelAlarm(this, AlarmReceiver.RELEASE_REMINDER)

            }
        }
    }
}
