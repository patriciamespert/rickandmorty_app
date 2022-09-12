package com.mundoandroid.rickandmortyrecyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mundoandroid.rickandmortyrecyclerview.databinding.ItemRickMortyBinding
import com.mundoandroid.rickandmortyrecyclerview.model.Result

class RickMortyViewHolder(view: View):RecyclerView.ViewHolder(view) {
    private val binding = ItemRickMortyBinding.bind(view)
    fun bind(result:Result){
        Glide
            .with(binding.root.context)
            .load(result.image)
            .into(binding.ivRickMorty)
        binding.tvRickMorty.text = result.name
    }
}