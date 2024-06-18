package com.dicoding.sibipediav2.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.sibipediav2.data.remote.response.QuizRecord
import com.dicoding.sibipediav2.databinding.ItemQuizScoreBinding

class QuizScoreAdapter : RecyclerView.Adapter<QuizScoreAdapter.QuizScoreViewHolder>() {

    private val quizRecords = mutableListOf<QuizRecord>()

    fun setQuizRecords(records: List<QuizRecord>) {
        quizRecords.clear()
        quizRecords.addAll(records)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizScoreViewHolder {
        val binding = ItemQuizScoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuizScoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuizScoreViewHolder, position: Int) {
        holder.bind(quizRecords[position])
    }

    override fun getItemCount(): Int = quizRecords.size

    class QuizScoreViewHolder(private val binding: ItemQuizScoreBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(record: QuizRecord) {
            binding.tvScore.text = "Skor : ${record.score}%"
            binding.tvCreatedAt.text = "Tanggal : " + java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault()).format(java.util.Date(record.createdAt))
        }
    }
}
