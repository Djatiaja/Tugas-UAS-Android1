package com.example.tugasuas.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tugasbookmark.database.database
import com.example.tugasuas.Dao.BookmarkDao
import com.example.tugasuas.Dao.CartDao
import com.example.tugasuas.R
import com.example.tugasuas.databinding.ItemCardBinding
import com.example.tugasuas.model.Bookmark
import com.example.tugasuas.model.Cart
import com.example.tugasuas.model.Furniture
import com.example.tugasuas.sharePref.PrefManager
import com.example.tugasuas.store.HomeFragment
import com.example.tugasuas.store.HomeFragmentDirections
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AdapterItemCard(private val listFurniture: List<Furniture>,
                      private val navigateToDetail: (String) -> Unit // Lambda for navigation
    ): RecyclerView.Adapter<AdapterItemCard.ItemHolder>() {
    inner class ItemHolder(
        private var binding: ItemCardBinding,


    ):RecyclerView.ViewHolder(binding.root){
        var bookmarkDao: BookmarkDao
        var executorService: ExecutorService
        var cartDao:CartDao

        init {
            executorService = Executors.newSingleThreadExecutor()
            val database = database.getDatabase(binding.root.context)
            bookmarkDao =database!!.bookmarkDao()!!
            cartDao = database!!.cartDao()!!
        }

        fun bind(data:Furniture){

            executorService.execute(){
                val bookmarkItem = bookmarkDao.getBookmark(data._id)
                    with(binding) {
                        if (bookmarkItem == null) {
                            bookmark.setBackgroundResource(R.drawable.baseline_bookmark_border_24)
                        } else {
                            bookmark.setBackgroundResource(R.drawable.baseline_bookmark_24)
                        }
                }
            }

            with(binding){
                val imageUrl = data.image
                title.text = data.name
                description.text = data.description
                price.text = "$"+data.price.toString()

                Glide.with(binding.root.context)
                    .load(imageUrl)
                    .into(binding.imageHolder)

                bookmark.setOnClickListener(){
                    bookmarking(data._id)
                }

                title.setOnClickListener(){
                    navigateToDetail(data._id)
                }

                btnAddToCart.setOnClickListener(){
                    addToCart(data)
                }
            }


        }

        fun addToCart(furniture: Furniture){
            val prefManager = PrefManager.getInstance(binding.root.context)

            executorService.execute(){
                cartDao.insert(Cart(
                    key = furniture._id,
                    quantity = 1,
                    isCheckout = false,
                    userID = prefManager.getUser()!!._id!!
                ))
            }
        }

        fun bookmarking(pkey:String){
            executorService.execute {
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
            executorService.execute(){
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

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding = ItemCardBinding.inflate(
            LayoutInflater.from(parent.context)
            ,parent, false
        )
        return ItemHolder(binding)
    }

    override fun getItemCount(): Int {
        return listFurniture.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(listFurniture[position])
    }
}