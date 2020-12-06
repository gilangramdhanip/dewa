package com.project.dewa.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.dewa.api.ServiceBuilder
import com.project.dewa.model.BarangResponse
import com.project.dewa.model.ModelBarang
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModelUmkm : AndroidViewModel(Application()) {

    private val destination =  MutableLiveData<ArrayList<ModelBarang>>()
    private val apiService = ServiceBuilder.create()

    fun setBarang(){
        val dataDestination = ArrayList<ModelBarang>()
        apiService.getbarangList().enqueue(object :
            Callback<BarangResponse> {
            override fun onFailure(call: Call<BarangResponse>, t: Throwable) {
                Log.d("FailureLog", t.toString())
            }

            override fun onResponse(call: Call<BarangResponse>, response: Response<BarangResponse>) {

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

    fun getBarang(): LiveData<ArrayList<ModelBarang>> {
        return destination
    }





}