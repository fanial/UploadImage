package com.fal.uploadimage.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fal.uploadimage.model.DataUserItem
import com.fal.uploadimage.network.ApiClient
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel(){
    var user : MutableLiveData<DataUserItem?> = MutableLiveData()

    fun liveDataUser () : MutableLiveData<DataUserItem?>{
        return user
    }

    fun postUser(address : RequestBody, city : RequestBody, email : RequestBody, fullName : RequestBody, image : MultipartBody.Part, password : RequestBody, phoneNumber : RequestBody){
        ApiClient.instance.regist(address, city, email, fullName, image, password, phoneNumber)
            .enqueue(object : Callback<DataUserItem>{
                override fun onResponse(
                    call: Call<DataUserItem>,
                    response: Response<DataUserItem>,
                ) {
                    if (response.isSuccessful){
                        user.postValue(response.body())
                    }else{
                        user.postValue(null)
                        error(response.message())
                    }
                }

                override fun onFailure(call: Call<DataUserItem>, t: Throwable) {
                    user.postValue(null)
                    error(t)
                }

            })
    }
}