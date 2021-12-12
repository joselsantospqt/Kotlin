package com.example.applicationperfilinvestidor.ui_emprestimo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.applicationperfilinvestidor.dados.EmprestimoDao
import java.lang.IllegalArgumentException

class ListaEmprestimosViewModelFactory(
    private val dataSource: EmprestimoDao,
    private val application: Application
): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override  fun <T: ViewModel?> create(modelClass: Class<T>):T{
        if(modelClass.isAssignableFrom(ListaEmprestimosViewModel::class.java)){
            return ListaEmprestimosViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}