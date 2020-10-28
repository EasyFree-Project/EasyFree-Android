package com.sosin.easyfree

import android.Manifest
import android.graphics.Bitmap
import android.graphics.Camera
import android.os.Bundle
import android.os.Environment
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.otaliastudios.cameraview.CameraView
import com.sosin.easyfree.navigation.BasketFragment
import com.sosin.easyfree.navigation.CameraFragment
import com.sosin.easyfree.navigation.DetailViewFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_camera.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation.setOnNavigationItemSelectedListener(this)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        val uid = intent.getStringExtra("uid")

        Toast.makeText(this, uid, Toast.LENGTH_LONG).show()

//        Set default screen
        bottom_navigation.selectedItemId = R.id.action_home
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