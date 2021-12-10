package com.example.applicationrecycloview

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applicationrecycloview.dados.Contato
import com.example.applicationrecycloview.dados.ContatoDao
import kotlinx.coroutines.launch

class ListaPessoasViewModel(val dataSource: ContatoDao, application: Application) : ViewModel() {

   val contatos = dataSource.obterTodos()

   fun incluir(contato: Contato){
      viewModelScope.launch {
         dataSource.insert(contato)
      }
   }
}