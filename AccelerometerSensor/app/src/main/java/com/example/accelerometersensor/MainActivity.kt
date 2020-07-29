package com.example.accelerometersensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.abs

class MainActivity : AppCompatActivity(), SensorEventListener {
    private var mLastShakeTime: Long = 0
    private var lastX = 0f
    private var lastY = 0f
    private var lastZ = 0f

    private lateinit var sensorManager: SensorManager
    private var mSensor: Sensor? = null

    private lateinit var vibrator: Vibrator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        if (mSensor != null) {
            Toast.makeText(this, "There's a accelerometer sensor", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "No accelerometer sensor", Toast.LENGTH_SHORT).show()
        }

        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return

        val currentTime = System.currentTimeMillis()

        if (currentTime - mLastShakeTime > MIN_TIME_BETWEEN_SHAKES_MILLISECS) {
            val diffTime = currentTime - mLastShakeTime
            mLastShakeTime = currentTime

            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val speed = abs(x + y + z - lastX - lastY - lastZ) / diffTime * 10000

            textView.text = "Speed is $speed"

            if (speed > SHAKE_THRESHOLD) {
                vibrate()
                Toast.makeText(this, "Shake detected", Toast.LENGTH_SHORT).show()
            }

            lastX = x
            lastY = y
            lastZ = z
        }
    }

    private fun vibrate() {
        if (vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        500,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                vibrator.vibrate(500)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mSensor?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    companion object {
        private const val SHAKE_THRESHOLD = 3000
        private const val MIN_TIME_BETWEEN_SHAKES_MILLISECS = 100
    }
}