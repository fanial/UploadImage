package com.fal.uploadimage.network

import com.fal.uploadimage.model.DataUserItem
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("/auth/register")
    @Multipart
    fun regist(
        @Part("address") address : RequestBody,
        @Part("city") city : RequestBody,
        @Part("email") email: RequestBody,
        @Part("fullName") fullName : RequestBody,
        @Part image : MultipartBody.Part,
        @Part("password") password : RequestBody,
        @Part("phoneNumber") phoneNumber : RequestBody
    ) : Call<DataUserItem>
}