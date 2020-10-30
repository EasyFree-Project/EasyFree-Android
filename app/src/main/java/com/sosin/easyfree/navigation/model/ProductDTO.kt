package com.sosin.easyfree.navigation.model

import android.os.Parcel
import android.os.Parcelable

// 받는 데이터 형식 정의
data class ProductDTO (
    var product_number:String? = null,
    var product_name:String? = null,
    var product_content: String? = null,
    var producer_location: String? = null,
    var capacity_size:String? = null,
    var nutrient:String? = null,
    var product_price:Int? = 0,
    var avg_review:String? = null,
    var review_count:Int? = 0,
    var url:String? = null,
    var category_number : String? = null) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(product_number)
        parcel.writeString(product_name)
        parcel.writeString(product_content)
        parcel.writeString(producer_location)
        parcel.writeString(capacity_size)
        parcel.writeString(nutrient)
        parcel.writeValue(product_price)
        parcel.writeString(avg_review)
        parcel.writeValue(review_count)
        parcel.writeString(url)
        parcel.writeString(category_number)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductDTO> {
        override fun createFromParcel(parcel: Parcel): ProductDTO {
            return ProductDTO(parcel)
        }

        override fun newArray(size: Int): Array<ProductDTO?> {
            return arrayOfNulls(size)
        }
    }
}