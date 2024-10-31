package com.example.hugeproj.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hugeproj.model.GlobalCovidData
import com.example.hugeproj.R

class DataAdapter(private var covidDataList: List<GlobalCovidData>) : RecyclerView.Adapter<DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val data_row = layoutInflater.inflate(R.layout.data_row, parent, false)
        return DataViewHolder(data_row)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val covidData = covidDataList[position]
        val fatalityRatePercentage = covidData.fatality_rate * 100

        holder.tv_confirmedCases.text = covidData.confirmed.toString()
        holder.tv_deaths.text = covidData.deaths.toString()
//        holder.tv_recovered.text = covidData.recovered.toString()
        holder.tv_activeCases.text =  covidData.active.toString()
        holder.tv_fatalityRate.text = fatalityRatePercentage.toString() + "%"
    }

    override fun getItemCount(): Int {
        return covidDataList.size
    }

    fun updateData(newData: List<GlobalCovidData>) {
        covidDataList = newData
        notifyDataSetChanged()
    }
}

class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tv_confirmedCases: TextView = view.findViewById(R.id.tv_confirmedCases)
    val tv_deaths: TextView = view.findViewById(R.id.tv_deaths)
//    val tv_recovered: TextView = view.findViewById(R.id.tv_recovered)
    val tv_activeCases: TextView = view.findViewById(R.id.tv_activeCases)
    val tv_fatalityRate: TextView = view.findViewById(R.id.tv_fatalityRate)
}
