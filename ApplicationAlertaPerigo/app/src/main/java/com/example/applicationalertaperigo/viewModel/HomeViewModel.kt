package com.example.applicationalertaperigo.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.applicationalertaperigo.model.login.DadosPessoa
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class HomeViewModel : ViewModel() {

    val _dadosPessoa: MutableLiveData<DadosPessoa> by lazy {
        MutableLiveData<DadosPessoa>()
    }

    var dadosPessoa: LiveData<DadosPessoa> = _dadosPessoa

    fun setData(newData: DadosPessoa) {
        _dadosPessoa.postValue(newData)
    }

    fun CarregaDadosUsuario(id : String) {
        val nomeCollection = "Usuario"
        val db = Firebase.firestore
        val retorno = db.collection(nomeCollection).document(id).get().addOnSuccessListener {
            val obj = it.toObject<DadosPessoa>()
            _dadosPessoa.postValue(obj)
        }.addOnFailureListener { exception ->
            Log.d("Carga de Dados", "Error getting documents: ", exception)
        }
    }

}