package com.example.tugasuas.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tugasbookmark.database.database
import com.example.tugasuas.Dao.CartDao
import com.example.tugasuas.R
import com.example.tugasuas.databinding.ItemCardBinding
import com.example.tugasuas.databinding.ItemCartBinding
import com.example.tugasuas.model.Cart
import com.example.tugasuas.model.Furniture
import com.example.tugasuas.store.CartFragment
import java.util.concurrent.ExecutorService

class AdapterItemCart(private val listCart:List<Cart>, private val listFurniture: List<Furniture>): RecyclerView.Adapter<AdapterItemCart.ItemHolder>() {
    inner class ItemHolder(
        val binding: ItemCartBinding
    ):RecyclerView.ViewHolder(binding.root){
        lateinit var cartDao:CartDao
        lateinit var executorService: ExecutorService

        init {
            val database = database.getDatabase(binding.root.context)
            cartDao = database!!.cartDao()!!
            executorService = java.util.concurrent.Executors.newSingleThreadExecutor()
        }

        fun bind(data:Cart, position: Int){
            val furniture = listFurniture.find { it._id == data.key }!!

            with(binding){
                name.text = furniture.name
                price.text = "$ "+(data.quantity * furniture.price).toString()
                Glide.with(binding.root.context)
                    .load(furniture.image)
                    .into(binding.imageHolder)
                quantity.text = data.quantity.toString()
                btnMinus.setOnClickListener(){
                    val quantity = binding.quantity.text.toString().toInt()
                    if (quantity > 1){
                        binding.quantity.text = (quantity - 1).toString()
                        refreshPrice(furniture, data)
                    }
                }
                btnTambah.setOnClickListener(){
                    val quantity = binding.quantity.text.toString().toInt()
                    if (quantity < furniture.quantity){
                        binding.quantity.text = (quantity + 1).toString()
                        refreshPrice(furniture,data)
                    }
                }

                deleteItem.setOnClickListener(){
                    deleteCart(data, position)
                }

                checkboxSelect.setOnClickListener(){
                    checking(data)
                }

                if (data.isCheckout){
                    checkboxSelect.isChecked = true}

            }

        }
        fun checking(data: Cart){
            executorService.execute(){
                if (binding.checkboxSelect.isChecked){
                    data.isCheckout = true
                }else{
                    data.isCheckout = false
                }
                cartDao.update(data)
            }
        }

        fun deleteCart(cart: Cart, position: Int){
            executorService.execute(){
                cartDao.delete(cart)
            }
            binding.deleteItem.setBackgroundResource(R.drawable.baseline_delete_24)
        }

        fun refreshPrice(furniture: Furniture, data: Cart){
            val varquantity = binding.quantity.text.toString().toInt()
            binding.price.text = "$ " +(furniture.price * varquantity).toString()

            executorService.execute(){
                cartDao.update(data.apply {
                    quantity =varquantity
                })
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding = ItemCartBinding.inflate(
            LayoutInflater.from(parent.context)
            ,parent, false
        )
        return ItemHolder(binding)
    }

    override fun getItemCount(): Int {
        return listCart.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(listCart[position], position)
    }
}