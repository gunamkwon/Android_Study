package com.example.timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import com.example.timer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.seekbarTimer.apply {
            max = 3599
            setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    var second = progress % 60
                    var minute = progress / 60
                    binding.apply {
                        binding.textSecond.text = if(second < 10) "0${second}" else "${second}"
                        binding.textMinute.text = if(minute < 10 )"0${progress / 60}" else "${progress / 60}"
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    var second = progress % 60
                    var minute = progress / 60
                    binding.apply {
                        binding.textSecond.text = if(second < 10) "0${second}" else "${second}"
                        binding.textMinute.text = if(minute < 10 )"0${progress / 60}" else "${progress / 60}"
                    }
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    var second = progress % 60
                    var minute = progress / 60
                    binding.apply {
                        binding.textSecond.text = if(second < 10) "0${second}" else "${second}"
                        binding.textMinute.text = if(minute < 10 )"0${progress / 60}" else "${progress / 60}"
                    }
                }
            })
        }
    }
}