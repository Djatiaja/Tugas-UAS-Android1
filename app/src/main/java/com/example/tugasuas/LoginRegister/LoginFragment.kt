package com.example.tugasuas.LoginRegister

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tugasuas.R
import com.example.tugasuas.StoreActivity
import com.example.tugasuas.databinding.FragmentBookmarkBinding
import com.example.tugasuas.databinding.FragmentLoginBinding
import com.example.tugasuas.model.User
import com.example.tugasuas.network.ApiClient
import com.example.tugasuas.sharePref.PrefManager

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var __binding: FragmentLoginBinding
    val binding get() = __binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        __binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(binding){


            btnLogin.setOnClickListener {
                // Validasi input
                val email = txtEmail.text.toString()
                val password = txtPassword.text.toString()
                if (email.isEmpty() || password.isEmpty()) {
                    // Tampilkan pesan kesalahan
                    Toast.makeText(binding.root.context, "Silakan masukkan email dan password", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Lakukan operasi login (ini bisa berupa panggilan API atau pengecekan database)
                performLogin("admincoy",email, password)
            }
        }
    }
    fun performLogin(nama:String,email:String, password:String){
        val api = ApiClient.getInstance()
        api.getAllUser().enqueue(object : retrofit2.Callback<List<User>>{
            override fun onResponse(call: retrofit2.Call<List<User>>, response: retrofit2.Response<List<User>>) {
                val data = response.body()
                if (data != null) {
                    for (user in data){
                        if (user.email == email && user.password == password){
                            val intent = Intent(binding.root.context, StoreActivity::class.java)

                            val prefManager = PrefManager.getInstance(binding.root.context)
                            prefManager.saveUser(
                                User(
                                    _id = "",
                                    nama = user.nama,
                                    email = user.nama,
                                    password = user.nama

                                )
                            )

                            startActivity(intent)
                            requireActivity().finish()
                            return
                        }
                    }
                    Toast.makeText(binding.root.context, "Email atau password salah", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<List<User>>, t: Throwable) {
                Toast.makeText(binding.root.context, "Gagal mengambil data", Toast.LENGTH_SHORT).show()
            }

        })






    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}