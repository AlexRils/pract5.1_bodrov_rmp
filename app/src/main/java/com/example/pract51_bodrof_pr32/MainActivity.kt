package com.example.pract51_bodrof_pr32

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val city = findViewById<EditText>(R.id.city)

        val result = findViewById<TextView>(R.id.result)

        val key = "ebe6bbc082a94d29c6312043079e1b21"

        val button = findViewById<Button>(R.id.getWeather)

        fun getWeather(url: String, result: TextView) {

            val queue = Volley.newRequestQueue(this)

            val request = JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                { response ->

                    val pressure = response.getJSONObject("main")
                        .getInt("pressure")

                    val wind = response.getJSONObject("wind")
                        .getDouble("speed")

                    val cityName = response.getString("name")

                    val temperature = response.getJSONObject("main")
                        .getDouble("temp")

                    Log.d("WEATHER", "Город: " + cityName)
                    Log.d("WEATHER", "Температура: " + temperature)

                    result.text = "Город: " + cityName +
                            "\nТемпература: " + temperature +
                            "\nДавление: " + pressure +
                            "\nСкорость ветра: " + wind

                },
                { error ->
                    Log.d("WEATHER", "Ошибка: " + error.toString())

                }
            )

            queue.add(request)
        }

        button.setOnClickListener {

            val url = "https://api.openweathermap.org/data/2.5/weather?q=" +
                    city.text.toString() + "&appid=" + key +
                    "&units=metric&lang=ru"

            getWeather(url, result)
        }


    }
}