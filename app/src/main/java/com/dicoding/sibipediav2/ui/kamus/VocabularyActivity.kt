package com.dicoding.sibipediav2.ui.kamus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.sibipediav2.databinding.ActivityVocabularyBinding
import com.dicoding.sibipediav2.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class VocabularyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVocabularyBinding
    private lateinit var vocabularyAdapter: VocabularyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVocabularyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        fetchVocabularyData()

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        binding.vocabularyRecyclerView.layoutManager = GridLayoutManager(this, 2)
        vocabularyAdapter = VocabularyAdapter(emptyList())
        binding.vocabularyRecyclerView.adapter = vocabularyAdapter
    }

    private fun fetchVocabularyData() {
        binding.progressBar.visibility = android.view.View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            val apiService = ApiConfig.getApiService()
            try {
                val response = apiService.getVocabulary()
                if (response.isSuccessful) {
                    val vocabularyList = response.body()?.data ?: emptyList()
                    withContext(Dispatchers.Main) {
                        vocabularyAdapter.setData(vocabularyList)
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
