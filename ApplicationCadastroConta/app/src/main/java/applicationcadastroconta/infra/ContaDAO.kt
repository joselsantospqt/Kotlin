package applicationcadastroconta.infra

import androidx.room.*
import applicationcadastroconta.domain.Conta

@Dao
interface ContaDAO {
    @Query("SELECT * FROM contas ORDER BY nome")
    suspend fun Listar(): List<Conta>

    @Query("SELECT * FROM contas WHERE nome LIKE :nome LIMIT 1")
    suspend fun  ObterPorNome(nome: String): Conta

    @Query("SELECT * FROM contas WHERE id = :id")
    suspend fun  ObterPorId(id: Int): Conta

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun  Inserir(vararg conta: Conta)

    @Update
    suspend fun Atualizar(conta: Conta)

    @Delete
    suspend fun Excluir(conta: Conta)

}