package com.example.applicationrecycloview.dados

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContatoDao {
    @Insert
    suspend fun insert(contato: Contato)
    @Update
    suspend fun alterar(contato: Contato)
    @Delete
    suspend fun excluir(contato: Contato)
    @Query("SELECT * FROM tabela_contatos WHERE idContato = :key")
    suspend fun obter(key: Long): Contato
    @Query("SELECT * FROM tabela_contatos ORDER BY nome_pessoa")
    fun obterTodos(): LiveData<List<Contato>>

}