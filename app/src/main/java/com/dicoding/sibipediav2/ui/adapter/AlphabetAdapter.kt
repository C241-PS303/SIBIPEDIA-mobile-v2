package com.dicoding.sibipediav2.ui.kamus

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.sibipediav2.R
import com.dicoding.sibipediav2.data.local.AlphabetItem

class AlphabetAdapter(private val alphabetList: List<AlphabetItem>) :
    RecyclerView.Adapter<AlphabetAdapter.AlphabetViewHolder>() {

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
        holder.imageView.setImageResource(currentItem.imageAlphabet)
        holder.textView.text = currentItem.teks
    }

    override fun getItemCount() = alphabetList.size
}
