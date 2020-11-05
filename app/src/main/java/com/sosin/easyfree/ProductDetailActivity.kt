package com.sosin.easyfree

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.sosin.easyfree.navigation.model.BasketDTO
import com.sosin.easyfree.navigation.model.ProductDTO
import com.sosin.easyfree.navigation.user.App
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlin.math.max

class ProductDetailActivity : AppCompatActivity() {
    var productCount : Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val productinfo = intent.getParcelableExtra<ProductDTO>("ProductInfo")

        Glide.with(this).load(productinfo?.url).into(product_detail_image)
        product_detail_name_text.setText(productinfo?.product_name)
        product_detail_price_text.setText(productinfo?.product_price.toString() + "원")
        product_detail_content_text.setText(productinfo?.product_content)
        product_detail_location_text.setText(productinfo?.producer_location)
        product_detail_avgreview_text.setText(productinfo?.avg_review)
        product_detail_nutrient_text.setText(productinfo?.nutrient)

        for(basket in App.BasketDTOs){
            if (basket.product_name == productinfo?.product_name){
                productCount = basket.product_count!!.toInt()
                product_detail_count_text.text = productCount.toString()
            }
        }

        product_detail_minus_btn.setOnClickListener {
            productCount = max(productCount-1, 1)
            product_detail_count_text.text = productCount.toString()
        }

        product_detail_plus_btn.setOnClickListener {
            productCount += 1
            product_detail_count_text.text = productCount.toString()
        }

        product_to_basket_btn.setOnClickListener {
            productinfo?.let { it1 -> orderProduct(it1, productCount) }
        }
    }

    fun orderProduct (productDTO: ProductDTO, count:Int){
        val checking = App.BasketDTOs.size
        var counter = 0
        for(basket in App.BasketDTOs){
            if (basket.product_number == productDTO.product_number){
                basket.product_count = count
                break
            }
            counter += 1
        }
        if (checking == counter){
            App.BasketDTOs.add(BasketDTO(productDTO.product_number, productDTO.product_name, count, productDTO.product_price, productDTO.url))
        }
        finish()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("toScreen", 1)
        this.finish()
        startActivity(intent)

    }

}