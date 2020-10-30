package com.sosin.easyfree.navigation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.sosin.easyfree.R
import com.sosin.easyfree.navigation.model.BasketDTO
import com.sosin.easyfree.navigation.model.ProductDTO
import com.sosin.easyfree.navigation.user.App
import kotlinx.android.synthetic.main.fragment_basket.*
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONStringer
import org.w3c.dom.Text
import kotlin.math.max

class BasketFragment: Fragment(){
    val TAG = "BASKET"
    var total_price : Int = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_basket,container,false)
        view.findViewById<RecyclerView>(R.id.basketviewfragment_recyclerview).adapter = BasketViewRecyclerViewAdapter()
        view.findViewById<RecyclerView>(R.id.basketviewfragment_recyclerview).layoutManager = LinearLayoutManager(activity)

        view.findViewById<Button>(R.id.order_to_server_btn).setOnClickListener {
            CustomerOrder()
        }

        view.findViewById<Button>(R.id.delete_item_btn).setOnClickListener {
            App.BasketDTOs.clear()
            totalCostChanged()
            total_price_value_text.text = total_price.toString()
        }
        return view
    }

    fun CustomerOrder(){
        var data : JSONObject = JSONObject()
        data.put("member_idx", App.uid)

        var basket_list : JSONArray = JSONArray()
        for (basket in App.BasketDTOs){
            var temp : JSONObject = JSONObject()
            temp.put("product_number", basket.product_number)
            temp.put("product_count", basket.product_count)
            basket_list.put(temp)
        }
        data.put("data", basket_list)

        var queue = Volley.newRequestQueue(activity)
        var jr = JsonObjectRequest(
            Request.Method.POST,
            getString(R.string.purchase_url),
            data,
            Response.Listener<JSONObject> { response ->
                try {
                    var res = response.getInt("statusCode")
                    if (res == 200){
                        Toast.makeText(activity, "주문 성공 !", LENGTH_LONG).show()
                    }
                    else if(res == 500){
                        Toast.makeText(activity, "주문 실패..", LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Log.d(TAG, "product fail")
                }
            },
            Response.ErrorListener { error ->
                VolleyLog.e(TAG, "/post request fail! Error: ${error.message}")
            })
        queue.add(jr)
        App.BasketDTOs.clear()
        totalCostChanged()
    }
    fun totalCostChanged(){
        total_price = 0
        basketviewfragment_recyclerview.adapter?.notifyDataSetChanged()
    }

    inner class BasketViewRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var basketDTOs: ArrayList<BasketDTO> = arrayListOf()

        init {
            basketDTOs = App.BasketDTOs
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view =
                LayoutInflater.from(parent.context).inflate(R.layout.basket_detail, parent, false)
            return CustomViewHolder(view)
        }

        inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view)

        override fun getItemCount(): Int {
            return basketDTOs.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as CustomViewHolder).itemView

            Glide.with(holder.itemView.context).load(basketDTOs!![position].product_url).into(viewHolder.findViewById(R.id.basketitem_imageview))

            //Product Name
            viewHolder.findViewById<TextView>(R.id.basket_item_text).text =
                basketDTOs!![position].product_name
            viewHolder.findViewById<ImageView>(R.id.delete_one_item_btn).setOnClickListener {
                basketDTOs.removeAt(position)
                totalCostChanged()
            }
            //Product Count
            viewHolder.findViewById<TextView>(R.id.product_count_text).text =
                basketDTOs!![position].product_count.toString()

            //Product Price
            var item_price = basketDTOs!![position].product_price!!.toInt() * basketDTOs!![position].product_count!!.toInt()
            viewHolder.findViewById<TextView>(R.id.one_item_price_text).text = item_price.toString()
            total_price += item_price

            viewHolder.findViewById<ImageView>(R.id.basket_item_plus_btn).setOnClickListener {
                App.BasketDTOs!![position].product_count = App.BasketDTOs!![position].product_count?.plus(1)
                totalCostChanged()
            }

            viewHolder.findViewById<ImageView>(R.id.basket_item_minus_btn).setOnClickListener {
                App.BasketDTOs!![position].product_count =
                    App.BasketDTOs!![position].product_count?.minus(1)?.let { it1 -> max(1, it1) }
                totalCostChanged()
            }
            total_price_value_text.text = total_price.toString()
        }

    }

}