package com.example.applicationalertaperigo.network

import com.example.applicationalertaperigo.model.login.EnderecoPessoa
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


private const val BASE_URL =
    "https://viacep.com.br/ws/"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface EnderecoApiService {
    @GET("{cep}/json/")
    suspend fun getEndereco(@Path("cep")  cep: String): EnderecoPessoa
}

object EnderecoApi {
    val retrofitService : EnderecoApiService by lazy {
        retrofit.create(EnderecoApiService::class.java)
    }
}