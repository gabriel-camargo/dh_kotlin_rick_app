package com.turma05.kotlinrickapp.listagem.repository

import com.turma05.kotlinrickapp.data.api.NetworkUtils
import com.turma05.kotlinrickapp.data.api.OnResult
import com.turma05.kotlinrickapp.data.model.ResponseModel
import com.turma05.kotlinrickapp.listagem.model.PersonagemModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonagemRepository {
    public fun obterLista(name: String?, onResult: OnResult<ResponseModel<PersonagemModel>>) {
        val client = NetworkUtils.getRetrofitInstance()
        val api = client.create(PersonagemEndpoint::class.java)

        val call = api.obterLista(name)

        call.enqueue(object: Callback<ResponseModel<PersonagemModel>> {
            override fun onResponse(
                call: Call<ResponseModel<PersonagemModel>>,
                response: Response<ResponseModel<PersonagemModel>>
            ) {
                onResult.onSucess(response.body()!!)
            }

            override fun onFailure(call: Call<ResponseModel<PersonagemModel>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}
