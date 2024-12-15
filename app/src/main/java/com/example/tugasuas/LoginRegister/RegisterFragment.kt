package com.example.tugasuas.LoginRegister

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tugasuas.LoginActivity
import com.example.tugasuas.R
import com.example.tugasuas.databinding.FragmentLoginBinding
import com.example.tugasuas.databinding.FragmentRegisterBinding
import com.example.tugasuas.model.User
import com.example.tugasuas.network.ApiClient
import com.example.tugasuas.network.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var __binding: FragmentRegisterBinding
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
        __binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            btnRegister.setOnClickListener {
                // Retrieve input values
                val nama = txtNama.text.toString()
                val email = txtEmail.text.toString()
                val password = txtPassword.text.toString()
                val confirmPassword = txtConfirmPassword.text.toString()

                // Validate input
                if (nama.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(requireContext(), "Silakan isi semua kolom", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (password != confirmPassword) {
                    Toast.makeText(requireContext(), "Password dan konfirmasi password tidak cocok", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Perform registration operation
                performRegistration(nama, email, password)
            }

        }
    }

    fun performRegistration(nama: String, email: String, password: String) {
        val user = User(nama = nama, email = email, password = password)
        val api = ApiClient.getInstance()
        api.postUser(user).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(p0: Call<ApiResponse>, p1: Response<ApiResponse>) {
                val act = requireContext() as LoginActivity
                act.viewpager.currentItem = 0

                Toast.makeText(binding.root.context, "Berhasil Register", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(p0: Call<ApiResponse>, t: Throwable) {
                Log.e("API Error", "Registrasi gagal: ${t.message}")
                Toast.makeText(requireContext(), "Registrasi gagal: ${t.message}", Toast.LENGTH_SHORT).show()
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
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}