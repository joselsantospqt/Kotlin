package applicationcadastroconta.infra

import androidx.lifecycle.LiveData
import androidx.room.*
import applicationcadastroconta.domain.Conta
import kotlinx.coroutines.flow.Flow

@Dao
interface ContaDAO {
    @Query("SELECT * FROM contas ORDER BY nome")
    fun Listar(): LiveData<List<Conta>>

    @Query("SELECT * FROM contas WHERE id = :id")
    fun ObterPorId(id: Int): Conta

    @Query("SELECT * FROM contas")
    fun ListarLiveData(): Flow<List<Conta>>

    @Query("SELECT * FROM contas WHERE nome LIKE :nome LIMIT 1")
    fun ObterPorNome(nome: String): Conta

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun Inserir(vararg conta: Conta)

    @Update
    fun Atualizar(conta: Conta)

    @Delete
    fun Excluir(conta: Conta)

}