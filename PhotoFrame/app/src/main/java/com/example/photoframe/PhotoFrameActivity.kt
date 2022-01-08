package com.example.photoframe

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.photoframe.databinding.ActivityPhotoframeBinding
import java.util.*
import kotlin.concurrent.timer

class PhotoFrameActivity : AppCompatActivity() {

    lateinit var binding : ActivityPhotoframeBinding

    private val photoList = mutableListOf<Uri>()
    private var currentPosition: Int = 0
    private var timer: Timer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoframeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        getPhotoUriFromIntent()
        //startTimer()
    }

    private fun getPhotoUriFromIntent() {
        val size = intent.getIntExtra("photoListSize", 0)
        for(i in 0..size) {
            intent.getStringExtra("photo$i")?.let{
                photoList.add(Uri.parse(it))
            }
        }
    }

    private fun startTimer() {
        timer = timer(period = 5 * 1000) {
            runOnUiThread {
                val current = currentPosition
                val next = if( photoList.size <= currentPosition + 1 ) 0 else currentPosition + 1

                binding.backgroundPhotoImageView.setImageURI(photoList[current])
                binding.photoImageView.alpha = 0f
                binding.photoImageView.setImageURI(photoList[next])
                binding.photoImageView.animate()
                    .alpha(1.0f)
                    .setDuration(1000)
                    .start()

                currentPosition = next
            }
        }
    }

    override fun onStop() {
        super.onStop()
        timer?.cancel()
    }

    override fun onStart() {
        super.onStart()
        startTimer()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}