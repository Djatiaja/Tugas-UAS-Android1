package com.example.tugasuas.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugasbookmark.database.database
import com.example.tugasuas.Dao.CartDao
import com.example.tugasuas.R
import com.example.tugasuas.adapter.AdapterItemCart
import com.example.tugasuas.adapter.AdapterItemHistory
import com.example.tugasuas.databinding.FragmentDetailBinding
import com.example.tugasuas.databinding.FragmentHistoryBinding
import com.example.tugasuas.sharePref.PrefManager
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var __binding: FragmentHistoryBinding
    val binding get() = __binding
    lateinit var cartDao:CartDao
    lateinit var  executor:ExecutorService


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
        __binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val database= database.getDatabase(binding.root.context)
        cartDao = database!!.cartDao()!!
        executor = Executors.newSingleThreadExecutor()
        refresh()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun refresh(){
        val prefManager = PrefManager.getInstance(binding.root.context)
        GlobalScope.launch(Dispatchers.IO) {
            val dataCart = cartDao.getALlPurchase(prefManager.getUser()!!._id!!)
            val dataFurniture = prefManager.getData()

            val aedapter = AdapterItemHistory(dataCart, dataFurniture)

            with(binding){
                rvHistory.apply {
                    adapter = aedapter
                    layoutManager = LinearLayoutManager(binding.root.context)
                }

                left.setOnClickListener(){
                    findNavController().navigateUp()
                }
            }

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HistoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}