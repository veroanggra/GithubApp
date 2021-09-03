package com.veronica.idn.githubapp.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.veronica.idn.githubapp.model.Users
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailViewModel : ViewModel() {
    val detailUsers = MutableLiveData<Users>()

    fun setDetailUser(username: String, context: Context) {
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "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")
        client.addHeader("User-Agent", "request")

        val url = "https://api.github.com/users/$username"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val jSonObject = JSONObject(result)

                    val user = Users()
                    user.name = jSonObject.getString("name")
                    user.username = jSonObject.getString("login")
                    user.location = jSonObject.getString("location")
                    user.followers = jSonObject.getInt("followers")
                    user.following = jSonObject.getInt("following")
                    user.avatar = jSonObject.getString("avatar_url")
                    detailUsers.postValue(user)
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getDetailUser(): LiveData<Users> {
        return detailUsers
    }
}