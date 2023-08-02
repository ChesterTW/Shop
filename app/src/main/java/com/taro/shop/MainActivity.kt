package com.taro.shop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.taro.shop.MainActivity.FunctionHolder
import com.taro.shop.databinding.ActivityMainBinding
import com.taro.shop.databinding.RowFunctionBinding

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    lateinit var binding: ActivityMainBinding
    private val auth = FirebaseAuth.getInstance()
    private var signup = false
    val functions = listOf<String>("Camera", "Invite friend", "Parking", "Downloading coupons", "News", "Movies")

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


        /// RecyclerView

        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.setHasFixedSize(true)
        binding.recycler.adapter = FunctionAdapter()


    }

    inner class FunctionAdapter() : RecyclerView.Adapter<FunctionHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunctionHolder {
/*            val inflater = LayoutInflater.from(parent.context)
            val binding = RowFunctionBinding.inflate(inflater,parent,false)
            return FunctionHolder(binding)*/

            val view = LayoutInflater.from(parent.context).inflate(R.layout.row_function,parent,false)
            return FunctionHolder(view)
        }

        override fun getItemCount(): Int {
            return functions.size
        }

        override fun onBindViewHolder(holder: FunctionHolder, position: Int) {
            holder.nameText.text = functions[position]
            holder.itemView.setOnClickListener(View.OnClickListener { view ->
                functionClicked(holder, position)
            })
        }

        private fun functionClicked(holder: FunctionHolder, position: Int) {
            Log.d(TAG, "functionClicked: $position")
            when(position){
                1-> startActivity(Intent(this@MainActivity ,ContactActivity::class.java))
                2-> startActivity(Intent(this@MainActivity ,ParkingActivity::class.java))
                4-> startActivity(Intent(this@MainActivity ,NewsActivity::class.java))
                5-> startActivity(Intent(this@MainActivity ,MovieActivity::class.java))
            }
        }

    }

/*    class FunctionHolder(private val binding: RowFunctionBinding) : RecyclerView.ViewHolder(binding.root){

        var nameText = binding.name
    }*/

    class FunctionHolder(view: View) : RecyclerView.ViewHolder(view){
        private val binding = RowFunctionBinding.bind(view)

        val nameText = binding.name
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