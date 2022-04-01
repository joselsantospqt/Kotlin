package com.projeto.applicationalertaperigo.model.login

import com.squareup.moshi.Json

data class EnderecoPessoa (
@Json(name = "cep") val cep: String,
@Json(name = "uf") val estado: String,
@Json(name = "localidade") val cidade: String,
@Json(name = "bairro") val bairro: String,
@Json(name = "logradouro") val logradouro: String
)