package com.projeto.applicationalertaperigo.viewModel

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projeto.applicationalertaperigo.model.login.DadosPessoa
import com.projeto.applicationalertaperigo.network.EnderecoApi
import com.projeto.applicationalertaperigo.ui.login.LoginActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

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
    private val _confirmaCadastro = MutableLiveData(false)
    var confirmaCadastro: LiveData<Boolean> = _confirmaCadastro

    fun setConfirmaCadastro(confirmaCadastro: Boolean) {
        _confirmaCadastro.postValue(confirmaCadastro)
    }
    private val _atualizaEndereco = MutableLiveData<Int>(0)
    var atualizaEndereco: LiveData<Int> = _atualizaEndereco

    fun setAtualizaEndereco() {
        _atualizaEndereco.value = _atualizaEndereco.value?.plus(1)
    }

    fun verificacaoDadosPessoa(): Boolean {
        return (senha.value?.length!! > 5 &&
                email.value?.length!! > 5 &&
                nome.value?.length!! > 2 &&
                sobreNome.value?.length!! > 2 &&
                confirmaSenha.value?.length == senha.value?.length
                )
    }

    fun verificacaoDadosEndereco(): Boolean {
        return (cep.value?.length!! > 7 &&
                estado.value?.length!! >= 2 &&
                bairro.value?.length!! > 2 &&
                logradouro.value?.length!! > 2 &&
                cidade.value?.length!! > 2 &&
/*                numero.value?.length!! > 2 &&*/
                complemento.value?.length!! > 2
                )
    }

    fun getEndereco() {
        viewModelScope.launch(Dispatchers.IO) {
            val enderecoJson = EnderecoApi.retrofitService.getEndereco(cep.value.toString())
            Log.i("API", enderecoJson.toString())

            setEstado(enderecoJson.estado)
            setCidade(enderecoJson.cidade)
            setBairro(enderecoJson.bairro)
            setLogradouro(enderecoJson.logradouro)

            viewModelScope.launch(Dispatchers.Main) {
                setAtualizaEndereco()
            }

        }
    }

    fun cadastroFireBase() {

        var auth = Firebase.auth
        auth.createUserWithEmailAndPassword(
            email.value.toString(),
            senha.value.toString()
        )
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    uid = user?.uid!!
                    cadastroFirestore()
                    Log.i("cadastro", "Sucesso ao cadastrar", task.exception)
                } else
                    Log.i("cadastro", "Falha ao cadastrar", task.exception)
            }
    }

    fun cadastroFirestore() {
        var registro = DadosPessoa(
            nome.value.toString(),
            sobreNome.value.toString(),
            email.value.toString(),
            cep.value.toString(),
            estado.value.toString(),
            bairro.value.toString(),
            logradouro.value.toString(),
            numero.value.toString(),
            complemento.value.toString(),
            cidade.value.toString()
        )
        val nomeCollection = "Usuario"
        val db = Firebase.firestore

        db.collection(nomeCollection)
            .document(uid)
            .set(registro)
            .addOnSuccessListener { document ->
                Log.i("Cadastrar", "Document: Id: ${uid}")
                setConfirmaCadastro(true)
            }.addOnFailureListener {
                Log.i("Cadastrar", "Falha ao Registrar")
            }
    }
}