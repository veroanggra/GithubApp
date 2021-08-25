package com.veronica.idn.githubapp

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient

class MainViewModel : ViewModel() {
    val listUser = MutableLiveData<ArrayList<Users>>()

    fun setListUser(context: Context, query: String? = null){
        val users = ArrayList<Users>()

        //request ke server
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "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")
        client.addHeader("User-Agent", "request")

        val url = when(query){
            "" -> "https://api.github.com/users"
            else -> "https://api.github.com/search/users?q=$query"
        }
    }
}