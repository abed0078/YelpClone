package com.example.yelpclone

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yelpclone.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_KEY =
    "H7ooAfBYc95e63ZxSpa-66Y3qR0PwxZlhF-PW8oUsrB1OsFO3kf54EF4SaCBGjwga-r2HuojKCOgzewDr0aVBfdEcxwbglRs_gRAgjHQbEuVJ9sP2s9s0LQ1sD3MYXYx"
private const val TAG = "MainActivity"
private const val BASE_URL = "https://api.yelp.com/v3/"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val restaurants = mutableListOf<YelpRestaurant>()
        val adapter = RestaurantAdapter(this, restaurants)
        binding.rvRestaurants.adapter=adapter
        binding.rvRestaurants.layoutManager=LinearLayoutManager(this)
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val yelpService = retrofit.create(YelpService::class.java)
        yelpService.searchRestaurants("Bearer $API_KEY", "Avocado Toast", "New York")
            .enqueue(object : Callback<YelpSearchResult> {
                override fun onResponse(
                    call: Call<YelpSearchResult>,
                    response: Response<YelpSearchResult>
                ) {
                    Log.i(TAG, "Response $response")
                    val body=response.body()
                    if(body==null){
                        Log.w(TAG,"did not receive valid response body from yelp Api....existing")
                        return
                    }
                    restaurants.addAll(body.restaurant)
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                    Log.i(TAG, " onFailure $t")
                }
            })

    }
}



