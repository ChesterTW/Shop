package com.taro.shop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.taro.shop.databinding.ActivityNicknameBinding

class NicknameActivity : AppCompatActivity() {
    private val TAG = NicknameActivity::class.java.simpleName
    private lateinit var binding: ActivityNicknameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityNicknameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.done.setOnClickListener(View.OnClickListener {

            /// SharedPreferences Stroage
            getSharedPreferences("shop", MODE_PRIVATE)
                .edit()
                .putString("NICKNAME", binding.nick.text.toString())
                .apply()


            /// Firebase realtime database storage
            FirebaseDatabase.getInstance()
                .getReference("users")
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child("nickname")
                .setValue(binding.nick.text.toString())

            /// Close this page
            finish()
        })

    }
}