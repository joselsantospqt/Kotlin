package com.example.applicationperfilinvestidor.dados

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EmprestimoDao {
    @Insert
    suspend fun insert(emprestimo: Emprestimo)
    @Update
    suspend fun alterar(emprestimo: Emprestimo)
    @Delete
    suspend fun excluir(emprestimo: Emprestimo)
    @Query("SELECT * FROM tabela_emprestimos WHERE idEmprestimo = :key")
    fun obter(key: Long): Emprestimo
    @Query("SELECT * FROM tabela_emprestimos ORDER BY nome_pessoa")
    fun obterTodos(): LiveData<List<Emprestimo>>
}