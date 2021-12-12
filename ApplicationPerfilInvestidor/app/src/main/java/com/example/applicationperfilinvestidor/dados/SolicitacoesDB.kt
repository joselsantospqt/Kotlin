package com.example.applicationperfilinvestidor.dados

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Emprestimo::class], version = 1, exportSchema = false)
abstract class SolicitacoesDB: RoomDatabase() {
    abstract val emprestimoDao: EmprestimoDao

    companion object{
        @Volatile
        private var INSTANCE: SolicitacoesDB? = null
        fun getInstance(context: Context): SolicitacoesDB{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SolicitacoesDB::class.java,
                        "solicitacoes_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}