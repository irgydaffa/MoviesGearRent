package com.example.moviesgearrent.data

import com.google.gson.annotations.SerializedName

class StatusDataWrapper (
    @SerializedName("data")
        var statusData: StatusData
)

class StatusData(
    val status: String
)