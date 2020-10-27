package com.sosin.easyfree.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sosin.easyfree.R
import com.sosin.easyfree.navigation.model.BasketDTO

class BasketFragment: Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_basket,container,false)
        view.findViewById<RecyclerView>(R.id.basketviewfragment_recyclerview).adapter = BasketViewRecyclerViewAdapter()
        view.findViewById<RecyclerView>(R.id.basketviewfragment_recyclerview).layoutManager = LinearLayoutManager(activity)
        return view
    }
    inner class BasketViewRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var basketDTOs: ArrayList<BasketDTO> = arrayListOf()

        init {

            var item = BasketDTO("까르보 불닭볶음면", 1, 1500)

            var range = arrayOf(1,2,3,4,5)
            for(i in range){
                basketDTOs.add(item!!)
            }

            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_detail, parent, false)
            return CustomViewHolder(view)
        }

        inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view)

        override fun getItemCount(): Int {
            return basketDTOs.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as CustomViewHolder).itemView
            //Product Name
            viewHolder.findViewById<TextView>(R.id.basket_item_text).text =
                basketDTOs!![position].product_name

            //Product Count
            viewHolder.findViewById<TextView>(R.id.product_count_text).text =
                basketDTOs!![position].product_count.toString()

            //Product Price
            viewHolder.findViewById<TextView>(R.id.one_item_price_text).text =
                basketDTOs!![position].product_price.toString()
        }
    }
}