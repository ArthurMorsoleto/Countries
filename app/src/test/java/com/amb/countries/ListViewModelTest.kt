package com.amb.countries

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.amb.countries.model.CountriesService
import com.amb.countries.model.Country
import com.amb.countries.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class ListViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var countriesService: CountriesService

    @InjectMocks
    var listViewModelTest = ListViewModel()

    private var testSingle: Single<List<Country>>? = null

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Before
    fun setupRxJavaSchedulers() {
        val immediate = object : Scheduler() {
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }

            override fun scheduleDirect(run: Runnable?, delay: Long, unit: TimeUnit?): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
    }

    @Test
    fun getCountriesSuccess() {
        val mockedCountry = Country("Name", "Capital", "")
        val countriesList = arrayListOf(mockedCountry)

        testSingle = Single.just(countriesList)

        Mockito.`when`(countriesService.getCountries()).thenReturn(testSingle)

        listViewModelTest.refresh()

        Assert.assertEquals(1, listViewModelTest.countries.value?.size)
        Assert.assertEquals(false, listViewModelTest.countryLoadError.value)
        Assert.assertEquals(false, listViewModelTest.loading.value)
    }

    @Test
    fun getCountriesError() {
        testSingle = Single.error(Throwable())

        Mockito.`when`(countriesService.getCountries()).thenReturn(testSingle)

        listViewModelTest.refresh()

        Assert.assertEquals(null, listViewModelTest.countries.value)
        Assert.assertEquals(true, listViewModelTest.countryLoadError.value)
        Assert.assertEquals(false, listViewModelTest.loading.value)
    }


}