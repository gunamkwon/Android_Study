package com.example.lotto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.marginRight
import com.example.lotto.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val lottoNum = mutableSetOf<Int>()
        initNumberPicker()


        // 버튼 이벤트 처리
        binding.btnAddNumber.setOnClickListener{
            if(lottoNum.size < 6) {
                if(lottoNum.add(binding.numberPicker.value)){
                    // UI 처리
                    makeBalls(lottoNum.last())
                }




            }

        }

        binding.btnReset.setOnClickListener{
            lottoNum.removeAll(lottoNum)

            // UI 처리
            binding.linearlayoutBalls.removeAllViews()
        }

        binding.btnRandom.setOnClickListener {
            while(lottoNum.size < 6){
                if(lottoNum.add((1..45).random())) {
                    // UI 처리
                    makeBalls(lottoNum.last())
                }


            }

        }
    }

    private fun initNumberPicker() {
        binding.numberPicker.maxValue = 45;
        binding.numberPicker.minValue = 1;
    }

    private fun makeBalls(num: Int) {

        val textview = TextView(applicationContext)

        textview.text = num.toString()
        textview.textSize = 24F;
        textview.gravity = Gravity.CENTER

        val linearLayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        linearLayoutParams.rightMargin = 24
        textview.layoutParams = linearLayoutParams

        //textview.background = resources.getDrawable(R.drawable.lotto_balls, null)
        textview.background = ContextCompat.getDrawable(this, R.drawable.lotto_balls)

        binding.linearlayoutBalls.addView(textview)


    }
}