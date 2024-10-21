package com.example.hugeproj

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CovidDataViewModel : ViewModel() {
    var selectedDate = MutableLiveData<String>()

    init {
        selectedDate.value = ""
    }

    fun setSelectedDate(date: String){
        selectedDate.value = date
    }
}