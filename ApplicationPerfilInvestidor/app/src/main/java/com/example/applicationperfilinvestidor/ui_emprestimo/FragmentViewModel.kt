package com.example.applicationperfilinvestidor.ui_emprestimo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FragmentViewModel: ViewModel() {

    val data  = MutableLiveData<ArrayList<String>>()

    fun setData(newData: ArrayList<String>){
        data.value = newData
    }

}