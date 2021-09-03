package com.veronica.idn.githubapp.model

data class Users(
    var username : String? = null,
    var name : String? = " ",
    var location : String? = " ",
    var followers : Int? = 0,
    var following : Int? = 0,
    var avatar : String? = null
)
