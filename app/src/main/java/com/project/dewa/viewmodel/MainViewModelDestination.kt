package com.project.dewa.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.dewa.api.ServiceBuilder
import com.project.dewa.model.DestinationResponse
import com.project.dewa.model.ModelDestination
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModelDestination : AndroidViewModel(Application()) {

    private val destination =  MutableLiveData<ArrayList<ModelDestination>>()
    private val apiService = ServiceBuilder.create()

    fun setDestination(kategori : String){
        val dataDestination = ArrayList<ModelDestination>()
        apiService.getFilterKategori(kategori).enqueue(object :
            Callback<DestinationResponse> {
            override fun onFailure(call: Call<DestinationResponse>, t: Throwable) {
                Log.d("FailureLog", t.toString())
            }

            override fun onResponse(call: Call<DestinationResponse>, response: Response<DestinationResponse>) {

                if(response.isSuccessful){
                    val dataDes = response.body()
                    Log.d("ResponseLog", response.toString())
                    dataDestination.addAll(dataDes!!.data)
                    destination.postValue(dataDestination)
                }else{
                    Log.d("gagalresponse", response.toString())
//                    Toast.makeText(getApplication(), "Gagal", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    fun getDestination(): LiveData<ArrayList<ModelDestination>> {
        return destination
    }

}