package com.taro.shop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.taro.shop.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val auth = FirebaseAuth.getInstance()
    private var signup = false

    // Step 1: Create launcher, Contract, When Finish to Do
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
        if(result.resultCode == RESULT_OK){
            val intent = Intent(this, NicknameActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

/*        if (!signup){
            val intent = Intent(this,SignUpActivity::class.java)
//            startActivity(intent)

            // Step  2: Call Launcher
            launcher.launch(intent)
        }*/

        auth.addAuthStateListener { auth ->
            authChanged(auth)
        }


//        binding.nickname.text = getSharedPreferences("shop", MODE_PRIVATE).getString("NICKNAME","")
        FirebaseDatabase.getInstance()
            .getReference("users")
            .child(auth.currentUser!!.uid)
            .child("nickname")
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    binding.nickname.text = snapshot.value.toString()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        /// Spinner
        val colors = arrayOf("Red", "Green", "Blue")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, colors)
        //adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                Log.d("MainActivity", "onItemSelected: ${colors[position]}")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


    }

    private fun authChanged(auth: FirebaseAuth) {
        if(auth.currentUser == null){
            val intent = Intent(this,SignUpActivity::class.java)
            launcher.launch(intent)
        } else{
            Log.d("MainActivity", "authChanged: ${auth.currentUser?.uid}")
        }
    }
}