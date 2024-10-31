package com.example.hugeproj.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hugeproj.R
import com.example.hugeproj.model.CountryCovidDataDeserializer
import com.example.hugeproj.model.CovidDataDeserializer
import com.example.hugeproj.model.CountryCovidDataApiResponse
import com.example.hugeproj.model.CovidDataAPI
import com.example.hugeproj.model.GlobalCovidDataApiResponse
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar

class CovidDataFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var globalCovidDataAdapter: DataAdapter
    private lateinit var countryCovidDataAdapter: CountryCovidDataAdapter

    private val countryIsoMap = mutableMapOf<String, String>()
    private var selectedDate: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.covid_data, container, false)
        setupUI(view)
        return view
    }

    private fun setupUI(view: View) {
        val tv_date = view.findViewById<TextView>(R.id.tv_date)
        val tv_noGlobalDataMessage = view.findViewById<TextView>(R.id.tv_noGlobalDataMessage)
        val noCountryCovidDataMessage = view.findViewById<TextView>(R.id.noCountryCovidDataMessage)

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val spn_countrySelect = view.findViewById<Spinner>(R.id.spn_countrySelect)
        val countryAdapter = ArrayAdapter<String>(
            requireContext(), android.R.layout.simple_spinner_item, mutableListOf()
        )
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spn_countrySelect.adapter = countryAdapter
        spn_countrySelect.onItemSelectedListener = this

        // Date selection
        tv_date.isClickable = true
        tv_date.setOnClickListener {
            DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
                val date = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                tv_date.text = date
                selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
                getGlobalCovidData(selectedDate)
            }, year, month, day).show()
        }

        val rv_globalCovidData = view.findViewById<RecyclerView>(R.id.rv_globalCovidData)
        val rv_countryCovidData = view.findViewById<RecyclerView>(R.id.rv_countryCovidData)

        // Set up RecyclerViews
        rv_globalCovidData.layoutManager = LinearLayoutManager(requireContext())
        rv_countryCovidData.layoutManager = LinearLayoutManager(requireContext())

        globalCovidDataAdapter = DataAdapter(emptyList())
        countryCovidDataAdapter = CountryCovidDataAdapter(emptyList())

        rv_globalCovidData.adapter = globalCovidDataAdapter
        rv_countryCovidData.adapter = countryCovidDataAdapter

        getCountryData()
    }

    private fun getCountryData() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://covid-api.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(CovidDataAPI::class.java)

        lifecycleScope.launch(Dispatchers.IO) {
            val response = api.getCountries()
            if (response.isSuccessful) {
                val countries = response.body()?.data ?: emptyList()

                val countryNames = mutableListOf("Please select a country")
                countryIsoMap["Please select a country"] = ""

                countries.forEach { country ->
                    countryIsoMap[country.name] = country.iso
                    countryNames.add(country.name)
                }

                withContext(Dispatchers.Main) {
                    val spn_countrySelect = view?.findViewById<Spinner>(R.id.spn_countrySelect)
                    val countryDataAdapter = spn_countrySelect?.adapter as ArrayAdapter<String>
                    countryDataAdapter.clear()
                    countryDataAdapter.addAll(countryNames)
                    countryDataAdapter.notifyDataSetChanged()
                }
            } else {
                Log.e("CovidDataFragment", "Failed to fetch data. Response code: ${response.code()}")
            }
        }
    }

    private fun getCountryCovidData(date: String?, iso: String?) {
        if (date == null || iso == null) {
            Log.e("CovidDataFragment", "Date or ISO code is null")
            return
        }

        val noCountryCovidDataMessage = view?.findViewById<TextView>(R.id.noCountryCovidDataMessage)
        val rv_countryCovidData = view?.findViewById<RecyclerView>(R.id.rv_countryCovidData)

        val gson = GsonBuilder()
            .registerTypeAdapter(CountryCovidDataApiResponse::class.java, CountryCovidDataDeserializer())
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://covid-api.com/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val api = retrofit.create(CovidDataAPI::class.java)

        lifecycleScope.launch(Dispatchers.IO) {
            val response = api.getCountryCovidData(date, iso)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val covidData = response.body()?.data
                    if (covidData != null) {
                        noCountryCovidDataMessage?.visibility = View.GONE
                        rv_countryCovidData?.visibility = View.VISIBLE
                        countryCovidDataAdapter.updateData(listOf(covidData))
                    } else {
                        noCountryCovidDataMessage?.visibility = View.VISIBLE
                        rv_countryCovidData?.visibility = View.GONE
                    }
                } else {
                    Log.e("CovidDataFragment", "Failed to fetch data: ${response.code()}")
                }
            }
        }
    }

    private fun getGlobalCovidData(date: String?) {
        if (date == null) {
            Log.e("CovidDataFragment", "Date is null")
            return
        }

        val tv_noGlobalDataMessage = view?.findViewById<TextView>(R.id.tv_noGlobalDataMessage)
        val rv_globalCovidData = view?.findViewById<RecyclerView>(R.id.rv_globalCovidData)

        val gson = GsonBuilder()
            .registerTypeAdapter(GlobalCovidDataApiResponse::class.java, CovidDataDeserializer())
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://covid-api.com/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val api = retrofit.create(CovidDataAPI::class.java)

        lifecycleScope.launch(Dispatchers.IO) {
            val response = api.getGlobalCovidData(date)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val covidData = response.body()?.data
                    if (covidData == null) {
                        tv_noGlobalDataMessage?.visibility = View.VISIBLE
                        rv_globalCovidData?.visibility = View.GONE
                    } else {
                        tv_noGlobalDataMessage?.visibility = View.GONE
                        rv_globalCovidData?.visibility = View.VISIBLE
                        globalCovidDataAdapter.updateData(listOf(covidData))
                    }
                } else {
                    Log.e("CovidDataFragment", "Failed to fetch data")
                }
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedCountryName = parent?.getItemAtPosition(position).toString()
        if (selectedCountryName == "Please select a country") {
            return
        }

        val countryAdapter = parent?.adapter as ArrayAdapter<String>
        if (countryAdapter.getItem(0) == "Please select a country") {
            countryAdapter.remove("Please select a country")
            countryAdapter.notifyDataSetChanged()
        }

        val iso = countryIsoMap[selectedCountryName]
        if (selectedDate != null && iso != null) {
            getCountryCovidData(selectedDate, iso)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Do nothing hehe
    }
}
