package applicationcadastroconta.infra

import androidx.room.*
import applicationcadastroconta.domain.Conta

@Dao
interface ContaDAO {
    @Query("SELECT * FROM contas ORDER BY nome")
    suspend fun listar(): List<Conta>

    @Query("SELECT * FROM contas WHERE nome LIKE :nome LIMIT 1")
    suspend fun  ObterPorNome(nome: String): Conta

    @Query("SELECT * FROM contas WHERE id = :id")
    suspend fun  ObterPorId(id: Int): Conta

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun  inserir(vararg conta: Conta)

    @Update
    suspend fun atualizar(conta: Conta)

    @Delete
    suspend fun excluir(conta: Conta)

}