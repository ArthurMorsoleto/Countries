package com.amb.countries.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amb.countries.R
import com.amb.countries.model.Country
import com.amb.countries.util.getProgressDrawable
import com.amb.countries.util.loadImage
import kotlinx.android.synthetic.main.item_country.view.*

class CountryListAdapter(
    var countries: ArrayList<Country>
) : RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    fun updateCountries(newCountries: List<Country>) {
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_country, parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvCountryName: TextView = view.tvName
        private val tvCountryCapital: TextView = view.tvCapital
        private val ivCountryFlag: ImageView = view.ivFlag

        private val progressDrawable = getProgressDrawable(context = view.context)

        fun bind(country: Country) {
            tvCountryName.text = country.name
            tvCountryCapital.text = country.capital
            ivCountryFlag.loadImage(country.flag, progressDrawable)
        }
    }
}