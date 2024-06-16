package com.dicoding.sibipediav2.ui.kamus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.IOException
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.sibipediav2.databinding.ActivityAlphabetBinding
import com.dicoding.sibipediav2.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class AlphabetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlphabetBinding
    private lateinit var alphabetAdapter: AlphabetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlphabetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.alphabetRecyclerView.layoutManager = GridLayoutManager(this, 2)
        alphabetAdapter = AlphabetAdapter(emptyList())
        binding.alphabetRecyclerView.adapter = alphabetAdapter

        fetchAlphabetData()

        binding.backButton.setOnClickListener {
            finish()
        }
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
                    // Handle the error
                    withContext(Dispatchers.Main) {
                        binding.progressBar.visibility = android.view.View.GONE
                    }
                }
            } catch (e: IOException) {
                // Handle the IOException
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = android.view.View.GONE
                }
            } catch (e: HttpException) {
                // Handle the HttpException
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = android.view.View.GONE
                }
            }
        }
    }
}
