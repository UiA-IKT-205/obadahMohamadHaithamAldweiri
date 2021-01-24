package no.uia.ikt205.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime

class MainActivity : AppCompatActivity() {

    lateinit var timer:CountDownTimer
    lateinit var startButton:Button
    lateinit var coutdownDisplay:TextView
    lateinit var btn30:Button
    lateinit var btn60:Button
    lateinit var btn90:Button
    lateinit var btn120:Button



    var timeToCountDownInMs = 0L
    val timeTicks = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton = findViewById<Button>(R.id.startCountdownButton)

        btn30 = findViewById<Button>(R.id.btn30)
        btn60 = findViewById<Button>(R.id.btn60)
        btn90 = findViewById<Button>(R.id.btn90)
        btn120 = findViewById<Button>(R.id.btn120)

        btn30.setOnClickListener(){
            timeToCountDownInMs=1800000
        }

        btn60.setOnClickListener(){
            timeToCountDownInMs=3600000
        }

        btn90.setOnClickListener(){
            timeToCountDownInMs=5400000
        }

        btn120.setOnClickListener(){
            timeToCountDownInMs=7200000
        }
        startButton.setOnClickListener(){
            startCountDown(it)
        }
        coutdownDisplay = findViewById<TextView>(R.id.countDownView)
    }
    fun startCountDown(v: View){
        timer = object : CountDownTimer(timeToCountDownInMs,timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity,"Arbeidsøkt er ferdig", Toast.LENGTH_SHORT).show()
            }
            override fun onTick(millisUntilFinished: Long) {
                updateCountDownDisplay(millisUntilFinished)
            }
        }
        timer.start()
    }
    fun updateCountDownDisplay(timeInMs:Long){
        coutdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)
    }
}
