package com.fal.uploadimage.view

import android.content.ContentResolver
import android.content.ContentValues.TAG
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.fal.uploadimage.R
import com.fal.uploadimage.databinding.ActivityMainBinding
import com.fal.uploadimage.viewmodel.UserViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class MainActivity : AppCompatActivity() {

    //image upload
    private var imageMultipart : MultipartBody.Part? = null
    private var imageUri : Uri? = Uri.EMPTY
    private var imageFile : File? = null
    private lateinit var model: UserViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.image.setOnClickListener {
            getContent.launch("image/*")
        }

        binding.btnRegist.setOnClickListener {
            register()
        }
    }

    private fun register() {
        val address = binding.address.text.toString().toRequestBody("multipart/form-data".toMediaType())
        val city = binding.city.text.toString().toRequestBody("multipart/form-data".toMediaType())
        val email = binding.email.text.toString().toRequestBody("multipart/form-data".toMediaType())
        val fullName = binding.fullName.text.toString().toRequestBody("multipart/form-data".toMediaType())
        val password = binding.password.text.toString().toRequestBody("multipart/form-data".toMediaType())
        val phoneNumber = binding.phoneNumber.text.toString().toRequestBody("multipart/form-data".toMediaType())

        model.postUser(address, city, email, fullName, imageMultipart!!, password, phoneNumber)
        model.liveDataUser().observe(this){
            if (it != null){
                Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show()
            }else{
                Log.d(TAG, "register failed: $it")
            }
        }
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()){ uri : Uri? ->
            uri?.let {
                val contentResolver: ContentResolver = this!!.contentResolver
                val type = contentResolver.getType(it)
                imageUri = it

                val fileNameimg = "${System.currentTimeMillis()}.png"
                binding.image.setImageURI(it)
                Toast.makeText(this, "$imageUri", Toast.LENGTH_SHORT).show()

                val tempFile = File.createTempFile("and1-", fileNameimg, null)
                imageFile = tempFile
                val inputstream = contentResolver.openInputStream(uri)
                tempFile.outputStream().use    { result ->
                    inputstream?.copyTo(result)
                }
                val requestBody: RequestBody = tempFile.asRequestBody(type?.toMediaType())
                imageMultipart = MultipartBody.Part.createFormData("image", tempFile.name, requestBody)
            }
        }
}