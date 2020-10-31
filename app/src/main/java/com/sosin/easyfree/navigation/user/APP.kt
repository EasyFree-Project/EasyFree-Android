package com.sosin.easyfree.navigation.user

import android.app.Application
import android.content.Context
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
        private lateinit var context: Context

        fun setContext(con: Context) {
            context=con
        }
        lateinit var INSTANCE: App
        var uid : Int = -1
        var category_number : String = "0"
        var ProductDTOs = ArrayList<ProductDTO>()
        var BasketDTOs = ArrayList<BasketDTO>()

    }
}