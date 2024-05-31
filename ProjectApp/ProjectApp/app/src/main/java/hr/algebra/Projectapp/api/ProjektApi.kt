package hr.algebra.Projectapp.api

import retrofit2.Call
import retrofit2.http.GET

const val API_URL = "https://jsonplaceholder.typicode.com/"

interface ProjectApi {

    @GET("posts")

    fun fetchItems()
            : Call<List<appItem>>

}