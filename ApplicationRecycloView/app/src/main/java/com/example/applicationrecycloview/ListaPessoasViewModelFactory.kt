package com.example.applicationrecycloview

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.applicationrecycloview.dados.ContatoDao

class ListaPessoasViewModelFactory(
    private val dataSource: ContatoDao,
    private val application: Application
): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListaPessoasViewModel::class.java)) {
            return ListaPessoasViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}