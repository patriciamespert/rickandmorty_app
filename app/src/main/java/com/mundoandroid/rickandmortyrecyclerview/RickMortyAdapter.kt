package com.mundoandroid.rickandmortyrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mundoandroid.rickandmortyrecyclerview.model.Result

class RickMortyAdapter(
    private val images:List<Result>,
    private val onRicKMortyClickedListener: (Result) -> Unit
) :
    RecyclerView.Adapter<RickMortyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RickMortyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RickMortyViewHolder(layoutInflater.inflate(R.layout.item_rick_morty,parent,false))
    }

    override fun onBindViewHolder(holder: RickMortyViewHolder, position: Int) {
        val item: Result = images[position]
        holder.bind(item)
        holder.itemView.setOnClickListener{ onRicKMortyClickedListener(item) }
    }

    override fun getItemCount(): Int = images.size
}