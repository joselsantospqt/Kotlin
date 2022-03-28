package com.example.applicationalertaperigo.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.applicationalertaperigo.model.login.DadosPessoa
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class HomeViewModel : ViewModel() {
    var uid: String = ""

    private val _nome = MutableLiveData("")
    var nome: LiveData<String> = _nome

    fun setNome(nome: String) {
        _nome.postValue(nome)
    }

    private val _sobreNome = MutableLiveData("")
    var sobreNome: LiveData<String> = _sobreNome

    fun setSobreNome(sobreNome: String) {
        _sobreNome.postValue(sobreNome)
    }

    private val _email = MutableLiveData("")
    var email: LiveData<String> = _email

    fun setEmail(email: String) {
        _email.postValue(email)
    }

    private val _senha = MutableLiveData("")
    var senha: LiveData<String> = _senha

    fun setSenha(senha: String) {
        _senha.postValue(senha)
    }

    private val _confirmaSenha = MutableLiveData("")
    var confirmaSenha: LiveData<String> = _confirmaSenha

    fun setConfirmaSenha(confirmaSenha: String) {
        _confirmaSenha.postValue(confirmaSenha)
    }

    private val _CEP = MutableLiveData("")
    var cep: LiveData<String> = _CEP

    fun setCEP(cep: String) {
        _CEP.postValue(cep)
    }

    private val _estado = MutableLiveData("")
    var estado: LiveData<String> = _estado

    fun setEstado(estado: String) {
        _estado.postValue(estado)
    }

    private val _bairro = MutableLiveData("")
    var bairro: LiveData<String> = _bairro

    fun setBairro(bairro: String) {
        _bairro.postValue(bairro)
    }

    private val _logradouro = MutableLiveData("")
    var logradouro: LiveData<String> = _logradouro

    fun setLogradouro(logradouro: String) {
        _logradouro.postValue(logradouro)
    }

    private val _numero = MutableLiveData("")
    var numero: LiveData<String> = _numero

    fun setNumero(numero: String) {
        _numero.postValue(numero)
    }

    private val _cidade = MutableLiveData("")
    var cidade: LiveData<String> = _cidade

    fun setCidade(cidade: String) {
        _cidade.postValue(cidade)
    }

    private val _complemento = MutableLiveData("")
    var complemento: LiveData<String> = _complemento

    fun setComplemento(complemento: String) {
        _complemento.postValue(complemento)
    }

    fun CarregaDadosUsuario(id : String) {
        val nomeCollection = "Usuario"
        val db = Firebase.firestore
        val retorno = db.collection(nomeCollection).document(id)
    }

}