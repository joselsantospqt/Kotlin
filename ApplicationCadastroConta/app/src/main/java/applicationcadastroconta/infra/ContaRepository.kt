package applicationcadastroconta.infra

import android.app.Application
import androidx.room.Room
import applicationcadastroconta.domain.Conta
import kotlinx.coroutines.flow.Flow
 import kotlinx.coroutines.runBlocking

class ContaRepository(aplicationContext: Application) {

    private lateinit var dao: ContaDAO

    init {
        val db =
            Room.databaseBuilder(aplicationContext, AppDatabase::class.java, "db_contas").build()
        dao = db.getContaDAO()
    }

    fun obterPorId(id: Int): Conta {
        return runBlocking { return@runBlocking dao.ObterPorId(id) }
    }

    fun listarContasLiveData(): Flow<List<Conta>> {
        return runBlocking {
            return@runBlocking dao.ListarLiveData()
        }
    }

    fun salvarConta(conta: Conta) {
        return runBlocking {
            if (conta.id == 0)
                return@runBlocking dao.Inserir(conta)
            else
                return@runBlocking dao.Atualizar(conta)
        }
    }

    fun excluirConta(conta: Conta){
        return runBlocking {
            return@runBlocking dao.Excluir(conta)
        }
    }
}