package com.mundoandroid.rickandmortyrecyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        getAllCharacters()
        binding.svRickMorty.setOnQueryTextListener(this)

    }

    private fun navigateTo(character: Result){
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.CHARACTER_NAME, character.name)
        intent.putExtra(DetailActivity.CHARACTER_IMAGE, character.image)
        intent.putExtra(DetailActivity.CHARACTER_STATUS, character.status)
        startActivity(intent)
    }

    private fun getAllCharacters(){
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
        adapter = RickMortyAdapter(rickMortyImages){ navigateTo(it) }
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
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}