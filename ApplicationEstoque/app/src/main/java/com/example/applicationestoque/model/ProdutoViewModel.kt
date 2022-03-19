package com.example.applicationestoque.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProdutoViewModel : ViewModel() {

    val _itemProduto: MutableLiveData<ProdutoComFoto> by lazy {
        MutableLiveData<ProdutoComFoto>()
    }

    var itemProduto: LiveData<ProdutoComFoto> = _itemProduto

    fun setData(newData: ProdutoComFoto) {
        _itemProduto.postValue(newData)
    }
}
