package com.example.applicationalertaperigo.model.login

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class DadosPessoa(
    val nome: String? = null,
    val sobrenome: String? = null,
    val email: String? = null,
    val cep: String? = null,
    val estado: String? = null,
    val cidade: String? = null,
    val bairro: String? = null,
    val logradouro: String? = null,
    val numero: String? = null,
    val complemento: String? = null
)

{

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "nome" to nome,
            "sobrenome" to sobrenome,
            "email" to email,
            "cep" to cep,
            "estado" to estado,
            "cidade" to cidade,
            "bairro" to bairro,
            "logradouro" to logradouro,
            "numero" to numero,
            "complemento" to complemento,
        )
    }
}