package com.example.android.file_inout

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.google.android.material.snackbar.Snackbar
import java.io.File

class MainActivity : AppCompatActivity() {

    private val TAG : String = "File I/O Activity"

    private lateinit var file : File
    private lateinit var preferenceButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferenceButton = findViewById(R.id.preference_button)

        preferenceButton.setOnClickListener {
            val intent = Intent(this, Main2Activity::class.java)
            startActivity(intent)
        }
    }


    private fun easyReadTextFile(fullPath: String): String {
        var contents = ""
        //context.openFileInput("").bufferedREader().useLines {lines ->
        //    content = lines.joinToString("\n")
        //}
        return contents
    }

    private fun easyWriteTextFile(filename : String, contents: String) {
        //context.openFileOutput(,Context.MODE_PRIVATE).use { stream ->
        //    stream.write(contents.toByteArray())
        //}
    }
}

