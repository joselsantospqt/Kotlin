package com.projeto.applicationalertaperigo.model.denuncia

import android.graphics.Bitmap
import com.google.type.DateTime

class DadosDenunciaComFoto(
    val id: String? = null,
    val dateRegistro: String? = null,
    val latitude: String? = null,
    val longitude: String? = null,
    val descricao: String? = null,
    val titulo: String? = null,
    val foto: Bitmap? = null
)