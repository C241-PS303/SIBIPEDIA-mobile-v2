package com.dicoding.sibipediav2.ui.kamus

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.sibipediav2.databinding.ActivityGestureDetailBinding
import com.dicoding.sibipediav2.ui.camera.CameraActivity

class GestureDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGestureDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGestureDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        val gestureImage = intent.getStringExtra("gestureImage")
        val gestureTitle = intent.getStringExtra("gestureTitle") ?: ""

        Glide.with(this)
            .load(gestureImage)
            .into(binding.gestureImageView)
        binding.gestureTitle.text = gestureTitle

        binding.practiceButton.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            intent.putExtra("EXPECTED_GESTURE", gestureTitle)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
