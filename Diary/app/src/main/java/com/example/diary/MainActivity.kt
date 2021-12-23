package com.example.diary

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.diary.R.string.dialog_message
import com.example.diary.R.string.dialog_title
import com.example.diary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var changePasswordMode: Boolean = false
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setNumberPicker()
        setButton()

    }

    private fun setNumberPicker() {
        binding.numberPicker1.apply{
            minValue = 0
            maxValue = 9
        }

        binding.numberPicker2.minValue = 0
        binding.numberPicker2.maxValue = 9

        binding.numberPicker3.minValue = 0
        binding.numberPicker3.maxValue = 9
    }

    private fun setButton() {
        binding.openButton.setOnClickListener {

            if(changePasswordMode) {
                Toast.makeText(this,"비밀번호 변경중입니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val passwordPreferences = getSharedPreferences(getString(R.string.save_password), Context.MODE_PRIVATE)
            val myPassword = "${binding.numberPicker1.value}${binding.numberPicker2.value}${binding.numberPicker3.value}"

            if(passwordPreferences.getString(getString(R.string.save_password),"000").equals(myPassword)) {
                //TODO : 맞는 경우
                val intent: Intent = Intent(this, MemoActivity::class.java)
                startActivity(intent)
            } else{
                /*val builder: AlertDialog.Builder? = this.let {
                    AlertDialog.Builder(it)

                }

                builder?.setPositiveButton("OK", {dialog, id -> })
                builder?.setMessage(dialog_message)
                    ?.setTitle(dialog_title)
                val dialog = builder?.create()
                dialog?.show()*/

                AlertDialog.Builder(this)
                    .setTitle(R.string.dialog_title)
                    .setMessage(R.string.dialog_message).setPositiveButton("확인") {_,_ ->}
                    .create()
                    .show()
            }
        }

        binding.changePasswordButton.setOnClickListener {

            if(changePasswordMode) {
                changePasswordMode = false
                Toast.makeText(this,"비밀번호 변경이 완료되었습니다.",Toast.LENGTH_SHORT).show()
                val newPassword = "${binding.numberPicker1.value}${binding.numberPicker2.value}${binding.numberPicker3.value}"
                val passwordPreferences = this.getSharedPreferences(getString(R.string.save_password),Context.MODE_PRIVATE)
                with(passwordPreferences.edit()) {
                    putString(getString(R.string.save_password), newPassword)
                    commit()
                }
                binding.changePasswordButton.setBackgroundColor(Color.BLACK)
                Log.d(TAG, "Password Change: " + newPassword)
            } else{
                val passwordPreferences = getSharedPreferences(getString(R.string.save_password), Context.MODE_PRIVATE)
                val myPassword = "${binding.numberPicker1.value}${binding.numberPicker2.value}${binding.numberPicker3.value}"

                if(passwordPreferences.getString(getString(R.string.save_password),"000").equals(myPassword)) {
                    changePasswordMode = true
                    Toast.makeText(this, "변경할 패스워드를 입력해주세요", Toast.LENGTH_SHORT).show()
                    binding.changePasswordButton.setBackgroundColor(Color.RED)
                } else{
                    AlertDialog.Builder(this)
                        .setTitle(R.string.dialog_title)
                        .setMessage(R.string.dialog_message).setPositiveButton("확인") {_,_ ->}
                        .create()
                        .show()
                }

            }
        }
    }

    companion object {
        const val TAG = "MAIN ACTIVITY"
    }
}