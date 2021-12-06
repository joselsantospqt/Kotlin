package applicationcadastroconta.infra

import android.app.Application
import androidx.room.Room
import applicationcadastroconta.domain.Conta
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

    fun listarContasLiveData(): List<Conta> {
        return runBlocking {
            return@runBlocking dao.Listar()
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
}