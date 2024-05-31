package hr.algebra.Projectapp.api

import com.google.gson.annotations.SerializedName

data class appItem(
    @SerializedName("userId") val _userId : String,
    @SerializedName("id") val _id : String,
    @SerializedName("title") val title : String,
    @SerializedName("body") val body : String


)
