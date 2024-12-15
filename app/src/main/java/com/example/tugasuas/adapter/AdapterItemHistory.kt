package com.example.tugasuas.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tugasbookmark.database.database
import com.example.tugasuas.Dao.CartDao
import com.example.tugasuas.databinding.ItemHistoryBinding
import com.example.tugasuas.model.Cart
import com.example.tugasuas.model.Furniture
import java.util.concurrent.ExecutorService

class AdapterItemHistory(private val listCart:List<Cart>, private val listFurniture: List<Furniture>): RecyclerView.Adapter<AdapterItemHistory.ItemHolder>() {
    inner class ItemHolder(
        val binding: ItemHistoryBinding
    ): RecyclerView.ViewHolder(binding.root){
        var cartDao: CartDao
        var executorService: ExecutorService

        init {
            val database = database.getDatabase(binding.root.context)
            cartDao = database!!.cartDao()!!
            executorService = java.util.concurrent.Executors.newSingleThreadExecutor()
        }

        fun bind(data:Cart){
            val furniture = listFurniture.find { it._id == data.key }!!
            with(binding){
                name.text = furniture.name
                price.text = "Harga total : $"+ (furniture.price * data.quantity).toString()
                quantity.text = "Jumlah barang :" + data.quantity.toString() +" buah"

                Glide.with(binding.root.context)
                    .load(furniture.image)
                    .into(binding.imageHolder)

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding = ItemHistoryBinding.inflate(
            LayoutInflater.from(parent.context)
            ,parent, false
        )
        return ItemHolder(binding)
    }

    override fun getItemCount(): Int {
        return listCart.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(listCart[position])
    }
}