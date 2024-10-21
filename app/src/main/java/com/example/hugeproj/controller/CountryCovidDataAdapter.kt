package com.example.hugeproj.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hugeproj.R
import com.example.hugeproj.model.Country
import com.example.hugeproj.model.CountryCovidData
import org.w3c.dom.Text

class CountryCovidDataAdapter(
    private var covidDataList: List<CountryCovidData>,
) : RecyclerView.Adapter<CountryCovidDataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryCovidDataViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val country_row = layoutInflater.inflate(R.layout.data_row,parent,false)
        return CountryCovidDataViewHolder(country_row)
    }

    override fun onBindViewHolder(holder: CountryCovidDataViewHolder, position: Int) {
        val covidData = covidDataList[position]
        var fatalityRatePercentage = covidData.fatality_rate * 100

//        holder.tv_countryname.text = covidData.
        holder.tv_confirmedCases.text =  covidData.confirmed.toString()
        holder.tv_deaths.text = covidData.deaths.toString()
//        holder.tv_recovered.text = "Recovered: " + covidData.recovered.toString()
        holder.tv_activeCases.text = covidData.active.toString()
        holder.tv_fatalityRate.text =  fatalityRatePercentage.toString() + "%"
    }

    override fun getItemCount(): Int {
        return covidDataList.size
    }

    fun updateData(newCovidData: List<CountryCovidData>) {
        covidDataList = newCovidData
        notifyDataSetChanged()
    }

}

class CountryCovidDataViewHolder(view: View) : RecyclerView.ViewHolder(view){
    val tv_confirmedCases: TextView = view.findViewById(R.id.tv_confirmedCases)
    val tv_deaths: TextView = view.findViewById(R.id.tv_deaths)
//    val tv_recovered: TextView = view.findViewById(R.id.tv_recovered)
    val tv_activeCases: TextView = view.findViewById(R.id.tv_activeCases)
    val tv_fatalityRate: TextView = view.findViewById(R.id.tv_fatalityRate)
}