package com.turma05.kotlinrickapp.listagem.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.turma05.kotlinrickapp.data.api.OnResult
import com.turma05.kotlinrickapp.data.model.ResponseModel
import com.turma05.kotlinrickapp.listagem.model.PersonagemModel
import com.turma05.kotlinrickapp.listagem.repository.PersonagemRepository

class PersonagemViewModel(
    private val repository: PersonagemRepository
): ViewModel() {

    val personagens = MutableLiveData<List<PersonagemModel>>()
    var personagensAntesDaBusca = listOf<PersonagemModel>()
    fun obterLista(name: String? = null) {
        repository.obterLista(name, object: OnResult<ResponseModel<PersonagemModel>> {
            override fun onSucess(result: ResponseModel<PersonagemModel>) {
                personagens.value = result.results
            }

            override fun onFailure() {
                TODO("Not yet implemented")
            }
        })
    }

    fun buscar(p0: String?) {
        personagensAntesDaBusca = personagens.value!!
        obterLista(p0)
    }

    fun cancelarBusca() {
        personagens.value = personagensAntesDaBusca
    }

    class PersonagemViewModelFactory(
        private val repository: PersonagemRepository
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PersonagemViewModel(repository) as T
        }
    }
}