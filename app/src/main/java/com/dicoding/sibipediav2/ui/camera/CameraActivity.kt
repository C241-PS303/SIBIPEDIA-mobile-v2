package com.dicoding.sibipediav2.ui.camera

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dicoding.sibipediav2.databinding.ActivityCameraBinding
import com.dicoding.sibipediav2.ml.ImageClassifierHelper
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import org.tensorflow.lite.task.vision.classifier.Classifications

class CameraActivity : AppCompatActivity(), ImageClassifierHelper.ClassifierListener {

    companion object {
        private var TAG = CameraActivity::class.java.simpleName
        private const val REQUEST_CODE_PERMISSIONS = 20
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    private lateinit var binding: ActivityCameraBinding
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var imageClassifierHelper: ImageClassifierHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        cameraExecutor = Executors.newSingleThreadExecutor()
        imageClassifierHelper = ImageClassifierHelper(
            context = this,
            classifierListener = this
        )
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor) { imageProxy ->
                        processImageProxy(imageProxy)
                    }
                }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageAnalysis
                )
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun processImageProxy(imageProxy: ImageProxy) {
        imageProxy.use {
            try {
                imageClassifierHelper.classifyImage(imageProxy)
            } catch (e: Exception) {
                Log.e(TAG, "Error processing image: ", e)
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
//                Toast.makeText(this, resources.getString(R.string.permission), Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onError(error: String) {
        runOnUiThread {
            Toast.makeText(this@CameraActivity, error, Toast.LENGTH_SHORT).show()
        }
        Log.e(TAG, error)
    }

    override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
        val resultText = StringBuilder()
        results?.forEach { classification ->
            classification.categories.forEach { category ->
                resultText.append("Label: ${category.label}\nPrediction: ${"%.2f".format(category.score * 100)}%\n")
            }
        }
        runOnUiThread {
            binding.tvResult.text = resultText.toString()
        }
        Log.d(TAG, "Inference time: $inferenceTime ms")
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
//        imageClassifierHelper.close()
    }
}
