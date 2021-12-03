package applicationcadastroconta.infra

import androidx.room.Database
import androidx.room.RoomDatabase
import applicationcadastroconta.domain.Conta

@Database(entities = [Conta::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getContaDAO(): ContaDAO
}