package com.example.tugasuas

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.tugasuas.databinding.ActivityStoreBinding
import com.example.tugasuas.model.Furniture
import com.example.tugasuas.network.ApiClient
import com.example.tugasuas.sharePref.PrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreActivity : AppCompatActivity() {
    lateinit var binding: ActivityStoreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityStoreBinding.inflate(layoutInflater)
        refresh()
        setContentView(binding.root)

        with(binding){
            val navController = findNavController(R.id.nav_host_fragment)
            bottomNavigationView.setupWithNavController(navController)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun refresh(){
        val client = ApiClient.getInstance()
        val furnitures = client.getAllFurnitures()
        val prefManager = PrefManager.getInstance(binding.root.context)
        furnitures.enqueue(object : Callback<List<Furniture>> {
            override fun onResponse(p0: Call<List<Furniture>>, p1: Response<List<Furniture>>) {
                prefManager.saveData(p1.body()!!)
                Toast.makeText(this@StoreActivity, "Berhasil Fetch data", Toast.LENGTH_SHORT).show()
            }
            override fun onFailure(p0: Call<List<Furniture>>, p1: Throwable) {
                Toast.makeText(binding.root.context, "Gagal fetch data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }
}