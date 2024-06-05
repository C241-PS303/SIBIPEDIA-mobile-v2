package com.dicoding.sibipediav2.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.sibipediav2.ui.kamus.GestureDetailActivity
import com.dicoding.sibipediav2.data.local.KamusItem
import com.dicoding.sibipediav2.databinding.ItemKamusBinding

class KamusAdapter(private val context: Context, private val listKata: ArrayList<KamusItem>) : RecyclerView.Adapter<KamusAdapter.ViewHolder>() {

    class ViewHolder(var binding: ItemKamusBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemKamusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listKata.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val description = listKata[position].description
        val profilePic = listKata[position].imageUrl

        holder.binding.alphabetText.text = description
        Glide.with(holder.itemView.context)
            .load(profilePic)
            .circleCrop()
            .into(holder.binding.alphabetImage)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, GestureDetailActivity::class.java)
            intent.putExtra("gestureImage", profilePic)
            intent.putExtra("gestureTitle", description)
            context.startActivity(intent)
        }
    }
}
