package com.example.photoframe

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ImageView
import android.widget.Toast
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

    private val imageViewList: List<ImageView> by lazy {
        mutableListOf<ImageView>().apply {
            add(binding.firstLeftImage)
            add(binding.firstCenterImage)
            add(binding.firstRightImage)
            add(binding.secondLeftImage)
            add(binding.secondCenterImage)
            add(binding.secondRightImage)
        }
    }

    private val imageUriList: MutableList<Uri> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAddPhotoButton()
        initStartPhotoFrameModeButton()
    }

    private fun initAddPhotoButton() {
        binding.addButton.setOnClickListener {
            permissionGranted()
        }
    }

    private fun permissionGranted() {
        when {
            ContextCompat.checkSelfPermission(this, permissions
            ) == PackageManager.PERMISSION_GRANTED -> {
                navigatePhotos()
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

    private fun initStartPhotoFrameModeButton() {
        binding.runButton.setOnClickListener {
            val intent = Intent(this, PhotoFrameActivity::class.java)
            imageUriList.forEachIndexed { index, uri ->
                intent.putExtra("photo$index", uri.toString())
            }
            intent.putExtra("photoListSize", imageUriList.size)
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1000 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    navigatePhotos()
                } else {
                    Toast.makeText(this, "권한을 거부하셨습니다.", Toast.LENGTH_SHORT).show();
                }

            }
            else -> {

            }
        }
    }

    private fun navigatePhotos() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent,2000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode != Activity.RESULT_OK) {
            return
        }

        when(requestCode) {
            2000 -> {
                val selectedImgUri: Uri? = data?.data
                if(selectedImgUri != null) {
                    imageUriList.add(selectedImgUri)
                    imageViewList[imageUriList.size -1].setImageURI(selectedImgUri)
                } else {
                    Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show();
                }
            } else -> {
                Toast.makeText(this, "사진을 가져오지 못했습니다.",Toast.LENGTH_SHORT).show();
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