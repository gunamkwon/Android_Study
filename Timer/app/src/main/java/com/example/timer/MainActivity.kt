package com.example.timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.SeekBar
import com.example.timer.databinding.ActivityMainBinding
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private var currentCountDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.seekbarTimer.apply {
            max = 60
            setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if(fromUser)
                        updateRemainingTime(progress * 60 * 1000L)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    currentCountDownTimer?.cancel()
                    currentCountDownTimer = null
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    seekBar ?: return
                    currentCountDownTimer = createCountDownTimer(seekBar.progress * 60 * 1000L)
                    currentCountDownTimer?.start()
                }
            })
        }
    }

    private fun createCountDownTimer(initialMillis: Long): CountDownTimer =
        object: CountDownTimer(initialMillis, 1000L){
            override fun onTick(millisUntilFinished: Long) {
                updateRemainingTime(millisUntilFinished)
                updateSeekBar(millisUntilFinished)
            }
            override fun onFinish() {
                updateRemainingTime(0)
                updateSeekBar(0)
            }
        }

    private fun updateRemainingTime(remainMillis: Long) {
        val remainSeconds = remainMillis / 1000
        binding.textMinute.text = "%02d".format(remainSeconds / 60)
        binding.textSecond.text = "%02d".format(remainSeconds % 60)
    }

    private fun updateSeekBar(remainMillis: Long) {
        binding.seekbarTimer.progress = (remainMillis / 1000 / 60).toInt()
    }
}
