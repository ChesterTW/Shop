package com.taro.shop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.taro.shop.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_news)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.container, NewsFragment.instance)
        fragmentTransaction.commit()

    }

}