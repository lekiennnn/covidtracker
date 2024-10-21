package com.example.hugeproj.controller

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.hugeproj.R
import com.example.hugeproj.ViewPagerAdapter
import com.example.hugeproj.model.CountryCovidDataApiResponse
import com.example.hugeproj.model.CovidDataAPI
import com.example.hugeproj.model.GlobalCovidDataApiResponse
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import org.w3c.dom.Text
import java.util.Calendar

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize ViewPager2
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)

        // Set the adapter to ViewPager
        val viewPagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter = viewPagerAdapter

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId){
                R.id.navigation_covid_data -> viewPager.currentItem = 0
                R.id.navigation_covid_news -> viewPager.currentItem = 1
            }
            true
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                when (position){
                    0 -> bottomNav.selectedItemId = R.id.navigation_covid_data
                    1 -> bottomNav.selectedItemId = R.id.navigation_covid_news
                }
            }
        })
    }

}

