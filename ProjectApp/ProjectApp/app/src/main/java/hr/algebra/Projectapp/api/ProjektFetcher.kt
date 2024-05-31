package hr.algebra.Projectapp.api

import android.content.ContentValues
import android.content.Context
import android.util.Log
import hr.algebra.Projectapp.PROVIDER_CONTENT_URI
import hr.algebra.Projectapp.ProjektReceiver
import hr.algebra.Projectapp.framework.sendBroadcast
import hr.algebra.Projectapp.model.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProjektFetcher(private val context: Context) {

    private val projectApi: ProjectApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        projectApi = retrofit.create(ProjectApi::class.java)
    }

    //fun fetchItems(count: Int) {
    fun fetchItems() {

        //val request = nasaApi.fetchItems(count = 10)
        val request = projectApi.fetchItems()

        request.enqueue(object: Callback<List<appItem>> {
            override fun onResponse(
                call: Call<List<appItem>>,
                response: Response<List<appItem>>
            ) {

                response.body()?.let { populateItems(it) }
            }

            override fun onFailure(call: Call<List<appItem>>, t: Throwable) {
                Log.e(javaClass.name, t.toString(), t)
            }

        })



    }

    private fun populateItems(projectItems: List<appItem>) {
        // FOREGROUND - do not go to internet!!!
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            //BACKROUND
            projectItems.forEach {


                val values = ContentValues().apply {
                    put(Item::_id.name, it._id)
                    put(Item::_userId.name, it.`_userId`)
                    put(Item::title.name, it.title)
                    put(Item::body.name, it.body)
                    put(Item::read.name, false)
                }

                context.contentResolver.insert(PROVIDER_CONTENT_URI, values)

            }
            println(projectItems)
            context.sendBroadcast<ProjektReceiver>()
        }
    }
}