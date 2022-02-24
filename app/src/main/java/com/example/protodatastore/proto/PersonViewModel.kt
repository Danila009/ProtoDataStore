package com.example.protodatastore.proto

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonViewModel(
    application: Application
):AndroidViewModel(application) {

    private val repository = ProtoRepository(application)

    val readPersonProto = repository.readPersonProto.asLiveData()

    fun updatePerson(
        username:String,
        age: Int
    ) = viewModelScope.launch(Dispatchers.IO) {
        repository.updatePersonProto(
            username,age
        )
    }
}