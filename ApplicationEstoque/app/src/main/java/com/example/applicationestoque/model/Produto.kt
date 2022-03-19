package com.example.applicationestoque.model

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Produto (
    val nome: String? = null,
    val quantidade: Int? = null,
    val preco: Double? = null,
    val descricao: String? = null,
    //val uriFoto: String? = null

) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "nome" to nome,
            "quantidade" to quantidade,
            "preco" to preco,
            "descricao" to descricao
        )
    }
}