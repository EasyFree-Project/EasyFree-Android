package com.sosin.easyfree.navigation.user

import android.app.Application
import com.sosin.easyfree.navigation.model.BasketDTO
import com.sosin.easyfree.navigation.model.ProductDTO

class App : Application() {

    init {
        INSTANCE = this
    }

    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        lateinit var INSTANCE: App
        var BasketDTOs = ArrayList<BasketDTO>() // 데이터 나중에 받아오기
        var ProductDTOs = ArrayList<ProductDTO>() // 데이터 나중에 받아오기
    }
}