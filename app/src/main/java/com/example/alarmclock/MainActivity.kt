package com.example.alarmclock

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.alarmclock.fragment.TimePickerFragment
import com.example.alarmclock.receiver.AlarmReceiver
import java.util.*


class MainActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {
    lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textView)
        currentTime()
    }

    private fun currentTime() {
        val newtimer: CountDownTimer = object : CountDownTimer(1000000000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val c = Calendar.getInstance()
                val am_pm = c[Calendar.AM_PM]
                var AP: String
                if (am_pm == 0) {
                    AP = "AM"
                } else {
                    AP = "PM"
                }
                textView.setText(
                    c[Calendar.HOUR_OF_DAY].toString() + ":" + c[Calendar.MINUTE] + ":" + c[Calendar.SECOND] + " " + AP
                )
            }
            override fun onFinish() {}
        }
        newtimer.start()
    }

    fun showTimerPickerFragment(view: View) {
        val timePickerFragment =
            TimePickerFragment()
        timePickerFragment.show(supportFragmentManager, "time_picker")

    }

    fun cencelAlarm(view: View) {
        cencel()
    }

    override fun onTimeSet(timePicker: TimePicker?, hour: Int, minute: Int) {

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        startAlarm(calendar)
    }

    private fun startAlarm(calendar: Calendar) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1)
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        Toast.makeText(this, "Alarm set", Toast.LENGTH_SHORT).show()
    }

    private fun cencel() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        alarmManager.cancel(pendingIntent)
        Toast.makeText(this, "Alarm cencel", Toast.LENGTH_SHORT).show()
    }

}