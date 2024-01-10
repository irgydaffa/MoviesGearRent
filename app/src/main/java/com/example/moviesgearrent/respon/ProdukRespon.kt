package com.example.moviesgearrent.respon

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

class ProdukRespon {
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("attributes")
    var attribute: ProdukAttribute? = null
}

class ProdukAttribute {
    @SerializedName("desc_produk")
    var desc_produk: String? = ""
    @SerializedName("nama_produk")
    var nama_produk: String? = ""
    @SerializedName("foto_produk")
    var foto_produk: ApiResponse<FotoResponse>? = null
    @SerializedName("status")
    var status : String? = ""
    @SerializedName("created_at")
    var created_at : String? = ""
    @SerializedName("updated_at")
    var updated_at : String? = ""
    @SerializedName("harga")
    var harga : Int? = null
    @SerializedName("deleted_at")
    var users_permission_user: UserRespon = UserRespon()
}

data class ApiResponse<T>(
    @SerializedName("data")
    val data: T? = null
)

class FotoResponse {
    @SerializedName("id")
    var id: Int = 0

    @SerializedName("attributes")
    var attributes: FotoAttributes = FotoAttributes()
}

class FotoAttributes {
    @SerializedName("name")
    var name: String = ""
    @SerializedName("url")
    var url : String = ""
}