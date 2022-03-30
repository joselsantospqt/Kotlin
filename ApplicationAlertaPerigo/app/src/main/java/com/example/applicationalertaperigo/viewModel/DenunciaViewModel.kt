package com.example.applicationalertaperigo.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.applicationalertaperigo.model.denuncia.DadosDenunciaComFoto

class DenunciaViewModel : ViewModel() {

    val _itemDenuncia: MutableLiveData<DadosDenunciaComFoto> by lazy {
        MutableLiveData<DadosDenunciaComFoto>()
    }

    var itemDenuncia: LiveData<DadosDenunciaComFoto> = _itemDenuncia

    fun setData(newData: DadosDenunciaComFoto) {
        _itemDenuncia.postValue(newData)
    }
}