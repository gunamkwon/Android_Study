package com.example.timer

import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.SeekBar
import com.example.timer.databinding.ActivityMainBinding
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private var currentCountDownTimer: CountDownTimer? = null
    private var tickingSoundId: Int? = null
    private var bellSoundId: Int? = null

    private val soundPool = SoundPool.Builder().build()

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
                    startCountDown(seekBar)
                }
            })
        }
        initSounds()
    }

    override fun onResume() {
        super.onResume()
        soundPool.autoResume()
    }

    override fun onPause() {
        super.onPause()
        soundPool.autoPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }

    private fun initSounds() {
        tickingSoundId = soundPool.load(this, R.raw.timer_ticking, 1)
        bellSoundId = soundPool.load(this, R.raw.timer_bell,1)
    }

    private fun createCountDownTimer(initialMillis: Long): CountDownTimer =
        object: CountDownTimer(initialMillis, 1000L){
            override fun onTick(millisUntilFinished: Long) {
                updateRemainingTime(millisUntilFinished)
                updateSeekBar(millisUntilFinished)
            }
            override fun onFinish() {
                completeCountDown()
            }
        }

    private fun startCountDown(seekBar: SeekBar) {
        currentCountDownTimer = createCountDownTimer(seekBar.progress * 60 * 1000L)
        currentCountDownTimer?.start()

        tickingSoundId?.let { soundId ->
            soundPool.play(soundId,1F,1F, 0, -1, 1F)
        }
    }
    private fun completeCountDown() {
        updateRemainingTime(0)
        updateSeekBar(0)

        soundPool.autoPause()
        bellSoundId?.let { soundId ->
            soundPool.play(soundId, 1F, 1F, 0, 0, 1F)
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
