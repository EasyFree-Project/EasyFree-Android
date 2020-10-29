package com.sosin.easyfree

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.bumptech.glide.Glide
import com.sosin.easyfree.navigation.model.ProductDTO
import com.sosin.easyfree.navigation.user.App
import kotlinx.android.synthetic.main.activity_product_detail.*

class ProductDetailActivity : AppCompatActivity() {
    var productCount : Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val productinfo = intent.getParcelableExtra<ProductDTO>("ProductInfo")

        Toast.makeText(this, productinfo.toString(), LENGTH_LONG).show()

        Glide.with(this).load(productinfo?.url).into(product_detail_image)
        product_detail_name_text.setText(productinfo?.product_name)
        product_detail_price_text.setText(productinfo?.product_price.toString())
        product_detail_content_text.setText(productinfo?.product_content)
        product_detail_location_text.setText(productinfo?.producer_location)
        product_detail_avgreview_text.setText(productinfo?.avg_review)
        product_detail_nutrient_text.setText(productinfo?.nutrient)


        product_detail_minus_btn.setOnClickListener {

        }

        product_detail_plus_btn.setOnClickListener {

        }

        product_to_basket_btn.setOnClickListener {
            OrderProduct(productinfo?.product_number.toString(), productCount)
        }
    }
    fun OrderProduct (product_number : String, count:Int){
        App.BasketDTOs
    }
}