package com.mundoandroid.rickandmortyrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.mundoandroid.rickandmortyrecyclerview.databinding.ActivityDetailBinding
import com.mundoandroid.rickandmortyrecyclerview.model.Result

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    companion object {
        const val CHARACTER_NAME = "DetailActivity:name"
        const val CHARACTER_IMAGE = "DetailActivity:image"
        const val CHARACTER_STATUS = "DetailActivity:status"
        const val CHARACTER_GENDER = "DetailActivity:gender"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Glide
            .with(binding.root.context)
            .load(intent.getStringExtra(CHARACTER_IMAGE))
            .into(binding.image)

        binding.name.text = intent.getStringExtra(CHARACTER_NAME)
        binding.status.text = intent.getStringExtra(CHARACTER_STATUS)
        binding.gender.text = intent.getStringExtra(CHARACTER_GENDER)
    }
}