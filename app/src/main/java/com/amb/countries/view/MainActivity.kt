package com.amb.countries.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.amb.countries.R
import com.amb.countries.viewmodel.ListViewModel

class MainActivity : AppCompatActivity() {

    private val rcCountries by lazy { findViewById<RecyclerView>(R.id.rcCountries) }
    private val swipeLayout by lazy { findViewById<SwipeRefreshLayout>(R.id.swipeLayout) }
    private val tvListError by lazy { findViewById<TextView>(R.id.tvListError) }
    private val loadingView by lazy { findViewById<ProgressBar>(R.id.loadingView) }

    lateinit var viewModel: ListViewModel

    private val countriesAdapter = CountryListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        rcCountries.run {
            layoutManager = LinearLayoutManager(context)
            adapter = countriesAdapter
        }

        swipeLayout.setOnRefreshListener {
            swipeLayout.isRefreshing = false
            viewModel.refresh()
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.countries.observe(this, Observer { countries ->
            countries?.let {
                rcCountries.visibility = View.VISIBLE
                countriesAdapter.updateCountries(it)
            }
        })

        viewModel.countryLoadError.observe(this, Observer { isError ->
            isError?.let { tvListError.visibility = if (it) View.VISIBLE else View.GONE }
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    rcCountries.visibility = View.GONE
                    tvListError.visibility = View.GONE
                }
            }
        })
    }

}
