package com.sosin.easyfree

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isGone
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sosin.easyfree.navigation.BasketFragment
import com.sosin.easyfree.navigation.CameraFragment
import com.sosin.easyfree.navigation.DetailViewFragment
import com.sosin.easyfree.navigation.model.BasketDTO
import com.sosin.easyfree.navigation.model.ProductDTO
import com.sosin.easyfree.navigation.user.App
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*
import org.json.JSONObject


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    val TAG = "Product"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation.setOnNavigationItemSelectedListener(this)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        var tobasket = intent.getIntExtra("toBasket", 0)
        capture_btn.visibility = View.GONE

        product_list_btn.setOnClickListener {
            getItemLists("6000095799")
        }

        if(tobasket==0){
            bottom_navigation.selectedItemId = R.id.action_home
        }else{
            bottom_navigation.selectedItemId = R.id.action_basket
        }
    }
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when(p0.itemId){
            R.id.action_home -> {
                var detailViewFragment = DetailViewFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, detailViewFragment).commit()
                return true
            }
            R.id.action_camera -> {
                var cameraFragment = CameraFragment()
                capture_btn.visibility = View.VISIBLE
                supportFragmentManager.beginTransaction().replace(R.id.main_content, cameraFragment).commitAllowingStateLoss()
                return true
            }
            R.id.action_basket -> {
                var basketFragment = BasketFragment()
                capture_btn.visibility = View.GONE
                supportFragmentManager.beginTransaction().replace(R.id.main_content, basketFragment).commit()
                return true
            }
        }
        return false
    }

    fun getItemLists(category_number : String){
        if(category_number != App.category_number){
            var queue = Volley.newRequestQueue(this)
            var jr = JsonObjectRequest(
                Request.Method.GET,
                getString(R.string.product_url) + category_number,
                null,
                Response.Listener<JSONObject> { response ->
                    try {
                        var items = response.getJSONObject("data").getJSONArray("item")
                        for (i in 0 until items.length()){
                            val item = items.getJSONObject(i)
                            val productDTO : ProductDTO = ProductDTO(item.getString("product_number"),
                                item.getString("product_name"),
                                item.getString("product_content"),
                                item.getString("producer_location"),
                                item.getString("capacity_size"),
                                item.getString("nutrient"),
                                item.getInt("product_price"),
                                item.getString("avg_review"),
                                item.getInt("review_count"),
                                item.getString("url"),
                                item.getString("category_number"))
                            App.ProductDTOs.add(productDTO)
                        }
                        moveProductPage()
                    } catch (e: Exception) {
                        Log.d(TAG, "product fail")

                    }
                },
                Response.ErrorListener { error ->
                    VolleyLog.e(TAG, "/post request fail! Error: ${error.message}")
                })

            queue.add(jr)
        }
        else{
            moveProductPage()
        }
    }

    fun moveProductPage(){
        startActivity(Intent(this, ProductActivity::class.java))
    }

}