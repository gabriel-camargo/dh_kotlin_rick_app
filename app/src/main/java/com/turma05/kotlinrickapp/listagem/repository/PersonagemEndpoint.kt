package com.turma05.kotlinrickapp.listagem.repository

import com.turma05.kotlinrickapp.data.model.ResponseModel
import com.turma05.kotlinrickapp.listagem.model.PersonagemModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PersonagemEndpoint {
    @GET("character")
    fun obterLista(@Query("name") name: String?): Call<ResponseModel<PersonagemModel>>
}