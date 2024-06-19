package com.dicoding.sibipediav2.ml

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
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

        val grayscaleBitmap = convertToGrayscale(bitmap)
        val resizedBitmap = Bitmap.createScaledBitmap(grayscaleBitmap, 150, 150, true)

        Log.d(TAG, "Processed Bitmap: ${resizedBitmap.width}x${resizedBitmap.height}")

        val tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(resizedBitmap)

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

        val outputData = outputFeature0.floatArray

        Log.d(TAG, "Output Data: ${outputData.contentToString()}")

        return processOutput(outputFeature0)
    }

    private fun convertToGrayscale(bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val grayscaleBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        for (y in 0 until height) {
            for (x in 0 until width) {
                val pixel = bitmap.getPixel(x, y)
                val gray = (Color.red(pixel) * 0.299 + Color.green(pixel) * 0.587 + Color.blue(pixel) * 0.114).toInt()
                val newPixel = Color.rgb(gray, gray, gray)
                grayscaleBitmap.setPixel(x, y, newPixel)
            }
        }
        return grayscaleBitmap
    }

    private fun processOutput(outputFeature: TensorBuffer): String {
        val probabilities = outputFeature.floatArray
        val maxIndex = probabilities.indices.maxByOrNull { probabilities[it] } ?: -1
        val confidence = probabilities[maxIndex] * 100
        val predictedLabel = CLASS_LABELS[maxIndex]

        Log.d(TAG, "Predicted Label: $predictedLabel, Confidence: $confidence")

        return "Detected sign: $predictedLabel with confidence: $confidence%"
    }
}
