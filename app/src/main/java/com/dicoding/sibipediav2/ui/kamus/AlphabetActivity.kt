package com.dicoding.sibipediav2.ui.kamus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.sibipediav2.R
import com.dicoding.sibipediav2.data.local.AlphabetItem
import com.dicoding.sibipediav2.databinding.ActivityAlphabetBinding

class AlphabetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlphabetBinding
    private lateinit var alphabetAdapter: AlphabetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlphabetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val alphabetList = listOf(
            AlphabetItem("A", R.drawable.a),
            AlphabetItem("B", R.drawable.b),
            AlphabetItem("C", R.drawable.c),
            AlphabetItem("D", R.drawable.d),
            AlphabetItem("E", R.drawable.e),
            AlphabetItem("F", R.drawable.f),
            AlphabetItem("G", R.drawable.g),
            AlphabetItem("H", R.drawable.h),
            AlphabetItem("I", R.drawable.i),
            AlphabetItem("J", R.drawable.j),
            AlphabetItem("K", R.drawable.k),
            AlphabetItem("L", R.drawable.l),
            AlphabetItem("M", R.drawable.m),
            AlphabetItem("N", R.drawable.n),
            AlphabetItem("O", R.drawable.o),
            AlphabetItem("P", R.drawable.p),
            AlphabetItem("Q", R.drawable.q),
            AlphabetItem("R", R.drawable.r),
            AlphabetItem("S", R.drawable.s),
            AlphabetItem("T", R.drawable.t),
            AlphabetItem("U", R.drawable.u),
            AlphabetItem("V", R.drawable.v),
            AlphabetItem("W", R.drawable.w),
            AlphabetItem("X", R.drawable.x),
            AlphabetItem("Y", R.drawable.y),
            AlphabetItem("Z", R.drawable.z)
        )

        binding.alphabetRecyclerView.layoutManager = GridLayoutManager(this, 2)
        alphabetAdapter = AlphabetAdapter(alphabetList)
        binding.alphabetRecyclerView.adapter = alphabetAdapter
    }
}
