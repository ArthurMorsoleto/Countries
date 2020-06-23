package com.amb.countries.di

import com.amb.countries.viewmodel.ListViewModel
import dagger.Component

@Component(modules = [CountriesServiceModule::class])
interface CountriesServiceComponent {

    fun inject(viewModel: ListViewModel)
}