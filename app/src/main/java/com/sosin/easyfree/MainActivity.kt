package com.sosin.easyfree

import android.Manifest
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sosin.easyfree.navigation.BasketFragment
import com.sosin.easyfree.navigation.CameraFragment
import com.sosin.easyfree.navigation.ProductViewFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation.setOnNavigationItemSelectedListener(this)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        var toscreen = intent.getIntExtra("toScreen", 0)

        if (toscreen == 0) {
            bottom_navigation.selectedItemId = R.id.action_list
        } else if (toscreen == 1) {
            bottom_navigation.selectedItemId = R.id.action_basket
        }

    }
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when(p0.itemId){
            R.id.action_list -> {
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