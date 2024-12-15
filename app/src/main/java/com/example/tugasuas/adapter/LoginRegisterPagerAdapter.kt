package com.example.tugasuas.adapter

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tugasuas.LoginRegister.LoginFragment
import com.example.tugasuas.LoginRegister.RegisterFragment
import com.example.tugasuas.R

class LoginRegisterPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    companion object{

        @StringRes
        private val TAB_TILES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
        )
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment : Fragment? = null
        when(position){
            0->fragment = LoginFragment()
            1->fragment = RegisterFragment()
        }
        return  fragment as Fragment
    }


}