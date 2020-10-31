package com.sosin.easyfree

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sosin.easyfree.navigation.BasketFragment
import com.sosin.easyfree.navigation.CameraFragment
import com.sosin.easyfree.navigation.ProductViewFragment
import com.sosin.easyfree.navigation.model.ProductDTO
import com.sosin.easyfree.navigation.user.App
import kotlinx.android.synthetic.main.activity_main.*
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


        if(tobasket==0){
            bottom_navigation.selectedItemId = R.id.action_home
        }else{
            bottom_navigation.selectedItemId = R.id.action_basket
        }
    }
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when(p0.itemId){
            R.id.action_home -> {
                var productViewFragment = ProductViewFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, productViewFragment).commit()
                return true
            }
            R.id.action_camera -> {
                var cameraFragment = CameraFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, cameraFragment).commitAllowingStateLoss()
                return true
            }
            R.id.action_basket -> {
                var basketFragment = BasketFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, basketFragment).commit()
                return true
            }
        }
        return false
    }

}