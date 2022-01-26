package com.example.myalarm

import android.app.TimePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // step 0 뷰를 초기화
        initOnOffButton()
        initChangeAlarmTimeButton()

        // step1 데이터 가져오기

        // step2 뷰에 데이터 그려주기
    }

    private fun initOnOffButton() {
        val onOffButton = findViewById<Button>(R.id.alarm_off_button)
        onOffButton.setOnClickListener {
            // 데이터 확인

            // On -> 알람 등록
            // Off -> 알람 제거

            // 데이터 저장
        }
    }

    private fun initChangeAlarmTimeButton() {
        val changeAlarmButton = findViewById<Button>(R.id.alarm_init_button)
        changeAlarmButton.setOnClickListener {
            // 현재 시간을 일단 가져온다.
            // TimePickDialog 로 시간 설정
            val calendar = Calendar.getInstance()

            TimePickerDialog(this, { picker, hour, minute ->
                // 해당 부분은 시간 선택후 발생한다.


                // 설정한 시간 데이터 저장
                val model = saveAlarmModel(hour,minute,false)
                // 뷰를 업데이트
                // 기존 알람 삭제


            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false ).show()

        }
    }

    private fun saveAlarmModel(
        hour: Int,
        minute: Int,
        onOff: Boolean
    ): AlarmDisplayModel {
        val model = AlarmDisplayModel(
            hour = hour,
            minute = minute,
            onOff = false
        )

        // SharedPreferences 에 저장
        val sharedPreferences = getSharedPreferences("time", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("alarm", model.makeDataForDB())
            putBoolean("onOff", model.onOff)
            commit()
        }

        return model
    }

}