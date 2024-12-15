package com.example.tugasuas

import android.content.Intent
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.tugasuas.adapter.AdapterItemCard
import com.example.tugasuas.adapter.LoginRegisterPagerAdapter
import com.example.tugasuas.databinding.ActivityLoginBinding
import com.example.tugasuas.databinding.ActivityStoreBinding
import com.example.tugasuas.model.Furniture
import com.example.tugasuas.sharePref.PrefManager
import com.google.android.material.tabs.TabLayoutMediator

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var viewpager: ViewPager2

    companion object{
        @StringRes
        private val  TAB_TILES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefManager = PrefManager.getInstance(this)

        if (prefManager.getUser()?.equals(null) == true){
            val intent = Intent(this@LoginActivity, StoreActivity::class.java)
            startActivity(intent)
        }

        val LoginRegisterPagerAdapter = LoginRegisterPagerAdapter(this@LoginActivity)


        viewpager= binding.viewPager
        viewpager.adapter = LoginRegisterPagerAdapter

        val tabs = binding.tabLayout
        TabLayoutMediator(tabs, viewpager){
                tabs, position ->tabs.text = resources.getString(TAB_TILES[position])
        }.attach()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}