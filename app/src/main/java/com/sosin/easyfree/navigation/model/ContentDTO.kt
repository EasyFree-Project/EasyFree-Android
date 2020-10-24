package com.sosin.easyfree.navigation.model

// 받는 데이터 형식 정의
data class ContentDTO(var explain:String? = null,
                      var imageUri: String? = null,
                      var uid: String? = null,
                      var userId:String? = null,
                      var timestamp:Long? = null,
                      var favoriteCount:Int = 0,
                      var favorites:Map<String, Boolean> =HashMap()){
    data class Comment(var uid : String? = null,
                       var userid:String?=null,
                       val comment:String?=null,
                       var timestamp:Long?=null)
}