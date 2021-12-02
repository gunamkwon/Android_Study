package com.example.bmi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bmi.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    lateinit var binding: ActivityResultBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bmi = intent.getDoubleExtra("resultBMI",0.0)

        binding.textBmiNumber.text = binding.textBmiNumber.text.toString() + bmi

        when(bmi) {
            in 0.0..18.5 -> binding.textBmiResult.text = binding.textBmiResult.text.toString() + "저체중"
            in 18.5..23.0 -> binding.textBmiResult.text = binding.textBmiResult.text.toString() + "정상 체중"
            in 23.0..25.0 -> binding.textBmiResult.text = binding.textBmiResult.text.toString() + "과체중"
            in 25.0..30.0 -> binding.textBmiResult.text = binding.textBmiResult.text.toString() + "경도 비만"
            in 30.0..35.0 -> binding.textBmiResult.text = binding.textBmiResult.text.toString() + "중정도 비만"
            else -> binding.textBmiResult.text = binding.textBmiResult.text.toString() + "고도 비만"
        }
    }
}