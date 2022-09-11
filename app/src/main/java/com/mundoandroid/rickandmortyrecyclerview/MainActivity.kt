package com.mundoandroid.rickandmortyrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.mundoandroid.rickandmortyrecyclerview.databinding.ActivityMainBinding
import com.mundoandroid.rickandmortyrecyclerview.model.Result
import com.mundoandroid.rickandmortyrecyclerview.model.RickMortyResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RickMortyAdapter
    private val rickMortyImages = mutableListOf<Result>()
    val tag = "getAllCharacters"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvRickMorty
        binding.svRickMorty.setOnQueryTextListener(this)
        getAllCharacters()
        initRecyclerView()
    }

    private fun getAllCharacters() {
        CoroutineScope(Dispatchers.IO).launch {
            val callAll: Response<RickMortyResponse> = getRetrofit().create(ApiService::class.java).getCharacterByName("character")
            val allCharacters: RickMortyResponse? = callAll.body()
            runOnUiThread{
                if(callAll.isSuccessful) {
                    val images: List<Result> = allCharacters?.images ?: emptyList()
                    rickMortyImages.clear()
                    rickMortyImages.addAll(images)
                    adapter.notifyDataSetChanged()
                }else{
                    showError()
                }
                hideKeyBoard()
            }
        }
    }

    private fun initRecyclerView() {
        adapter = RickMortyAdapter(rickMortyImages)
        binding.rvRickMorty.layoutManager = LinearLayoutManager(this)
        binding.rvRickMorty.adapter = adapter
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchByName(query:String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<RickMortyResponse> = getRetrofit().create(ApiService::class.java).getCharacterByName("character?name=$query")
            val characters: RickMortyResponse? = call.body()
            runOnUiThread{
                if(call.isSuccessful) {
                    val images: List<Result> = characters?.images ?: emptyList()
                    rickMortyImages.clear()
                    rickMortyImages.addAll(images)
                    adapter.notifyDataSetChanged()
                }else{
                    showError()
                }
                hideKeyBoard()
            }
        }
    }

    private fun hideKeyBoard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }

    private fun showError() {
        toast("Ha ocurrido un error")
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            searchByName(query.toLowerCase())
        }else{
            getAllCharacters()
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}