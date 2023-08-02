package com.taro.shop

import android.annotation.SuppressLint
import android.database.Observable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.taro.shop.databinding.ActivityParkingBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class ParkingActivity : AppCompatActivity() {
    private val TAG = ParkingActivity::class.java.simpleName
    private lateinit var binding: ActivityParkingBinding

    private val URL = "https://api.jsonserve.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(URL)
        .build()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityParkingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var parkingService = retrofit.create(ParkingService::class.java)

        parkingService.getInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "Success $it")
                binding.info.text = it.toString()
            }, {
                Log.d(TAG, "Error: $it")
            }, {
                Log.d(TAG, "Complete: Done")
            })


    }

}

interface ParkingService{
    @GET("ho12XS")
    fun getInfo() :io.reactivex.Observable<String>
}