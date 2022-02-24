package com.example.protodatastore.proto

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.protodatastore.Person
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException

class ProtoRepository(
    private val context: Context
) {

    val readPersonProto:Flow<Person> = context.personDataStore.data
        .catch { exception ->
            if (exception is IOException){
                Log.d("DataStore", exception.message.toString())
                emit(Person.getDefaultInstance())
            }else{
                throw exception
            }
        }

    suspend fun updatePersonProto(
        username:String,
        age:Int
    ){
        context.personDataStore.updateData { person ->
            person.toBuilder()
                .setUsername(username)
                .setAge(age)
                .build()
        }
    }

    companion object{
        private val Context.personDataStore:DataStore<Person> by dataStore(
            fileName = "person_data_store",
            serializer = PersonSerializer
        )
    }
}