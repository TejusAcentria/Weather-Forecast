package com.weatherforecast.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.weatherforecast.Activity.MainActivity
import com.weatherforecast.POJO.Forecast
import com.weatherforecast.R
import kotlinx.android.synthetic.main.list_forecast.view.*
import java.text.SimpleDateFormat
import java.util.*


class WeatherListAdapter(var mainActivity: MainActivity, var arrayListForecast: List<Forecast>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mainActivity).inflate(R.layout.list_forecast, parent, false)
        return WeatherViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayListForecast.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (holder is WeatherViewHolder) {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_WEEK, position)
            val date = calendar.time

            holder.minTemp.text = String.format(
                mainActivity.getString(R.string.temp_list),
                arrayListForecast.get(position).lowTemp,
                arrayListForecast.get(position).highTemp
            )
            holder.weather1.text = arrayListForecast.get(position).weather

            holder.dayOfWeek.text = SimpleDateFormat(mainActivity.getString(R.string.date_pattern), Locale.ENGLISH).format(date.time)

            setWeatherIcon(arrayListForecast.get(position).weatherIocnId, holder.listIcon)

        }
    }

    private fun setWeatherIcon(weatherIocnId: String?, listIcon: ImageView?) {
        when (weatherIocnId) {
            "01d" -> {

                listIcon!!.setImageResource(R.drawable.sun);

            }

            "01n" -> {
                listIcon!!.setImageResource(R.drawable.sun);

            }

            "02d" -> {
                listIcon!!.setImageResource(R.drawable.clear);

            }


            "02n" -> {
                listIcon!!.setImageResource(R.drawable.clear);

            }


            "03d" -> {
                listIcon!!.setImageResource(R.drawable.clouds);

            }


            "03n" -> {
                listIcon!!.setImageResource(R.drawable.clouds);

            }


            "04d" -> {
                listIcon!!.setImageResource(R.drawable.clouds);

            }


            "04n" -> {
                listIcon!!.setImageResource(R.drawable.clouds);

            }

            "09d" -> {
                listIcon!!.setImageResource(R.drawable.rain);

            }


            "09n" -> {
                listIcon!!.setImageResource(R.drawable.rain);

            }


            "10d" -> {
                listIcon!!.setImageResource(R.drawable.rain);

            }


            "10n" -> {
                listIcon!!.setImageResource(R.drawable.rain);

            }


            "11d" -> {
                listIcon!!.setImageResource(R.drawable.storm);

            }


            "11n" -> {
                listIcon!!.setImageResource(R.drawable.storm);


            }

            "13d" -> {
                listIcon!!.setImageResource(R.drawable.storm);

            }


            "13n" -> {
                listIcon!!.setImageResource(R.drawable.storm);


            }

            "1d" -> {
                listIcon!!.setImageResource(R.drawable.clouds);

            }

            "1n" -> {
                listIcon!!.setImageResource(R.drawable.clouds);


            }

        }
    }


}

class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var dayOfWeek = itemView.dayOfWeek
    var listIcon = itemView.listIcon
    var weather1 = itemView.weather1
    var minTemp = itemView.lowTemp
}

