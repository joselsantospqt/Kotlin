package com.example.applicationloginfirebase.model

import com.squareup.moshi.Json

data class Endereco(
    @Json(name = "cep") val cep: String,
    @Json(name = "uf") val estado: String,
    @Json(name = "localidade") val cidade: String,
    @Json(name = "bairro") val bairro: String,
    @Json(name = "logradouro") val logradouro: String
)
