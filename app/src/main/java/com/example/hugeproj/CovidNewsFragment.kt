package com.example.hugeproj

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hugeproj.model.Article
import com.example.hugeproj.model.CovidNewsAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class CovidNewsFragment : Fragment(){

    private lateinit var covidNewsAdapter: CovidNewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.covid_news, container, false)
        setupUI(view)

        getCovidNewsData()

        return view
    }

    fun setupUI(view : View){
        val rv_covidNews = view.findViewById<RecyclerView>(R.id.rv_covidNews)
        rv_covidNews.layoutManager = LinearLayoutManager(requireContext())
        covidNewsAdapter = CovidNewsAdapter(emptyList()){
            url -> openurlInBrowser(url)
        }
        rv_covidNews.adapter = covidNewsAdapter

    }

    fun getCovidNewsData(){
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://covid-19-news.p.rapidapi.com/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://covidnewsapi.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(CovidNewsAPI::class.java)

        lifecycleScope.launch(Dispatchers.Main) {
            val response = api.getCovidNews()
            if(response.isSuccessful){
                val newsList = response.body()?.articles ?: emptyList()
                Log.d("API Response", "Received ${newsList.size} articles")
                covidNewsAdapter.updateNews(newsList)
            } else {
                Log.e("API Response", "Error: ${response.code()} - ${response.message()}")
                Toast.makeText(requireContext(), "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun openurlInBrowser(url: String){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}
