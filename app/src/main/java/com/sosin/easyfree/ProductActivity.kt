package com.sosin.easyfree

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sosin.easyfree.navigation.model.ProductDTO
import com.sosin.easyfree.navigation.user.App
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.android.synthetic.main.product_detail.view.*
import java.lang.Exception


class ProductActivity : AppCompatActivity() {
    val handler : Handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)


        this.findViewById<RecyclerView>(R.id.product_recyclerview).adapter = ProductViewRecyclerViewAdapter()
        this.findViewById<RecyclerView>(R.id.product_recyclerview).layoutManager = GridLayoutManager(
            this, 2
        )
        category_text.text = App.ProductDTOs[0].category_number

    }

    inner class ProductViewRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var productDTOs: ArrayList<ProductDTO> = arrayListOf()

        init {
            productDTOs = App.ProductDTOs
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view =
                LayoutInflater.from(parent.context).inflate(R.layout.product_detail, parent, false)
            return CustomViewHolder(view)
        }

        inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view)

        override fun getItemCount(): Int {
            return productDTOs.size
        }


        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as CustomViewHolder).itemView
            //Product Image
            Glide.with(holder.itemView.context).load(productDTOs!![position].url).into(viewHolder.findViewById(R.id.product_image))
            //Product Name
            viewHolder.findViewById<TextView>(R.id.product_name_text).text =
                productDTOs!![position].product_name

            //Product Count
            viewHolder.findViewById<TextView>(R.id.product_price_text).text =
                productDTOs!![position].product_price.toString()

            viewHolder.product_image.setOnClickListener {
                // 화면 전환 product number 어떻게?
            }
            viewHolder.product_image.setOnClickListener {
                productDTOs!![position]?.let { it1 -> moveProductDetailPage(it1) }
            }
        }

    }

    fun moveProductDetailPage(pn : ProductDTO){
        var detailintent = Intent(this, ProductDetailActivity::class.java)
        detailintent.putExtra("ProductInfo", pn)
        startActivity(detailintent)
    }


}