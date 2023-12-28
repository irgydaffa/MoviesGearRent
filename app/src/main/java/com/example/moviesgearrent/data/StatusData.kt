package com.example.moviesgearrent.data

import com.google.gson.annotations.SerializedName

class StatusDataWrapper (
    @SerializedName("data")
        var statusdata : StatusData
)

class StatusData(
    var status: String
)