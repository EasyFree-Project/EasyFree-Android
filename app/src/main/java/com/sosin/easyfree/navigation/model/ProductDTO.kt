package com.sosin.easyfree.navigation.model

// 받는 데이터 형식 정의
data class ProductDTO(var product_name:String? = null,
                      var product_content: String? = null,
                      var producer_location: String? = null,
                      var capacity_size:String? = null,
                      var nutrient:String? = null,
                      var product_price:Int? = 0,
                      var avg_review:String? = null,
                      var review_count:Int? = 0,
                      var category_number : String? = null)
//                      var favorites:Map<String, Boolean> =HashMap())
{
    data class Comment(
        var uid: String? = null,
        var userid: String? = null,
        val comment: String? = null,
        var timestamp: Long? = null
    )
}