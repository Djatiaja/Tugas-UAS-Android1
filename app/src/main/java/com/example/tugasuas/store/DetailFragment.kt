package com.example.tugasuas.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.tugasbookmark.database.database
import com.example.tugasuas.Dao.BookmarkDao
import com.example.tugasuas.Dao.CartDao
import com.example.tugasuas.R
import com.example.tugasuas.databinding.FragmentDetailBinding
import com.example.tugasuas.model.Bookmark
import com.example.tugasuas.model.Cart
import com.example.tugasuas.model.Furniture
import com.example.tugasuas.sharePref.PrefManager
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var __binding: FragmentDetailBinding
    val binding get() = __binding!!

    lateinit var bookmarkDao:BookmarkDao
    lateinit var cartDao: CartDao
    lateinit var executor: ExecutorService

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

        __binding = FragmentDetailBinding.inflate(inflater, container, false)
        val database= database.getDatabase(binding.root.context)
        bookmarkDao = database!!.bookmarkDao()!!
        cartDao = database!!.cartDao()!!
        executor = Executors.newSingleThreadExecutor()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefManager = PrefManager.getInstance(binding.root.context)
        val datas = prefManager.getData()
        val id = arguments?.getString("key")
        val data = getFurnitureById(id!!, datas)
        refresh(data._id)

        with(binding){
            txtTitle.text = data.name
            txtQty.text = 0.toString()
            txtPrice.text = "$ "+ data.price.toString()
            txtDescription.text = data.description

            left.setOnClickListener(){
                findNavController().navigateUp()
            }

            btnMinus.setOnClickListener(){
                var qty = txtQty.text.toString().toInt()
                if (qty > 0){
                    qty--
                    txtQty.text = qty.toString()
                }
            }
            btnPlus.setOnClickListener(){
                var qty = txtQty.text.toString().toInt()
                if (qty < data.quantity){
                    qty++
                    txtQty.text = qty.toString()
                }
            }

            Glide.with(binding.root.context)
                .load(data.image)
                .into(binding.imageHolder)



            bookmark.setOnClickListener(){
                bookmarking(data._id)
            }

            btnAddToCart.setOnClickListener(){
                val quantity = txtQty.text.toString().toInt()
                addToCart(data, quantity)
            }
        }
    }

    fun addToCart(furniture: Furniture, quantity:Int){
        val prefManager = PrefManager.getInstance(binding.root.context)
        executor.execute(){
            cartDao.insert(Cart(
                key = furniture._id,
                quantity = quantity,
                isCheckout = false,
                userID = prefManager.getUser()!!._id!!
            ))
        }
    }

    fun bookmarking(pkey:String){
        executor.execute {
            val bookmarkItem = bookmarkDao.getBookmark(pkey)
            if (bookmarkItem == null) {
                bookmarkDao.insert(Bookmark(key = pkey))
            } else {
                bookmarkDao.delete(bookmarkItem)
            }
        }
        refresh(pkey)
    }
    fun refresh(pkey: String){
        executor.execute(){
            val bookmarkItem = bookmarkDao.getBookmark(pkey)
            with(binding) {
                if (bookmarkItem == null) {
                    bookmark.setBackgroundResource(R.drawable.baseline_bookmark_border_24)
                } else {
                    bookmark.setBackgroundResource(R.drawable.baseline_bookmark_24)
                }
            }
        }
    }

    fun getFurnitureById(id: String, furnitureList:List<Furniture>): Furniture{
        return furnitureList.find { furniture -> furniture._id == id }!!
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}