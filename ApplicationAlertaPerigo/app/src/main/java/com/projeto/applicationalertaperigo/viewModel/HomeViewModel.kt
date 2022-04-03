package com.projeto.applicationalertaperigo.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projeto.applicationalertaperigo.model.login.DadosPessoa
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeViewModel : ViewModel() {


    val _dadosPessoa: MutableLiveData<DadosPessoa> by lazy {
        MutableLiveData<DadosPessoa>()
    }

    var dadosPessoa: LiveData<DadosPessoa> = _dadosPessoa

    fun setData(newData: DadosPessoa) {
        _dadosPessoa.postValue(newData)
    }

    fun CarregaDadosUsuario(id: String) {
        val nomeCollection = "Usuario"
        val db = Firebase.firestore
        val retorno = db.collection(nomeCollection).document(id).get().addOnSuccessListener {
            val obj = it.toObject<DadosPessoa>()
            _dadosPessoa.postValue(obj)
        }.addOnFailureListener { exception ->
            Log.d("Carga de Dados", "Error getting documents: ", exception)
        }
    }

    //NAVEGAÇÃO DA ACTIVITY ENTRE FRAGMENTS
    //RASCUNHO DE COMO VAI FUNCIONAR A NÚMERAÇÃO DA PÁGINAÇÃO
    //N° PAGINAÇÃO:
    // 0 = DEFAULT
    // 1 = HOME
    // 2 = PERFIL
    // 3 = CADASTRAR DENUNCIA
    // 4 = LISTAR DENUNCIA
    // 5 = DIRETORIO DE ARQUIVOS

    private val _trocaFragment = MutableLiveData<Int>(null)
    var trocaFragment: LiveData<Int> = _trocaFragment

    fun setTrocaFragment(id: Int) {
        _trocaFragment.postValue(id)
    }

    fun NavegaFragment(id: Int) {
        setTrocaFragment(id)
    }

}