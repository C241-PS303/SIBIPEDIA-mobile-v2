package com.dicoding.sibipediav2.ui.kamus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.sibipediav2.R
import com.dicoding.sibipediav2.data.remote.response.VocabularyItem

class VocabularyAdapter(private var vocabularyList: List<VocabularyItem>) :
    RecyclerView.Adapter<VocabularyAdapter.VocabularyViewHolder>() {

    class VocabularyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.vocabularyImage)
        val textView: TextView = itemView.findViewById(R.id.vocabularyWord)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VocabularyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_vocabulary, parent, false)
        return VocabularyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: VocabularyViewHolder, position: Int) {
        val currentItem = vocabularyList[position]
        holder.textView.text = currentItem.word
        Glide.with(holder.itemView.context)
            .load(currentItem.image)
            .into(holder.imageView)
    }

    override fun getItemCount() = vocabularyList.size

    fun setData(newVocabularyList: List<VocabularyItem>) {
        vocabularyList = newVocabularyList
        notifyDataSetChanged()
    }
}
