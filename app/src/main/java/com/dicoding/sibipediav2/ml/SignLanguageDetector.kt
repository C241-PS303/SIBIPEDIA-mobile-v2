package com.dicoding.sibipediav2.ml


import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor

class SignLanguageDetector(private val context: Context) {

    companion object {
        private const val TAG = "SignLanguageDetector"
        private val CLASS_LABELS = arrayOf(
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
        )
    }

    fun detectSignLanguage(bitmap: Bitmap): String {
        val model = Sibipedia.newInstance(context)

        val tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(bitmap)

        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(150, 150, ResizeOp.ResizeMethod.BILINEAR))
            .add(NormalizeOp(0.0f, 1.0f))
            .build()

        val processedImage = imageProcessor.process(tensorImage)

        val byteBuffer = processedImage.buffer

        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 150, 150, 3), DataType.FLOAT32)
        inputFeature0.loadBuffer(byteBuffer)

        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        model.close()

        val outputShape = outputFeature0.shape
        val outputDataType = outputFeature0.dataType
        val outputData = outputFeature0.floatArray

        Log.d(TAG, "Output Shape: ${outputShape.contentToString()}")
        Log.d(TAG, "Output Data Type: $outputDataType")
        Log.d(TAG, "Output Data: ${outputData.contentToString()}")

        return processOutput(outputFeature0)
    }

    private fun processOutput(outputFeature: TensorBuffer): String {
        val probabilities = outputFeature.floatArray
        val maxIndex = probabilities.indices.maxByOrNull { probabilities[it] } ?: -1
        val confidence = probabilities[maxIndex] * 100
        val predictedLabel = CLASS_LABELS[maxIndex]

        return "Detected sign: $predictedLabel with confidence: $confidence%"
    }
}
