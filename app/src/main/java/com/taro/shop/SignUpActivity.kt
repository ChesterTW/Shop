package com.taro.shop

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.taro.shop.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signup.setOnClickListener(View.OnClickListener {
            val sEmail = binding.email.text.toString()
            val sPassword = binding.password.text.toString()

            /// FirebaseAuth 是取物件的方式
            /// createUserWithEmailAndPassword 建立用戶，提供電子郵件、密碼。
            /// 無法得知執行結果是否成功。
//            FirebaseAuth.getInstance().createUserWithEmailAndPassword(sEmail, sPassword)
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(sEmail, sPassword)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        AlertDialog.Builder(this)
                            .setTitle("Sign UP")
                            .setMessage("Account created.")
                            .setPositiveButton("OK") { _, _ ->
                                setResult(Activity.RESULT_OK)
                                finish()
                            }.show()

                    } else{
                        AlertDialog.Builder(this)
                            .setTitle("Sign UP")
                            .setMessage(it.exception?.message)
                            .setPositiveButton("OK", null)
                            .show()
                    }
                    /// it.exception?.message 會顯示無法錯誤的訊息，而且是能夠讓使用者理解的。
                }

        })
    }
}