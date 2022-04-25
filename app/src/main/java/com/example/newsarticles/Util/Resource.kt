package com.example.newsarticles.Util
sealed class Resource<T> (
    val data: T?=null,
    val message:String?=null)
{
    //classes that allowed to inherit of this class

    class Success<T>(data: T?):Resource<T>(data)
    class Error<T>(message:String,data: T?=null):Resource<T>(data,message)
    class Loading<T>:Resource<T>()


}