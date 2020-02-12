package com.weatherforecast.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.weatherforecast.Adapter.WeatherListAdapter
import com.weatherforecast.POJO.Forecast
import com.weatherforecast.R
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private var arrayListForecast: MutableList<Forecast>? = null

    private val JSON_URL =
        "http://api.openweathermap.org/data/2.5/forecast?q=Jaipur&cnt=5&mode=json&units=metric&appid=36b391cad222acde5d9f53bd20b64e98"

    var weatherList: RecyclerView? = null
    var weatherListAdapter: WeatherListAdapter? = null
    var currentTempText:TextView?=null
    var currentDayofweek:TextView?=null
    var currentCity:TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()


        loadWeatherList()

        arrayListForecast = ArrayList()

    }


    private fun initView() {
        weatherList = findViewById(R.id.weatherList)

        currentTempText=findViewById(R.id.textView2)

        currentCity=findViewById(R.id.textView)

        currentDayofweek=findViewById(R.id.textView3)

        val viewManager = LinearLayoutManager(this)
        weatherList.apply {
            this!!.layoutManager = viewManager
            isNestedScrollingEnabled = false
            hasFixedSize()
        }
    }



    private fun renderForecastWeather(json: JSONObject) {
        try {
            val list = json.getJSONArray("list")

            currentweather(list, json)

            setCurrentDate()

            for (i in 0..4) {

                val listItem = list.getJSONObject(i)
                val temp = listItem.getJSONObject("main")
                val min = temp.getString("temp_min")
                val max = temp.getString("temp_max")
                val weather = listItem.getJSONArray("weather").getJSONObject(0)
                val forecast = Forecast()
                forecast.highTemp = max
                forecast.lowTemp = min
                forecast.weather = weather.get("description").toString()
                forecast.weatherId = weather.get("id").toString()
                forecast.weatherIocnId = weather.get("icon").toString()
                arrayListForecast!!.add(forecast)

            }


            weatherListAdapter = WeatherListAdapter(this, arrayListForecast!!)
            weatherList!!.adapter = weatherListAdapter


        } catch (e: JSONException) {
            Log.e("FORECAST_JSON_ERROR", e.toString())
        }

    }

    private fun setCurrentDate() {
        val sdf = SimpleDateFormat("EE")
        val d = Date()
        val dayOfTheWeek = sdf.format(d)
        currentDayofweek!!.text = "Todays " + " " + dayOfTheWeek
    }


    private fun currentweather(list: JSONArray, json: JSONObject) {
        val listItem = list.getJSONObject(0)
        val main = listItem.getJSONObject("main")
        val city = json.getJSONObject("city")

        currentCity!!.text = city.getString("name")
        currentTempText!!.text = main.getString("temp") + "°C"
    }


    private fun loadWeatherList() {
        showProgress()
        val stringRequest = StringRequest(
            Request.Method.GET, JSON_URL,
            object : Response.Listener<String> {
                override fun onResponse(response: String) {
                    try {
                        hideProgress()
                        val obj = JSONObject(response)

                        slideUp(weatherList!!)

                        renderForecastWeather(obj)

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError) {
                    hideProgress()
                    gotToErrorActivity()
                }
            })

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    private fun gotToErrorActivity() {
        val intent = Intent(this, ErrorActivity::class.java)
        startActivityForResult(intent, 0)
    }


    fun showProgress() {
        if (progressBar != null) {
            progressBar!!.visibility = View.VISIBLE
        }
    }

    fun hideProgress() {
        if (progressBar!!.visibility == View.VISIBLE) {
            progressBar!!.visibility = View.GONE
        }
    }


    fun slideUp(view: RecyclerView) {
        view.visibility = View.VISIBLE
        val animate = TranslateAnimation(
            0f,
            0f,
            view.height.toFloat(),
            0f
        )
        animate.duration = 1100
        animate.fillAfter = true
        view.startAnimation(animate)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0) {
            if (data?.getBooleanExtra("refresh", false) == true) {
                loadWeatherList()
            }
        }

    }

}
