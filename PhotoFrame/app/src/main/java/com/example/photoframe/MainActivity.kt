package com.example.photoframe

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.photoframe.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    private val permissions: String = android.Manifest.permission.READ_EXTERNAL_STORAGE
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == PERMISSION_REQUEST_GALLERY) {

        }

    }


    private fun permissionGranted() {
        when {
            ContextCompat.checkSelfPermission(this, permissions
            ) == PackageManager.PERMISSION_GRANTED -> {
                // TODO 성공
            }
            ActivityCompat.shouldShowRequestPermissionRationale(this, permissions) -> {
                // 실패
                showPermissionContextPopup()
            }
            else -> {
                requestPermissions(arrayOf(permissions), 1000)
            }
        }
    }

    private fun showPermissionContextPopup() {
        AlertDialog.Builder(this)
            .setTitle("권한이 필요합니다.")
            .setMessage("전자액자를 불러오기 위해 권한이 필요합니다.")
            .setPositiveButton("동의하기") { dialog, which ->
                requestPermissions(arrayOf(permissions), 1000)
            }
            .setNegativeButton("취소하기") { _, _ -> }
            .create()
            .show()
    }

    companion object {
        const val PERMISSION_REQUEST_GALLERY = 0
    }
}