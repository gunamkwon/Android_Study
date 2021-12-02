package com.example.bmi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bmi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonResult.setOnClickListener {

            val weight : Double = binding.edittextWeight.text.toString().toDouble()
            val height : Double = binding.edittextHeight.text.toString().toDouble()

            val result : Double = weight / ((height/100) * (height/100))

            val resultIntent = Intent(this, ResultActivity::class.java)
            resultIntent.putExtra("resultBMI",result)

            startActivity(resultIntent)
        }
    }
}