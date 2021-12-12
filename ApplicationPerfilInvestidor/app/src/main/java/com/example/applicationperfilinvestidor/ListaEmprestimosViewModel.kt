package com.example.applicationperfilinvestidor

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applicationperfilinvestidor.dados.Emprestimo
import com.example.applicationperfilinvestidor.dados.EmprestimoDao
import kotlinx.coroutines.launch

class ListaEmprestimosViewModel(val dataSource: EmprestimoDao, application: Application): ViewModel() {

    val emprestimos = dataSource.obterTodos()

    fun incluir(emprestimo: Emprestimo){
        viewModelScope.launch {
            dataSource.insert(emprestimo)
        }
    }
}