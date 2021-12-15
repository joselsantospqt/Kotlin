package com.example.applicationperfilinvestidor.dados

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabela_Emprestimos")
data class Emprestimo(
    @PrimaryKey(autoGenerate = true)
    var idEmprestimo: Long = 0L,
    @ColumnInfo(name="nome_pessoa")
    var nome: String,
    @ColumnInfo(name="valor")
    var valor: String,
    @ColumnInfo(name="status")
    var status: String
)