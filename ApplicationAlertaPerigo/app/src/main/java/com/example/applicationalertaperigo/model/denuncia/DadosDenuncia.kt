package com.example.applicationalertaperigo.model.denuncia

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.type.DateTime

@IgnoreExtraProperties
data class DadosDenuncia (
    val idUsuario: String? = null,
    val dateRegistro: String? = null,
    val latitude: String? = null,
    val longitude: String? = null,
    val descricao: String? = null,
    val titulo: String? = null
    ) {

        @Exclude
        fun toMap(): Map<String, Any?> {
            return mapOf(
                "idUsuario" to idUsuario,
                "dateRegistro" to dateRegistro,
                "latitude" to latitude,
                "longitude" to longitude,
                "descricao" to descricao,
                "titulo" to titulo
            )
        }
    }