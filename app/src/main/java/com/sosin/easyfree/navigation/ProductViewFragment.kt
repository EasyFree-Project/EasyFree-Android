package com.sosin.easyfree.navigation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sosin.easyfree.ProductDetailActivity
import com.sosin.easyfree.R
import com.sosin.easyfree.navigation.model.ProductDTO
import com.sosin.easyfree.navigation.user.App
import kotlinx.android.synthetic.main.product_detail.view.*

class ProductViewFragment: Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_product, container, false)
        view.findViewById<RecyclerView>(R.id.fragment_product_recyclerview)?.adapter = ProductViewRecyclerViewAdapter()
        view.findViewById<RecyclerView>(R.id.fragment_product_recyclerview)?.layoutManager = GridLayoutManager(activity, 2)
        activity?.findViewById<TextView>(R.id.category_text)?.text = "상품이 비어있습니다."
        return view
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
                productDTOs!![position]?.let { it1 -> moveProductDetailPage(it1) }
            }

            var category_name = "상품이 비어있습니다."

            if (productDTOs[0].category_number == "6000095799") {
                category_name = "사과"
            } else if (productDTOs[0].category_number == "6000095829") {
                category_name = "수박"
            } else if (productDTOs[0].category_number == "6000095904") {
                category_name = "바나나"
            } else if (productDTOs[0].category_number == "6000095921") {
                category_name = "레몬"
            } else if (productDTOs[0].category_number == "6000096175") {
                category_name = "감자"
            } else if (productDTOs[0].category_number == "6000096206") {
                category_name = "호박"
            } else if (productDTOs[0].category_number == "6000096294") {
                category_name = "당근"
            } else if (productDTOs[0].category_number == "6000096220") {
                category_name = "깻잎"
            }

            activity?.findViewById<TextView>(R.id.category_text)?.text = category_name
        }

    }

    fun moveProductDetailPage(pn: ProductDTO) {
        var detailintent = Intent(activity, ProductDetailActivity::class.java)
        detailintent.putExtra("ProductInfo", pn)
        startActivity(detailintent)
    }
}