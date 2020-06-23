package com.amb.countries.di

import com.amb.countries.model.CountriesService
import dagger.Module
import dagger.Provides

@Module
class CountriesServiceModule {

    @Provides
    fun providesCountriesServices(): CountriesService {
        return CountriesService()
    }
}