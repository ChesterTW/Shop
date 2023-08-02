package com.taro.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.taro.shop.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {
    lateinit var binding: FragmentNewsBinding

    companion object{
        val instance: NewsFragment by lazy { NewsFragment() }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentNewsBinding.inflate(layoutInflater)
        return inflater.inflate(R.layout.fragment_news, container, false)
    }
}