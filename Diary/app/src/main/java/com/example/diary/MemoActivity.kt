package com.example.diary

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener
import com.example.diary.databinding.ActivityMemoBinding

class MemoActivity : AppCompatActivity() {

    lateinit var binding: ActivityMemoBinding

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailPreferences = getSharedPreferences("diary", Context.MODE_PRIVATE)

        binding.diaryEditText.setText(detailPreferences.getString("diary",""))

        val runnable = Runnable {
            getSharedPreferences("diary", Context.MODE_PRIVATE).edit{
                putString("detail",binding.diaryEditText.text.toString())
            }
        }

        binding.diaryEditText.addTextChangedListener {

            // 이전의 Runnable 지우기
            handler.removeCallbacks(runnable)

            // 0.5초 이내에 Runnable 실행행
            handler.postDelayed(runnable, 500)
        }
    }


}