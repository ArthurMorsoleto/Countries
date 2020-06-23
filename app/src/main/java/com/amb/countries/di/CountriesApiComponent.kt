package com.amb.countries.di

import com.amb.countries.model.CountriesService
import dagger.Component

@Component(modules = [CountriesApiModule::class])
interface CountriesApiComponent {

    fun inject(service: CountriesService)
}