package com.example.protodatastore.proto

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.protodatastore.Person
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object PersonSerializer:Serializer<Person> {
    override suspend fun readFrom(input: InputStream): Person
    = try {
        Person.parseFrom(input)
    }catch (e: InvalidProtocolBufferException){
        throw CorruptionException("proto.", e)
    }

    override suspend fun writeTo(t: Person, output: OutputStream) = t.writeTo(output)

    override val defaultValue: Person = Person.getDefaultInstance()

}