package com.dicoding.sibipediav2.ui.kamus

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.sibipediav2.databinding.ActivityAlphabetBinding
import com.dicoding.sibipediav2.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class AlphabetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlphabetBinding
    private lateinit var alphabetAdapter: AlphabetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlphabetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        fetchAlphabetData()

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        binding.alphabetRecyclerView.layoutManager = GridLayoutManager(this, 2)
        alphabetAdapter = AlphabetAdapter(emptyList()) { alphabetItem ->
            val intent = Intent(this, GestureDetailActivity::class.java).apply {
                putExtra("gestureImage", alphabetItem.image)
                putExtra("gestureTitle", alphabetItem.name)
            }
            startActivity(intent)
        }
        binding.alphabetRecyclerView.adapter = alphabetAdapter
    }

    private fun fetchAlphabetData() {
        binding.progressBar.visibility = android.view.View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            val apiService = ApiConfig.getApiService()
            try {
                val response = apiService.getAlphabet()
                if (response.isSuccessful) {
                    val alphabetList = response.body()?.signedUrl?.data ?: emptyList()
                    withContext(Dispatchers.Main) {
                        alphabetAdapter.setData(alphabetList)
                        binding.progressBar.visibility = android.view.View.GONE
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        binding.progressBar.visibility = android.view.View.GONE
                    }
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = android.view.View.GONE
                }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = android.view.View.GONE
                }
            }
        }
    }
}
