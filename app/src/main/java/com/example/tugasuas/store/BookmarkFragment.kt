package com.example.tugasuas.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tugasbookmark.database.database
import com.example.tugasuas.R
import com.example.tugasuas.adapter.AdapterItemCard
import com.example.tugasuas.databinding.FragmentBookmarkBinding
import com.example.tugasuas.databinding.FragmentHomeBinding
import com.example.tugasuas.model.Furniture
import com.example.tugasuas.network.ApiClient
import com.example.tugasuas.network.AppService
import com.example.tugasuas.sharePref.PrefManager
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor
import java.util.concurrent.Executors

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BookmarkFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookmarkFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var __binding: FragmentBookmarkBinding
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
        __binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        println("resume bookmark")
    }

    override fun onPause() {
        super.onPause()
        println("pause bookmark")
        refresh()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun refresh(){
        val database = database.getDatabase(binding.root.context)
        val dao = database!!.bookmarkDao()!!
        val prefManager = PrefManager.getInstance(binding.root.context)
        GlobalScope.launch(Dispatchers.IO) {
            val data = prefManager.getData().filter { item ->
                dao.getBookmark(item._id, prefManager.getUser()!!._id!!) != null
            }

            withContext(Dispatchers.Main) {
                val padapter = AdapterItemCard(data){
                    id->
                    val action = BookmarkFragmentDirections.actionBookmarkFragmentToDetailFragment(id)
                    findNavController().navigate(action)
                }
                with(binding){
                    rvBookmark.apply {
                        adapter = padapter
                        layoutManager= GridLayoutManager(binding.root.context, 2)
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refresh()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BookmarkFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BookmarkFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}