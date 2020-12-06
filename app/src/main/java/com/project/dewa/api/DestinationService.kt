package com.project.dewa.api

import com.project.dewa.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DestinationService {

    @GET("destination")
    fun getDestinationList(): Call<DestinationResponse>

    @GET("destination")
    fun getDestinationbyId(
        @Query ("id_wisatawan") id_wisatawan : String
    ): Call<DestinationResponse>

    @GET("destination")
    fun getFilterKategori(@Query("id_kategori") id_kategori: String): Call<DestinationResponse>

    @GET("barang")
    fun getbarangList(): Call<BarangResponse>

    @GET("barang")
    fun getBarangListbyId(
        @Query ("id_wisatawan") id_wisatawan : String,
        @Query("id_kategori") id_kategori: String
    ): Call<BarangResponse>

    @GET("barang")
    fun getKecamatanBarangbyid(
        @Query("id_kabupaten") id_kabupaten: String,
        @Query("id_kecamatan") id_kecamatan: String): Call<KecamatanResponse>

    @GET("barang")
    fun getFilterKategoriBarang(@Query("id_kategori") id_kategori: String): Call<BarangResponse>


    @GET("kabupaten")
    fun getKabupaten(): Call<KabupatenResponse>

    @GET("kategori")
    fun getKategori(): Call<KategoriResponse>

    @GET("kecamatan")
    fun getKecamatan(@Query("id_kabupaten") id_kabupaten: String): Call<KecamatanResponse>

    @GET("destination")
    fun getKecamatanbyid(
        @Query("id_kabupaten") id_kabupaten: String,
        @Query("id_kecamatan") id_kecamatan: String): Call<KecamatanResponse>


}