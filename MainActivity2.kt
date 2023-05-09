@file:Suppress("DEPRECATION")
package com.example.myapplication

import android.annotation.SuppressLint
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiConfiguration.AuthAlgorithm.strings
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class MainActivity2 : AppCompatActivity() {

    private lateinit var tView: TextView
    private lateinit var eText: EditText
    private lateinit var main_btn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        tView = findViewById(R.id.result_info)
        eText = findViewById(R.id.user_field)
        main_btn = findViewById(R.id.main_btn)

        main_btn.setOnClickListener {
            if (eText.text.toString().trim().equals("")) {
                Toast.makeText(applicationContext, R.string.nui, Toast.LENGTH_LONG).show()
            } else {
                val city = eText.text.toString()
                val key = "a6a2097a4edd4d5db5ac91edef7e7a4c"
                val url =
                    "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + key + "&units=metric&lang=ru"

                GetUrlData().execute(url)
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private inner class GetUrlData : AsyncTask<String, Unit, Unit>() {

        @Override
        override fun onPreExecute() {
            super.onPreExecute()
            tView.text = ("Ожидайте...")
        }

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: String?) {

            var connection: HttpURLConnection? = null
            var reader: BufferedReader? = null

            try {

                val url = URL(params[0])
                Log.d("zxc", url.toString())
                connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                val stream = connection.inputStream
                reader = BufferedReader(InputStreamReader(stream))

                val sb = StringBuilder()
                var line : String? = null

                do{

                    line = reader.readLine()
                    sb.append("$line\n")

                } while (line !=null )

                val lol = JSONObject(sb.toString())

                Log.d("zxc", sb.toString())

                tView.text =
                    "Температура: " + lol.getJSONObject("main").getInt("temp") + "°C   " +
                    "Ощущается как: " + lol.getJSONObject("main").getInt("feels_like") + "°C"


            } catch (e: MalformedURLException) {
                Log.d("zxc", e.toString())
            } catch (e: IOException) {
                Log.d("zxc", e.toString())
            } finally {
                connection?.disconnect()

                try {
                    reader?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }

        }

        @Deprecated("Deprecated in Java")
        @SuppressLint("SetTextI18n")
        override fun onPostExecute(result: Unit?) {

        }

    }
}
