package com.amb.countries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amb.countries.model.Country

class ListViewModel : ViewModel() {

    val countries = MutableLiveData<List<Country>>()
    val countryLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchCountries()
    }

    private fun fetchCountries() {
        val mockData = listOf(
            Country("A"),
            Country("B"),
            Country("C"),
            Country("D"),
            Country("E")
        )
        countryLoadError.value = false
        loading.value = false
        countries.value = mockData
    }

}