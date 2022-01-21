package com.example.recorder

import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.security.Permissions
import java.util.jar.Manifest

// Media Recorder
//  https://developer.android.com/reference/android/media/MediaRecorder?hl=ko

// Supported Media formats
// https://developer.android.com/guide/topics/media/media-formats

// Media Player
// https://developer.android.com/reference/android/media/MediaPlayer

// Custom Drawing
// https://developer.android.com/training/custom-views/custom-drawing?hl=ko

class MainActivity : AppCompatActivity() {

    private val soundVisualizerView: SoundVisualizeVIew by lazy {
        findViewById(R.id.soundVisualizeView)
    }

    private val recordTimeTextView: CountUpView by lazy {
        findViewById(R.id.recordTimeTextView)
    }

    private val recordButton: RecordButton by lazy {
        findViewById(R.id.btn_record)
    }
    private val resetButton: Button by lazy{
        findViewById(R.id.btn_reset)
    }

    private val PERMISSION_REQUEST = 100


    //
    private val recordingFilePath: String by lazy {
        "${externalCacheDir?.absolutePath}/recording.3gp"
    }
    // 사용하지 않을 때는 release()시키고 null 두어야 메모리관리에 좋다
    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null


    private var state = State.BEFORE_RECORDING
        set(value) {
            field = value
            resetButton.isEnabled = (value == State.AFTER_RECORDING) ||
                    (value == State.ON_PLAYING)
            recordButton.updateIconWithState(value)
        }


    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        initButtonEvent()
        initVariables()
    }

    private fun initVariables() {
        state = State.BEFORE_RECORDING
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun initButtonEvent() {
        soundVisualizerView.onRequestCurrentAmplitude = {
            recorder?.maxAmplitude ?: 0
        }
        recordButton.setOnClickListener {
            onCheckPermission()
            when(state) {
                State.BEFORE_RECORDING -> {
                    startRecording()
                }
                State.ON_RECORDING -> {
                    stopRecording()
                }
                State.AFTER_RECORDING -> {
                    startPlaying()
                }
                State.ON_PLAYING -> {
                    stopPlaying()
                }
            }
        }

        resetButton.setOnClickListener {
            stopPlaying()
            soundVisualizerView.clearVisualization()
            recordTimeTextView.clearCountTime()
            state = State.BEFORE_RECORDING
        }
    }


    fun onCheckPermission() {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) == PERMISSION_GRANTED) {
            // 권한이 있으니 실행
        } else {
            requestRecordingPermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode) {
            PERMISSION_REQUEST -> {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 권한이 있으니 실행
                } else {
                    finish()
                }
            }
        }
    }

    fun requestRecordingPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.RECORD_AUDIO)) {
            Toast.makeText(this, "마이크 접근 권한이 요구됩니다",Toast.LENGTH_SHORT).show()
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.RECORD_AUDIO), PERMISSION_REQUEST)
        } else {
            Toast.makeText(this, "카메라 허가를 받을 수 없습니다", Toast.LENGTH_SHORT).show()
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.RECORD_AUDIO), PERMISSION_REQUEST)
        }
    }

    fun initViews() {
        recordButton.updateIconWithState(state)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun startRecording() {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            // 따로 저장하지 않기 때문에 캐쉬에 저장할 것
            // 내부에 저장 or 외부에 저장 => 녹음의 경우, 내부는 용량이 작을 수 있다.
            // 그래서 이 프로젝트의 경우, 외부 캐쉬에 저장해야함
            setOutputFile(recordingFilePath)
            prepare()
        }
        recorder?.start()
        soundVisualizerView.startVisualizing(false)
        recordTimeTextView.startCountUp()
        state = State.ON_RECORDING
    }

    private fun stopRecording() {
        recorder?.run {
            stop()
            release()
        }
        recorder = null
        soundVisualizerView.stopVisualizing()
        recordTimeTextView.stopCountUp()
        state = State.AFTER_RECORDING
    }

    private fun startPlaying() {
        player = MediaPlayer().apply {
            setDataSource(recordingFilePath)
            prepare()
            // Streaming의 경우 async를 사용하기도 한다.
        }
        player?.setOnCompletionListener {
            stopPlaying()
            state = State.AFTER_RECORDING
        }
        player?.start()
        soundVisualizerView.startVisualizing(true)
        recordTimeTextView.startCountUp()
        state = State.ON_PLAYING
    }

    private fun stopPlaying() {
        player?.release()
        player = null
        soundVisualizerView.stopVisualizing()
        recordTimeTextView.stopCountUp()
        state = State.AFTER_RECORDING
    }
}