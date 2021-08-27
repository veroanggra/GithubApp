package com.veronica.idn.githubapp

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class MainViewModel : ViewModel() {
    val listUser = MutableLiveData<ArrayList<Users>>()

    fun setListUser(context: Context, query: String? = null) {
        val users = ArrayList<Users>()

        //request ke server
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "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")
        client.addHeader("User-Agent", "request")

        val url = when (query) {
            "" -> "https://api.github.com/users"
            else -> "https://api.github.com/search/users?q=$query"
        }

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val jsonArray = when (query) {
                        "" -> JSONArray(result)
                        else -> JSONObject(result).getJSONArray("items")
                    }
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                TODO("Not yet implemented")
            }

        })

    }
}