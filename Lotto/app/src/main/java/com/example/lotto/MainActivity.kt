package com.example.lotto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.view.marginRight
import com.example.lotto.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    private val lottoNum = mutableSetOf<Int>()
    private val randomNum = mutableSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNumberPicker()
        initButtonEvent()

    }


    private fun initButtonEvent() {
        // 버튼 이벤트 처리
        binding.btnAddNumber.setOnClickListener{
            if(lottoNum.size < 6) {
                if(lottoNum.add(binding.numberPicker.value)){
                    // UI 처리
                    makeBalls(lottoNum.last())
                }
            } else {
                Toast.makeText(this, "번호를 더 이상 추가할 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnRandom.setOnClickListener {
            lottoNum.removeAll(randomNum)

            if(randomNum.size !=0){
                for(i in 0 until randomNum.size){
                    val idx = lottoNum.size
                    binding.linearlayoutBalls.removeView(binding.linearlayoutBalls[idx])
                }
            }

            randomNum.clear()


            while(randomNum.size < 6 - lottoNum.size) {
                val num = (1..45).random()
                if(!lottoNum.contains(num)) {
                    randomNum.add(num)
                }
            }

            for(num in randomNum){
                lottoNum.add(num)
                makeBalls(num)
            }
        }

        binding.btnReset.setOnClickListener{
            lottoNum.removeAll(lottoNum)

            // UI 처리
            binding.linearlayoutBalls.removeAllViews()
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

        textview.background = ContextCompat.getDrawable(this, R.drawable.lotto_balls)

        binding.linearlayoutBalls.addView(textview)


    }
}