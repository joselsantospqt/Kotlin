package com.example.applicationrecycloview.dados

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabela_contatos")
data class Contato(
    @PrimaryKey(autoGenerate = true)
    var idContato: Long = 0L,
    @ColumnInfo(name="nome_pessoa")
    var nome: String,
    @ColumnInfo(name="telefone")
    var telefone: String,
    @ColumnInfo(name="email")
    var email: String
)
