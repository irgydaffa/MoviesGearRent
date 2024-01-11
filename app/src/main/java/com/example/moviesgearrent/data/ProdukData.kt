package com.example.moviesgearrent.data

import com.google.gson.annotations.SerializedName

class ProdukDataWrapper (
    @SerializedName("data")
    var data: produk? = null
)

class produk (
    @SerializedName("nama_produk")
    var nama_produk: String? = null,

    @SerializedName("desc_produk")
    var desc_produk: String? = null,

    @SerializedName("harga")
    var harga: String? = null,

    @SerializedName("users_permissions_user")
    var users_permissions_user: String? = null
)