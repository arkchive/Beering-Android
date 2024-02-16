package com.example.beering.feature.auth.join.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TermViewModel : ViewModel() {
    private val checkBoxList = ArrayList<Boolean>()
    private val _checkBoxes = MutableLiveData<ArrayList<Boolean>>()
    val checkBoxes: LiveData<ArrayList<Boolean>> = _checkBoxes
    private val _curTermIndex = MutableLiveData<Int>()
    val curTermIndex : LiveData<Int> = _curTermIndex

    init{
        for (i in 0 .. 2){
            checkBoxList.add(false)
        }
    }

    fun toggleCheckBox(index : Int){
        checkBoxList[index] = !checkBoxList[index]
        _checkBoxes.value = checkBoxList
    }

    fun agreeTerm(){
        checkBoxList[curTermIndex.value!!] = !checkBoxList[curTermIndex.value!!]
        _checkBoxes.value = checkBoxList
    }

    fun checkAll(checked : Boolean){
        for(i in 0..2){
            checkBoxList[i] = checked
        }
        _checkBoxes.value = checkBoxList
    }

    fun setTermIndex(index : Int){
        _curTermIndex.value = index
    }
}