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
    var desc_produk: String? = null
    @SerializedName("nama_produk")
    var nama_produk: String? = null
    @SerializedName("foto_produk")
    var foto_produk: String? = null
    @SerializedName("status")
    var status : String? = null
    @SerializedName("created_at")
    var created_at : String? = null
    @SerializedName("updated_at")
    var updated_at : String? = null
    @SerializedName("harga")
    var harga : Integer? = null
    @SerializedName("deleted_at")
    var users_permission_user: UserRespon = UserRespon()
}