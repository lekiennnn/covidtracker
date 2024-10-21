package com.example.hugeproj

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hugeproj.controller.CountryCovidDataAdapter
import com.example.hugeproj.controller.CountryCovidDataDeserializer
import com.example.hugeproj.model.CountryCovidData
import com.example.hugeproj.model.CountryCovidDataApiResponse
import com.example.hugeproj.model.CovidDataAPI
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar
import java.util.concurrent.TimeUnit

class CountryCovidDataFragment : Fragment() {

//    private lateinit var countryCovidDataAdapter: CountryCovidDataAdapter
//    private lateinit var covidDataViewModel: CovidDataViewModel
//
//    private lateinit var selectedDate: String
//
////    private val countryIsoMap = mutableMapOf<String, String>()
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.country_covid_data, container, false)
//
//        covidDataViewModel = ViewModelProvider(requireActivity()).get(CovidDataViewModel::class.java)
//
//        setupUI(view)
//        return view
//    }
//
//    private fun setupUI(view : View){
//        val tv_date = view.findViewById<TextView>(R.id.tv_date)
//        val rv_countryCovidData = view.findViewById<RecyclerView>(R.id.rv_countryCovidData)
//
////        val noCountryCovidDataMessage = view.findViewById<TextView>(R.id.noCountryCovidDataMessage)
//
//        rv_countryCovidData.layoutManager = LinearLayoutManager(requireContext())
//        countryCovidDataAdapter = CountryCovidDataAdapter(emptyList(), emptyList())
//
////        countryCovidDataAdapter = CountryCovidDataAdapter(emptyList())
//        rv_countryCovidData.adapter = countryCovidDataAdapter
//
//        covidDataViewModel.selectedDate.observe(viewLifecycleOwner, Observer { selectedDate ->
//            tv_date.text = selectedDate
//
//        })
//        getDate(view)
//    }
//
//    fun getDate(view: View) {
//
//        val calendar = Calendar.getInstance()
//        val year = calendar.get(Calendar.YEAR)
//        val month = calendar.get(Calendar.MONTH)
//        val day = calendar.get(Calendar.DAY_OF_MONTH)
//
//        val tv_date = view.findViewById<TextView>(R.id.tv_date)
//
//        tv_date.isClickable = true
//        tv_date.setOnClickListener {
//            val datePickerDialog = DatePickerDialog(
//                view.context,
//                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
//                    // Update the format to dd/mm/yyyy
//                    val date = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
//                    tv_date.text = date
//                    selectedDate = date
//
//                    val selectedDate = String.format(
//                        "%04d-%02d-%02d",
//                        year,
//                        month + 1,
//                        dayOfMonth
//                    ) // Format as YYYY-MM-DD
//
//                    covidDataViewModel.setSelectedDate(date)
//
//                    getCountryData(selectedDate)
////                    getCountryCovidData(selectedDate)
//
//                },
//                year,
//                month,
//                day
//            ).show()
//        }
//
//        covidDataViewModel.selectedDate.observe(viewLifecycleOwner, Observer { selectedDate ->
//            tv_date.text = selectedDate
//        })
//
//    }
//
//    private fun getCountryData(selectedDate: String){
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://covid-api.com/api/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val api = retrofit.create(CovidDataAPI::class.java)
//
//        lifecycleScope.launch(Dispatchers.IO) {
//            val response = api.getCountries()
//
//            if(response.isSuccessful){
//                val countries = response.body()?.data ?: emptyList()
//                val isoCodes = countries.map { it.iso }
//
//                Log.d("TAG", "getCountryData: ${countries}")
//                Log.d("TAG", "ISO: ${isoCodes}")
//
//                withContext(Dispatchers.Main){
//                    for(iso in isoCodes){
////                        Log.d("TAG", "Country: ${co}")
//                        getCountryCovidData(selectedDate, iso)
////                        countryCovidDataAdapter.updateData(countries, listOf(covidData))
//                    }
//                }
//            }
//            else{
//                Log.e("TAG", "Failed to fetch data. Response code: ${response.code()} ")
//            }
//        }
//
//    }
//
//    private fun getCountryCovidData(date: String, iso: String){
//
//        val okHttpClient = OkHttpClient.Builder()
//            .connectTimeout(30, TimeUnit.SECONDS)
//            .writeTimeout(30, TimeUnit.SECONDS)
//            .readTimeout(30, TimeUnit.SECONDS)
//            .build()
//
//        val gson = GsonBuilder()
//            .registerTypeAdapter(CountryCovidDataApiResponse::class.java, CountryCovidDataDeserializer())
//            .create()
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://covid-api.com/api/")
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .client(okHttpClient)
//            .build()
//
//        val api = retrofit.create(CovidDataAPI::class.java)
//
//        lifecycleScope.launch(Dispatchers.IO) {
//            val response = api.getCountryCovidData(date, iso)
//
//            if (response.isSuccessful){
//                Log.d("TAG", "getCountryCovidData: ${response.body()}")
//            }
//            else{
//                Log.e("TAG", "Failed to fetch data. Response code: ${response.code()} ")
//            }
//        }
//    }


}