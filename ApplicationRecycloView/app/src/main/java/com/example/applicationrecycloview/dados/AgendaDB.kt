package com.example.applicationrecycloview.dados

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contato::class], version = 1, exportSchema = false)
abstract class AgendaDB: RoomDatabase() {
    abstract val contatoDao: ContatoDao

    companion object{
        @Volatile
        private var INSTANCE: AgendaDB? = null
        fun getInstance(context: Context): AgendaDB{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AgendaDB::class.java,
                        "agenda_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}