package com.sosin.easyfree.navigation.model

// 받는 데이터 형식 정의
data class BasketDTO(
                     var product_number:String? = null,
                     var product_name:String? = null,
                     var product_count: Int? = 0,
                     var product_price:Int? = 0,
                     var product_url :String? =null
                    )