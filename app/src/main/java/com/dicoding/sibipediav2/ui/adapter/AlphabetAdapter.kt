package com.dicoding.sibipediav2.ui.kamus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.sibipediav2.R
import com.dicoding.sibipediav2.data.remote.response.AlphabetItem

class AlphabetAdapter(
    private var alphabetList: List<AlphabetItem>,
    private val onItemClick: (AlphabetItem) -> Unit
) : RecyclerView.Adapter<AlphabetAdapter.AlphabetViewHolder>() {

    class AlphabetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.alphabetImage)
        val textView: TextView = itemView.findViewById(R.id.alphabetLetter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlphabetViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_alphabet, parent, false)
        return AlphabetViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AlphabetViewHolder, position: Int) {
        val currentItem = alphabetList[position]
        holder.textView.text = currentItem.name
        Glide.with(holder.itemView.context)
            .load(currentItem.image)
            .into(holder.imageView)

        holder.itemView.setOnClickListener {
            onItemClick(currentItem)
        }
    }

    override fun getItemCount() = alphabetList.size

    fun setData(newAlphabetList: List<AlphabetItem>) {
        alphabetList = newAlphabetList
        notifyDataSetChanged()
    }
}
