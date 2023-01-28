package ru.cft.shift2023winter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.cft.shift2023winter.Adapter.CharacterAdapter
import ru.cft.shift2023winter.Model.SearchResult
import ru.cft.shift2023winter.RetrofitClient.Common
import ru.cft.shift2023winter.RetrofitClient.RetrofitService
import ru.cft.shift2023winter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var mService: RetrofitService
    lateinit var recycleView: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        recycleView = binding.characterList

        mService = Common.retrofitService
        recycleView.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recycleView.layoutManager = layoutManager
        getAllMovieList()
    }

    private fun getAllMovieList() {
        mService.getCharacterList().enqueue(object : Callback<SearchResult> {
            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
            }

            override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                val mutableList = response.body() as SearchResult
                println(mutableList)
                adapter = CharacterAdapter(baseContext, mutableList.results)
                adapter.notifyDataSetChanged()
                recycleView.adapter = adapter
            }
        })
    }
}