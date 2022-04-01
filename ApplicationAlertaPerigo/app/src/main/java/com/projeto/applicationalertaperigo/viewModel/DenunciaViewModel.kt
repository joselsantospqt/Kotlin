package com.projeto.applicationalertaperigo.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projeto.applicationalertaperigo.model.denuncia.DadosDenunciaComFoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DenunciaViewModel : ViewModel() {

    val _itemDenuncia: MutableLiveData<DadosDenunciaComFoto> by lazy {
        MutableLiveData<DadosDenunciaComFoto>()
    }

    var itemDenuncia: LiveData<DadosDenunciaComFoto> = _itemDenuncia

    fun setData(newData: DadosDenunciaComFoto) {
        _itemDenuncia.postValue(newData)
    }

    private val _atualizaReward = MutableLiveData<Int>(0)
    var atualizaReward: LiveData<Int> = _atualizaReward

    fun setAtualizaReward(number: Int) {
        _atualizaReward.value = number
    }

    fun ativaReward(number: Int) {
            setAtualizaReward(number)
    }

}